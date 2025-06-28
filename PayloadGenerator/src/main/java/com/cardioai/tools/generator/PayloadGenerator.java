package com.cardioai.tools.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
            List<String> records = new LinkedList();
            Scanner recordScanner = new Scanner(new File(config.getManifestFilePath()));
            while (recordScanner.hasNextLine()) {
                String line = recordScanner.nextLine();
                records.add(line);
            }

            List<String> commands = buildExtractionCommands(config, records);
            File scriptFile = new File(config.getScriptFilePath());
            FileWriter writter = new FileWriter(scriptFile);
            try (BufferedWriter writer = new BufferedWriter(writter)) {
                writer.write("#!/usr/bin/bash");
                writer.newLine();
                for (String command : commands) {
                    writer.write(command);
                    writer.newLine();
                }
            }
        } catch (IOException | IllegalStateException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private static PayloadGeneratorConfig getPayloadGeneratorConfig(String[] args) throws IOException {
        String manifestFilePath = null;
        int fromTime = 0;
        int toTime = 60;
        String targetDirectoryPath = null;
        String scriptFilePath = null;

        for (int e = 0; e < args.length; e++) {
            switch (args[e]) {
                case "-m":
                case "--manifest": {
                    manifestFilePath = args[++e];
                    File check = new File(manifestFilePath);
                    if (!check.exists() || !check.isFile()) {
                        logErrorMessage(
                                LogMessage.PG001, manifestFilePath);
                        throw new IOException();
                    }
                    break;
                }
                case "-f":
                case "--from-time":
                    fromTime = Integer.parseInt(args[++e]);
                    break;
                case "-t":
                case "--to-time":
                    toTime = Integer.parseInt(args[++e]);
                    break;
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
                case "-s":
                case "--script": {
                    scriptFilePath = args[++e];
                    break;
                }
                default:
                    logErrorMessage(
                            LogMessage.PG003, args[e]);
                    throw new IllegalStateException();
            }
        }

        return new PayloadGeneratorConfig(
                manifestFilePath, fromTime, toTime, targetDirectoryPath, scriptFilePath);
    }

    private static List<String> buildExtractionCommands(PayloadGeneratorConfig config, List<String> records) throws IOException, InterruptedException {
        List<String> commands = new ArrayList<>();

        for (String record : records) {
            File headerFile = new File(record);
            if (!headerFile.exists() || !headerFile.isFile()) {
                logErrorMessage(LogMessage.PG004, record);
                throw new IOException();
            }

            String dataFileName = UUID.randomUUID().toString();
            String dataFullFilePath = config.getTargetDirectoryPath()
                    .concat(dataFileName)
                    .concat(".txt");

            // Example: rdsamp -r ./dataset/record.hea -c -H -f 0 -t 60 -v > /data/53d22f41-e20f-4142-aea3-04d1bb09048c.txt
            StringBuilder command = new StringBuilder();
            command.append("rdsamp ");
            command.append("-r ");
            command.append(record);
            command.append(" -c ");
            command.append("-H ");
            command.append("-f ");
            command.append(config.getFromTime());
            command.append(" -t ");
            command.append(config.getToTime());
            command.append(" -v ");
            command.append("> ");
            command.append(dataFullFilePath);
            commands.add(command.toString());
        }

        return commands;
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}
