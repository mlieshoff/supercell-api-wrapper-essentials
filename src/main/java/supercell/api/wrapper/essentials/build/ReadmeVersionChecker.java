package supercell.api.wrapper.essentials.build;

import lombok.extern.slf4j.Slf4j;

import static supercell.api.wrapper.essentials.build.BuildUtils.readFileToString;
import static supercell.api.wrapper.essentials.common.Utils.require;

@Slf4j
public class ReadmeVersionChecker {

    public static void main(String[] args) {
        if (args == null || args.length != 3) {
            throw new IllegalStateException(
                    "version filename, readme file name and readme title are required!");
        }
        String versionFilename = args[0];
        require("versionFilename", versionFilename);
        String readMeFilename = args[1];
        require("readMeFilename", readMeFilename);
        String readMeArtefactName = args[2];
        require("readMeTitle", readMeArtefactName);
        String versionTxt = readFileToString(versionFilename).substring(1);
        String readMe = readFileToString(readMeFilename);
        boolean success = true;
        String searchTitle = readMeArtefactName + " " + versionTxt;
        String searchMavenVersion = "<version>" + versionTxt + "</version>";
        String searchGradle = "name: '" + readMeArtefactName + "', version: '" + versionTxt + "'";
        String reason = null;
        if (!readMe.contains(searchTitle)) {
            success = false;
            reason = "title";
        } else if (!readMe.contains(searchMavenVersion)) {
            success = false;
            reason = "mavenVersion";
        } else if (!readMe.contains(searchGradle)) {
            success = false;
            reason = "gradleVersion";
        }
        if (!success) {
            log.error("****************************************************");
            log.error("*** README VERSION DON'T MATCHES VERSION.TXT !!! ***");
            log.error("****************************************************");
            throw new IllegalStateException(
                    "Readme file '" + readMeFilename + "' contains problems, reason: " + reason);
        }
    }
}
