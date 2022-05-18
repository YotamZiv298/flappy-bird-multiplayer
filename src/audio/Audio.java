package audio;

import main.resources.OSDetector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public abstract class Audio {

    public static final String DIE = OSDetector.isWindows() ? "src\\audio\\sfx\\sfx_die.wav" : "src/audio/sfx/sfx_die.wav";
    public static final String FLAP = OSDetector.isWindows() ? "src\\audio\\sfx\\sfx_flap.wav" : "src/audio/sfx/sfx_flap.wav";
    public static final String POINT = OSDetector.isWindows() ? "src\\audio\\sfx\\sfx_point.wav" : "src/audio/sfx/sfx_point.wav";

    public static void play(String audioPath) {
        new Thread(() -> {
            File file = new File(audioPath);

            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();

                clip.open(audioInputStream);
                clip.start();
                clip.addLineListener(myLineEvent -> {
                    if (myLineEvent.getType() == LineEvent.Type.STOP)
                        clip.close();
                });
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
