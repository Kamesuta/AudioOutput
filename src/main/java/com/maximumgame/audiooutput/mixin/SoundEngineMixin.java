package com.maximumgame.audiooutput.mixin;

import com.maximumgame.audiooutput.AudioOutputMod;
import net.minecraft.client.sound.SoundEngine;
import org.lwjgl.openal.ALC10;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({SoundEngine.class})
public abstract class SoundEngineMixin {
    public SoundEngineMixin() {
    }

    /**
     * @author Maximumgame
     * @reason Customize output device target
     */
    @Overwrite
    private static long openDevice() {
        long device = 0L;

        for (int i = 0; i < 3; ++i) {
            long l = ALC10.alcOpenDevice(AudioOutputMod.SelectedOutputDevice.getDeviceName());
            if (l != 0L) {
                int err = ALC10.alcGetError(l);
                if (err == 0) {
                    device = l;
                    break;
                }
            }
        }

        if (device == 0L) {
            throw new IllegalStateException("Failed to open OpenAL device");
        } else {
            return device;
        }
    }
}
