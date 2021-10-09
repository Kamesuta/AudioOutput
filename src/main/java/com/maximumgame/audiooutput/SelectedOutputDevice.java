package com.maximumgame.audiooutput;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Output Device Config
 * @author Kamesuta
 */
public class SelectedOutputDevice {
    private final File file;
    private final Properties properties;

    public SelectedOutputDevice() {
        file = FabricLoader.getInstance().getConfigDir().resolve("audiooutput.properties").toFile();
        properties = new Properties();
        load();
    }

    private void load() {
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            AudioOutputMod.log(Level.WARN, "Could not read config file.");
        }
    }

    private void save() {
        try {
            properties.store(new FileOutputStream(file), "Audio Output Config");
        } catch (IOException e) {
            AudioOutputMod.log(Level.WARN, "Could not save config file.");
        }
    }

    public String getDeviceName() {
        return properties.getProperty("audiooutput.select");
    }

    public void setDeviceName(String name) {
        if (name != null)
            properties.setProperty("audiooutput.select", name);
        else
            properties.remove("audiooutput.select");
        save();
    }
}
