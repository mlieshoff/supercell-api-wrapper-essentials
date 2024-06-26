package supercell.api.wrapper.essentials.build;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static supercell.api.wrapper.essentials.TestConstants.*;
import static supercell.api.wrapper.essentials.build.ReadmeVersionChecker.main;

class ReadmeVersionCheckerTest {

    @Test
    void main_whenWithNullArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(null))
                .withMessage("version filename, readme file name and readme title are required!");
    }

    @Test
    void main_whenWithEmptyArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(new String[] {}))
                .withMessage("version filename, readme file name and readme title are required!");
    }

    @Test
    void main_whenWithEmptyVersionFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(
                        () -> main(new String[] {EMPTY, README_CONSISTENT_FILENAME, README_TITLE}))
                .withMessage("versionFilename must be set! But was: ");
    }

    @Test
    void main_whenWithEmptyReadMeFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> main(new String[] {VERSION_100_FILENAME, EMPTY, README_TITLE}))
                .withMessage("readMeFilename must be set! But was: ");
    }

    @Test
    void main_whenWithEmptyReadMeTitleParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME, README_CONSISTENT_FILENAME, EMPTY
                                        }))
                .withMessage("readMeTitle must be set! But was: ");
    }

    @Test
    void main_whenWithRaedMeInconsistentTitleName_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME,
                                            README_INCONSISTENT_TITLE_NAME,
                                            README_TITLE
                                        }))
                .withMessage(
                        "Readme file './src/test/resources/README_inconsistent_title_name.md' contains problems, reason: title");
    }

    @Test
    void main_whenWithReadMeInconsistentTitleVersion_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME,
                                            README_INCONSISTENT_TITLE_VERSION,
                                            README_TITLE
                                        }))
                .withMessage(
                        "Readme file './src/test/resources/README_inconsistent_title_version.md' contains problems, reason: title");
    }

    @Test
    void main_whenWithReadMeInconsistentGradleName_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME,
                                            README_INCONSISTENT_GRADLE_NAME,
                                            README_TITLE
                                        }))
                .withMessage(
                        "Readme file './src/test/resources/README_inconsistent_gradle_name.md' contains problems, reason: gradleVersion");
    }

    @Test
    void main_whenWithReadMeInconsistentGradleVersion_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME,
                                            README_INCONSISTENT_GRADLE_VERSION,
                                            README_TITLE
                                        }))
                .withMessage(
                        "Readme file './src/test/resources/README_inconsistent_gradle_version.md' contains problems, reason: gradleVersion");
    }

    @Test
    void main_whenWithReadMeInconsistentMavenVersion_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME,
                                            README_INCONSISTENT_MAVEN_VERSION,
                                            README_TITLE
                                        }))
                .withMessage(
                        "Readme file './src/test/resources/README_inconsistent_maven_version.md' contains problems, reason: mavenVersion");
    }

    @Test
    void main_whenWithSameVersionInVersionFileAndReadMe_thenDoNotThrowException() {

        main(new String[] {VERSION_100_FILENAME, README_CONSISTENT_FILENAME, README_TITLE});
    }
}
