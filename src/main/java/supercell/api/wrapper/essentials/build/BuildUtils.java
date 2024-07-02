package supercell.api.wrapper.essentials.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static supercell.api.wrapper.essentials.common.Utils.require;

public class BuildUtils {

    public static String readFileToString(String filename) {
        require("filename", filename);
        File file = new File(filename);
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return new String(fileInputStream.readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("could not read file: " + filename);
        }
    }
}
