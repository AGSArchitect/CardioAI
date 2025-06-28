package com.cardioai.tools.generator;

/**
 * PayloadGeneratorConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class PayloadGeneratorConfig {

    private final String manifestFilePath;
    private final int fromTime;
    private final int toTime;
    private final String targetDirectoryPath;
    private final String scriptFilePath;

    public PayloadGeneratorConfig(String manifestFilePath, int fromTime, int toTime, String targetDirectoryPath, String scriptFilePath) {
        this.manifestFilePath = manifestFilePath;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.targetDirectoryPath = targetDirectoryPath;
        this.scriptFilePath = scriptFilePath;
    }

    public String getManifestFilePath() {
        return manifestFilePath;
    }

    public int getFromTime() {
        return fromTime;
    }

    public int getToTime() {
        return toTime;
    }

    public String getTargetDirectoryPath() {
        return targetDirectoryPath;
    }

    public String getScriptFilePath() {
        return scriptFilePath;
    }
}
