package supercell.api.wrapper.essentials.build;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import static supercell.api.wrapper.essentials.TestConstants.*;
import static supercell.api.wrapper.essentials.build.ReleaseVersionChecker.main;

import org.junit.jupiter.api.Test;

class ReleaseVersionCheckerTest {

    @Test
    void main_whenWithNullArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(null))
                .withMessage("version filename and pom file name are required!");
    }

    @Test
    void main_whenWithEmptyArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(new String[] {}))
                .withMessage("version filename and pom file name are required!");
    }

    @Test
    void main_whenWithNotEnoughParameters_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(new String[] {PARAMETER}))
                .withMessage("version filename and pom file name are required!");
    }

    @Test
    void main_whenWithVersionFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> main(new String[] {EMPTY, PARAMETER}))
                .withMessage("versionFilename must be set! But was: ");
    }

    @Test
    void main_whenWithPomFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> main(new String[] {PARAMETER, EMPTY}))
                .withMessage("pomFilename must be set! But was: ");
    }

    @Test
    void main_whenWithDifferentVersionInVersionFileAndPom_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_DIFFERENT_THAN_TEST_POM_FILENAME,
                                            TEST_POM_FILENAME
                                        }))
                .withMessage(
                        "Version of 'VERSION.txt' (2.0) are not matching with version in 'pom.xml' (1.0.0)!");
    }

    @Test
    void main_whenWithSameVersionInVersionFileAndPom_thenDoNotThrowException() {

        main(new String[] {VERSION_100_FILENAME, TEST_POM_FILENAME});
    }
}
