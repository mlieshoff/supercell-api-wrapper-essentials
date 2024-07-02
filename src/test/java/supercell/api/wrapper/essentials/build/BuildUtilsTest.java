package supercell.api.wrapper.essentials.build;

import static org.assertj.core.api.Assertions.*;

import static supercell.api.wrapper.essentials.TestConstants.*;
import static supercell.api.wrapper.essentials.TestConstants.FILE;
import static supercell.api.wrapper.essentials.build.BuildUtils.readFileToString;

import org.junit.jupiter.api.Test;

class BuildUtilsTest {

    @Test
    void main_whenWithNullFilename_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> readFileToString(NULL_STRING))
                .withMessage("filename must be set! But was: null");
    }

    @Test
    void main_whenWithEmptyFilename_thenThrowException() {

        assertThatIllegalArgumentException()
                .isThrownBy(() -> readFileToString(EMPTY))
                .withMessage("filename must be set! But was: ");
    }

    @Test
    void main_whenWithNonExistingFilename_thenThrowException() {

        assertThatIllegalStateException()
                .isThrownBy(() -> readFileToString(NOT_EXISTING_FILE))
                .withMessage("could not read file: xxx.txt");
    }

    @Test
    void main_whenWithFilename_thenRead() {
        String expected = "hey ho";

        String actual = readFileToString(FILE);

        assertThat(actual).isEqualTo(expected);
    }
}
