package com.cardioai.tools.simulator;

import com.cardioai.tools.model.PayloadVO;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TrafficSimulator
 *
 * @author Ariel Gonzalez
 * @version 1.0
 */
public class TrafficSimulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficSimulator.class);

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) {
        try {
            TrafficSimulatorConfig config = getTrafficSimulatorConfig(args);
            List<PayloadVO> payloads = getPayloads(config.getSourceDirectoryPath());
            /**
             * TODO: Implement Traffic Simulator...
             */
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static TrafficSimulatorConfig getTrafficSimulatorConfig(String[] args) throws IOException {
        String sourceDirectoryPath = null;
        String adapterName = null;
        String resourceName = null;
        String originCode = null;
        String deviceCode = null;
        long pauseBeforeInitiating = 15000;
        long pauseAfterMessage = 0;
        long pauseAfterCycle = 15000;

        for (int e = 0; e < args.length; e++) {
            switch (args[e]) {
                case "-s":
                case "--source": {
                    sourceDirectoryPath = args[++e];
                    File check = new File(sourceDirectoryPath);
                    if (!check.exists() || !check.isDirectory()) {
                        logErrorMessage(
                                LogMessage.TS001, sourceDirectoryPath);
                        throw new IOException();
                    }
                    break;
                }
                case "-a":
                case "--adapter": {
                    adapterName = args[++e];
                    if (!adapterName.equals("HTTP") && !adapterName.equals("ESB")) {
                        logErrorMessage(
                                LogMessage.TS002, String.valueOf(adapterName));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-n":
                case "--name": {
                    resourceName = args[++e];
                    if (resourceName.startsWith("-")) {
                        logErrorMessage(
                                LogMessage.TS003, String.valueOf(resourceName));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-o":
                case "--origin-code": {
                    originCode = args[++e];
                    if (!originCode.equals("clinical") && !originCode.equals("consumer")) {
                        logErrorMessage(
                                LogMessage.TS004, String.valueOf(originCode));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-v":
                case "--device-code": {
                    deviceCode = args[++e];
                    if (deviceCode.startsWith("-")) {
                        logErrorMessage(
                                LogMessage.TS005, String.valueOf(deviceCode));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-b":
                case "--before": {
                    pauseBeforeInitiating = Long.parseLong(args[++e]);
                    if (pauseBeforeInitiating < 0) {
                        logErrorMessage(
                                LogMessage.TS006, String.valueOf(pauseBeforeInitiating));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-f":
                case "--after": {
                    pauseAfterMessage = Long.parseLong(args[++e]);
                    if (pauseAfterMessage < 0) {
                        logErrorMessage(
                                LogMessage.TS007, String.valueOf(pauseAfterMessage));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                case "-c":
                case "--cycle": {
                    pauseAfterCycle = Long.parseLong(args[++e]);
                    if (pauseAfterCycle < 0) {
                        logErrorMessage(
                                LogMessage.TS008, String.valueOf(pauseAfterCycle));
                        throw new IllegalArgumentException();
                    }
                    break;
                }
                default:
                    logErrorMessage(
                            LogMessage.TS009, args[e]);
                    throw new IllegalStateException();
            }
        }

        return new TrafficSimulatorConfig(
                sourceDirectoryPath, adapterName, resourceName, originCode, deviceCode, pauseBeforeInitiating, pauseAfterMessage, pauseAfterCycle);
    }

    private static List<PayloadVO> getPayloads(String sourceDirectoryPath) throws IOException {
        List<PayloadVO> payloads = new LinkedList<>();

        Gson gson = new Gson();
        File sourceDirectory = new File(sourceDirectoryPath);
        File[] payloadFiles = sourceDirectory.listFiles();
        for (File payloadFile : payloadFiles) {
            String json = Files.readString(Paths.get(payloadFile.toURI()));
            payloads.add(
                    gson.fromJson(json, PayloadVO.class));
        }

        return payloads;
    }

    private static void logErrorMessage(LogMessage error, String value) {
        LOGGER.error(error.getMessage(),
                error.name(), value);
    }
}
