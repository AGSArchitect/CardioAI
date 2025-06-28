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

    public PayloadGeneratorConfig(String manifestFilePath, int fromTime, int toTime, String targetDirectoryPath) {
        this.manifestFilePath = manifestFilePath;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.targetDirectoryPath = targetDirectoryPath;
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
}
