package supercell.api.wrapper.essentials.build;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static supercell.api.wrapper.essentials.TestConstants.*;
import static supercell.api.wrapper.essentials.build.ChangeLogVersionChecker.main;

class ChangeLogVersionCheckerTest {

    @Test
    void main_whenWithNullArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(null))
                .withMessage("version filename and changelog file name are required!");
    }

    @Test
    void main_whenWithEmptyArray_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> main(new String[] {}))
                .withMessage("version filename and changelog file name are required!");
    }

    @Test
    void main_whenWithEmptyVersionFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> main(new String[] {EMPTY, CHANGELOG_CONSISTENT_FILENAME}))
                .withMessage("versionFilename must be set! But was: ");
    }

    @Test
    void main_whenWithEmptyChangeLogFileParameter_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> main(new String[] {VERSION_100_FILENAME, EMPTY}))
                .withMessage("changeLogFilename must be set! But was: ");
    }

    @Test
    void main_whenWithChangeLogInconsistentHead_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME, CHANGELOG_INCONSISTENT_HEAD
                                        }))
                .withMessage(
                        "Versions in './src/test/resources/CHANGELOG_inconsistent_head.md' are not matching, reason: head");
    }

    @Test
    void main_whenWithChangeLogInconsistentTitle_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME, CHANGELOG_INCONSISTENT_TITLE
                                        }))
                .withMessage(
                        "Versions in './src/test/resources/CHANGELOG_inconsistent_title.md' are not matching, reason: title");
    }

    @Test
    void main_whenWithChangeLogInconsistentCurrent_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(
                        () ->
                                main(
                                        new String[] {
                                            VERSION_100_FILENAME, CHANGELOG_INCONSISTENT_CURRENT
                                        }))
                .withMessage(
                        "Versions in './src/test/resources/CHANGELOG_inconsistent_current.md' are not matching, reason: tag");
    }

    @Test
    void main_whenWithSameVersionInVersionFileAndChangeLog_thenDoNotThrowException() {

        main(new String[] {VERSION_100_FILENAME, CHANGELOG_CONSISTENT_FILENAME});
    }
}
