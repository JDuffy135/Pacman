package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
    Clip clip;
    URL soundURL[] = new URL[11];

    public Sound()
    {
        soundURL[0] = getClass().getResource("/sound/cutscene (probably won't use).wav");
        soundURL[1] = getClass().getResource("/sound/eatenghostretreating.wav");
        soundURL[2] = getClass().getResource("/sound/eatfruit.wav");
        soundURL[3] = getClass().getResource("/sound/eatghost.wav");
        soundURL[4] = getClass().getResource("/sound/frightenedghosts.wav");
        soundURL[5] = getClass().getResource("/sound/ghostsiren.wav");
        soundURL[6] = getClass().getResource("/sound/ghostsiren2.wav");
        soundURL[7] = getClass().getResource("/sound/highscore.wav");
        soundURL[8] = getClass().getResource("/sound/pacmandeath.wav");
        soundURL[9] = getClass().getResource("/sound/startup.wav");
        soundURL[10] = getClass().getResource("/sound/wakawaka.wav");
    }

    public void setFile(int i)
    {
        try
        {
            AudioInputStream AIS = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(AIS);
        } catch (Exception e)
        {

        }
    }

    public void play()
    {
        clip.start();
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop()
    {
        clip.stop();
    }

    public boolean isRunning()
    {
        return clip.isRunning();
    }
}
