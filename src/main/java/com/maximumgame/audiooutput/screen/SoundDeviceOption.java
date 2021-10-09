package com.maximumgame.audiooutput.screen;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class SoundDeviceOption extends Option {
    private final String key;
    private final SoundOutputScreen parent;
    private final String deviceName;

    public SoundDeviceOption(Text key, SoundOutputScreen parent, String deviceName) {
        super(key.asString());
        this.key = key.getString();
        this.parent = parent;
        this.deviceName = deviceName;
    }

    public String GetDeviceName() {
        return this.deviceName;
    }

    public void set(GameOptions options) {
        this.parent.SelectedOption(this);
    }

    public boolean get(GameOptions options) {
        return true;
    }

    public Text getDisplayString(GameOptions options) {
        return this.key.startsWith("OpenAL Soft on") ? new LiteralText(this.key.substring(14, this.key.length())) : new LiteralText(this.key);
    }

    public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
        ClickableWidget btn = new ButtonWidget(x, y, width, 20, this.getDisplayString(options), (buttonWidget) -> {
            this.set(options);
        });
        btn.setMessage(this.getDisplayString(options));
        return btn;
    }
}
