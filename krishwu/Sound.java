package krishwu;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    private File file;
    private AudioInputStream audioInputStream;
    private Clip clip;

    public Sound(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        // Makes the audio run and work properly so yeah.
        file = new File(filePath); // New file.
        audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile()); // Pass it into the audio stream.
        clip = AudioSystem.getClip(); // Get the clip and set it to the clip.
        clip.open(audioInputStream); // Open the sound file and be able to play it with it prepared.

    }

    //Allows you the play the audio track.
    public void play() {
        //Starts the sound at the beggining.
        clip.setFramePosition(0);
        //Starts playing the sound until it reaches the end.
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously so it keeps playing and you love it.
    }
}
