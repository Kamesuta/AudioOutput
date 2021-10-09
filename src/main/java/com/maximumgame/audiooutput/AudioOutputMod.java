package com.maximumgame.audiooutput;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AudioOutputMod implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "audiooutput";
    public static final String MOD_NAME = "Audio Output";
    public static SelectedOutputDevice SelectedOutputDevice = new SelectedOutputDevice();

    public AudioOutputMod() {
    }

    public void onInitialize() {
        log(Level.INFO, "Init");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[Audio Output] " + message);
    }
}
