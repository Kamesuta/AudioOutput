package com.maximumgame.audiooutput.screen;

import com.maximumgame.audiooutput.AudioOutputMod;
import com.maximumgame.audiooutput.mixin.SoundManagerAccessor;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.Level;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SoundOutputScreen extends GameOptionsScreen {
    private final Screen parent;
    private ButtonListWidget list;

    public SoundOutputScreen(Screen parent) {
        super(parent, (GameOptions) null, new TranslatableText("audiooutput.deviceselectscreen", new Object[0]));
        this.parent = parent;
    }

    private static ArrayList<String> GetDevices() {
        ArrayList<String> devices = new ArrayList();
        devices.clear();

        try {
            boolean extPresentSuccess = ALC10.alcIsExtensionPresent(0L, "ALC_enumerate_all_EXT");
            if (!extPresentSuccess) {
                throw new Exception("Failed to enumerate");
            } else {
                List<String> alcDevices = ALUtil.getStringList(0L, 4115);
                if (alcDevices == null) {
                    return devices;
                } else {
                    Iterator var3 = alcDevices.iterator();

                    while (var3.hasNext()) {
                        String deviceName = (String) var3.next();
                        long device = ALC10.alcOpenDevice(deviceName);
                        if (device != 0L) {
                            int errorCode = ALC10.alcGetError(device);
                            if (errorCode == 0) {
                                ALC10.alcCloseDevice(device);
                                devices.add(deviceName);
                            }
                        }
                    }

                    return devices;
                }
            }
        } catch (Exception var8) {
            AudioOutputMod.log(Level.ERROR, var8.getMessage());
            devices.clear();
            return devices;
        }
    }

    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addSingleOptionEntry(new SoundDeviceOption(new TranslatableText("audiooutput.device.default"), this, (String) null));
        ArrayList<String> audioDevices = GetDevices();
        Iterator var2 = audioDevices.iterator();

        while (var2.hasNext()) {
            String device = (String) var2.next();
            this.list.addSingleOptionEntry(new SoundDeviceOption(new TranslatableText(device), this, device));
        }

        this.addSelectableChild(this.list);
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, new TranslatableText("gui.done"), (buttonWidget) -> {
            this.client.setScreen(this.parent);
        }));
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void SelectedOption(SoundDeviceOption option) {
        AudioOutputMod.SelectedOutputDevice.setDeviceName(option.GetDeviceName());
        SoundSystem soundSystem = ((SoundManagerAccessor) this.client.getSoundManager()).GetSoundSystem();
        soundSystem.reloadSounds();
        this.client.setScreen(this.parent);
    }
}
