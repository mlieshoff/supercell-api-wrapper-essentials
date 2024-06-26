package supercell.api.wrapper.essentials.connector;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static wiremock.org.apache.http.HttpHeaders.AUTHORIZATION;
import static wiremock.org.apache.http.HttpStatus.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import supercell.api.wrapper.essentials.common.PaginationResponse;
import supercell.api.wrapper.essentials.common.RawResponse;
import supercell.api.wrapper.essentials.common.Request;

import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Map;

class StandardConnectorTest {

    private static final String API_KEY = "API_KEY";
    private static final String JSON = "{}";

    private StandardConnector unitUnderTest;

    private String url;

    @Mock private HttpResponse<String> httpResponse;

    private static WireMockServer wireMockServer;

    @BeforeAll
    public static void beforeAll() {
        WireMockConfiguration wireMockConfiguration = new WireMockConfiguration().dynamicPort();
        wireMockServer = new WireMockServer(wireMockConfiguration);
        wireMockServer.start();
    }

    @AfterEach
    public void afterEach() {
        wireMockServer.resetAll();
    }

    @BeforeEach
    void setUp() {
        configureFor("localhost", wireMockServer.port());
        url = "http://localhost:" + wireMockServer.port();
        openMocks(this);
        unitUnderTest = new StandardConnector();
    }

    @Test
    void getInitialValue_whenCalled_thenReturnNull() throws Exception {

        String actual = unitUnderTest.getInitialValue(url);

        assertThat(actual).isNull();
    }

    @Test
    void onJsonReceived_whenCalled_thenReturnNull() {

        assertThatNoException().isThrownBy(() -> unitUnderTest.onJsonReceived(url, JSON));
    }

    @Test
    void checkResponse_whenCalled_thenReturnUnmodifiedResponse() {

        HttpResponse<String> actual = unitUnderTest.checkResponse(JSON, httpResponse);

        assertThat(actual).isEqualTo(httpResponse);
    }

    @Test
    void get_whenCalled_thenReturnGetValue() {
        FooResponse expected = createExpectedFooResponse();
        FooRequest fooRequest = new FooRequest(true);
        RequestContext requestContext =
                new RequestContext(
                        url + "/test/{restName}", API_KEY, fooRequest, FooResponse.class);
        stubFor(
                get(urlEqualTo("/test/rest?queryName1=query1&queryName2=query2"))
                        .withHeader(AUTHORIZATION, equalTo("Bearer " + API_KEY))
                        .willReturn(aResponse().withBody(JSON).withStatus(SC_OK)));

        PaginationResponse<String> actual = unitUnderTest.get(requestContext);

        assertThat(actual).isEqualTo(expected);
    }

    private FooResponse createExpectedFooResponse() {
        FooResponse fooResponse = new FooResponse();
        RawResponse rawResponse = new RawResponse();
        rawResponse.setRaw(JSON);
        fooResponse.setRawResponse(rawResponse);
        return fooResponse;
    }

    static class FooRequest extends Request {

        FooRequest(boolean storeRawResponse) {
            super(storeRawResponse);
        }

        @Override
        public Map<String, Object> getQueryParameters() {
            Map<String, Object> map = super.getQueryParameters();
            map.put("queryName1", "query1");
            map.put("queryName2", "query2");
            return map;
        }

        @Override
        public Map<String, Object> getRestParameters() {
            Map<String, Object> map = super.getRestParameters();
            map.put("restName", "rest");
            return map;
        }
    }

    static class FooResponse extends PaginationResponse<String> {}

    @Test
    void get_whenCalledAndStatusCodeNot200_thenThrowException() {
        FooResponse expected = new FooResponse();
        RawResponse rawResponse = new RawResponse();
        rawResponse.setRaw(JSON);
        expected.setRawResponse(rawResponse);
        FooRequest fooRequest = new FooRequest(true);
        RequestContext requestContext =
                new RequestContext(
                        url + "/test/{restName}", API_KEY, fooRequest, FooResponse.class);
        stubFor(
                get(urlEqualTo("/test/rest?queryName1=query1&queryName2=query2"))
                        .withHeader(AUTHORIZATION, equalTo("Bearer " + API_KEY))
                        .willReturn(
                                aResponse()
                                        .withBody(JSON)
                                        .withStatus(SC_INTERNAL_SERVER_ERROR)
                                        .withBody(JSON)));

        assertThatExceptionOfType(ConnectorException.class)
                .isThrownBy(() -> unitUnderTest.get(requestContext))
                .withMessage("500: " + JSON);
    }

    @Test
    void get_whenCalledAndException_thenThrowException() {
        FooRequest fooRequest = new FooRequest(true);
        RequestContext requestContext =
                new RequestContext("xxx yyy", API_KEY, fooRequest, FooResponse.class);
        stubFor(
                get(urlEqualTo("/test/rest?queryName1=query1&queryName2=query2"))
                        .withHeader(AUTHORIZATION, equalTo("Bearer " + API_KEY))
                        .willReturn(aResponse().withBody(JSON).withStatus(SC_OK)));

        assertThatExceptionOfType(ConnectorException.class)
                .isThrownBy(() -> unitUnderTest.get(requestContext))
                .withCauseInstanceOf(URISyntaxException.class);
    }
}
