package supercell.api.wrapper.essentials.build;

import lombok.extern.slf4j.Slf4j;

import static supercell.api.wrapper.essentials.build.BuildUtils.readFileToString;
import static supercell.api.wrapper.essentials.common.Utils.require;

@Slf4j
public class ReleaseVersionChecker {

    public static void main(String[] args) {
        if (args == null || args.length != 2) {
            throw new IllegalStateException("version filename and pom file name are required!");
        }
        String versionFilename = args[0];
        String pomFilename = args[1];
        require("versionFilename", versionFilename);
        require("pomFilename", pomFilename);
        String versionTxt = readFileToString(versionFilename).substring(1);
        String pom = readFileToString(pomFilename);
        int start = pom.indexOf("<version>");
        int end = pom.indexOf("</version>");
        String pomVersion = pom.substring(start + 9, end);
        if (!versionTxt.equals(pomVersion)) {
            log.error("*************************************************");
            log.error("*** POM VERSION DON'T MATCHES VERSION.TXT !!! ***");
            log.error("*************************************************");
            throw new IllegalStateException(
                    "Version of 'VERSION.txt' ("
                            + versionTxt
                            + ") are not matching with version in 'pom.xml' ("
                            + pomVersion
                            + ")!");
        }
    }
}
