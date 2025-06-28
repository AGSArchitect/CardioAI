package com.cardioai.tools.generator;

/**
 * PayloadGeneratorConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class PayloadGeneratorConfig {

    private final String manifestFilePath;
    private final String targetDirectoryPath;

    public PayloadGeneratorConfig(String manifestFilePath, String targetDirectoryPath) {
        this.manifestFilePath = manifestFilePath;
        this.targetDirectoryPath = targetDirectoryPath;
    }

    public String getManifestFilePath() {
        return manifestFilePath;
    }

    public String getTargetDirectoryPath() {
        return targetDirectoryPath;
    }
}
