package com.cardioai.tools.generator;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PayloadGenerator
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class PayloadGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayloadGenerator.class);

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            PayloadGeneratorConfig config = getPayloadGeneratorConfig(args);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static PayloadGeneratorConfig getPayloadGeneratorConfig(String[] args) throws IOException {
        String sourceDirectoryPath = null;
        String targetDirectoryPath = null;

        for (int e = 0; e < args.length; e++) {
            switch (args[e]) {
                case "-s":
                case "--source": {
                    sourceDirectoryPath = args[++e];
                    File check = new File(sourceDirectoryPath);
                    if (!check.exists() || !check.isDirectory()) {
                        logErrorMessage(
                                LogMessage.PG001, sourceDirectoryPath);
                        throw new IOException();
                    }
                    break;
                }
                case "-d":
                case "--destination": {
                    targetDirectoryPath = args[++e];
                    File check = new File(targetDirectoryPath);
                    if (!check.exists() || !check.isDirectory()) {
                        logErrorMessage(
                                LogMessage.PG002, targetDirectoryPath);
                        throw new IOException();
                    }
                    break;
                }
                default:
                    logErrorMessage(
                            LogMessage.PG003, args[e]);
                    throw new IllegalStateException();
            }
        }

        return new PayloadGeneratorConfig(
                sourceDirectoryPath, targetDirectoryPath);
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}