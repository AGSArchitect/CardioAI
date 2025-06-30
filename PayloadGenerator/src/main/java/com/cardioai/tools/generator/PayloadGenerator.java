package com.cardioai.tools.generator;

import com.cardioai.tools.model.PayloadVO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
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
            File sourceDirectory = new File(config.getSourceDirectoryPath());
            File[] dataFiles = sourceDirectory.listFiles();
            List<PayloadVO> payloads = getPayloads(dataFiles);
            /**
             *
             * TODO: Write Payloads to Disk...
             *
             */
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

    private static List<PayloadVO> getPayloads(File[] dataFiles) throws FileNotFoundException {
        List<PayloadVO> payloads = new ArrayList();

        for (File dataFile : dataFiles) {
            Scanner dataScanner = new Scanner(dataFile);
            PayloadVO payload = new PayloadVO();
            payload.setDeviceId(getNextId());
            payload.setCustomerId(getNextId());
            payload.setSequenceId(getNextId());
            payload.setSequence(1);
            payload.setIndex(1);
            payload.setHeaders(getHeaders(dataScanner));
            payload.setData(getData(dataScanner));
            payload.setRecordId(getRecordId(dataFile.getName()));
            payload.setCreated(getTime());
            payloads.add(payload);
        }

        return payloads;
    }

    private static String getNextId() {
        return UUID.randomUUID().toString();
    }

    private static long getTime() {
        return System.currentTimeMillis();
    }

    private static String getHeaders(Scanner dataScanner) {
        String headers = dataScanner.nextLine();

        headers = headers.toUpperCase();
        headers = headers.replaceAll("'", "");
        headers = headers.replaceFirst("SAMPLE #", "INDEX");

        return headers;
    }

    private static List<String> getData(Scanner dataScanner) {
        List<String> data = new ArrayList<>();

        while (dataScanner.hasNextLine()) {
            data.add(dataScanner.nextLine());
        }

        return data;
    }

    private static String getRecordId(String name) {
        return name.substring(0, name.indexOf("."));
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}
