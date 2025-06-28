package com.cardioai.tools.generator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
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

            List<String> records = new LinkedList();
            Scanner scanner = new Scanner(new File(config.getManifestFilePath()));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                records.add(line);
            }
            
            /**
             * 
             * TODO: Simple-threaded implementation of the Payload Generator from PhysioNet WFDB records.
             * 
             */
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static PayloadGeneratorConfig getPayloadGeneratorConfig(String[] args) throws IOException {
        String manifestFilePath = null;
        String targetDirectoryPath = null;

        for (int e = 0; e < args.length; e++) {
            if (args[e].equals("-m") || args[e].equals("--manifest")) {
                manifestFilePath = args[++e];
                File check = new File(manifestFilePath);
                if (!check.exists() || !check.isFile()) {
                    logErrorMessage(
                            LogMessage.PG001, manifestFilePath);
                    throw new IOException();
                }
            } else if (args[e].equals("-d") || args[e].equals("--destination")) {
                targetDirectoryPath = args[++e];
                File check = new File(targetDirectoryPath);
                if (!check.exists() || !check.isDirectory()) {
                    logErrorMessage(
                            LogMessage.PG002, targetDirectoryPath);
                    throw new IOException();
                }
            }
        }

        return new PayloadGeneratorConfig(
                manifestFilePath, targetDirectoryPath);
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}
