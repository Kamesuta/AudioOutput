package com.maximumgame.audiooutput.mixin;

import com.maximumgame.audiooutput.screen.SoundOutputScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SoundOptionsScreen.class})
public abstract class SoundOptionsScreenMixin extends GameOptionsScreen {
    public SoundOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"init"}
    )
    private void init(CallbackInfo info) {
        this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 108, 150, 20, new TranslatableText("audiooutput.device"), (buttonWidget) -> {
            this.client.setScreen(new SoundOutputScreen(this));
        }));
    }
}
