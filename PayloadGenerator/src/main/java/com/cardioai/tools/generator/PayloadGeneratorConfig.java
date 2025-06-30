package com.cardioai.tools.generator;

/**
 * PayloadGeneratorConfig
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class PayloadGeneratorConfig {

    private final String sourceDirectoryPath;
    private final String targetDirectoryPath;

    public PayloadGeneratorConfig(String sourceDirectoryPath, String targetDirectoryPath) {
        this.sourceDirectoryPath = sourceDirectoryPath;
        this.targetDirectoryPath = targetDirectoryPath;
    }

    public String getSourceDirectoryPath() {
        return sourceDirectoryPath;
    }

    public String getTargetDirectoryPath() {
        return targetDirectoryPath;
    }
}