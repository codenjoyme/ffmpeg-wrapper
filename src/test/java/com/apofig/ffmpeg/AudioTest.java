package com.apofig.ffmpeg;

import org.junit.Test;

import java.io.IOException;

public class AudioTest extends AbstractRunnerTest {

    @Test
    public void shouldExtract() {
        // when
        audio()
                .extract("work/done.mp4");

        // then
        assertExec("ffmpeg -i work/done.mp4 -vn -acodec copy work/done-audio.mp4");
    }

    @Test
    public void shouldAudio() {
        // when
        audio()
                .join("work/done.mp4", "work/audio.mp4");

        // then
        assertExec("ffmpeg -i work/done.mp4 -i work/audio.mp4 -c:v copy -map 0:v:0 -map 1:a:0 work/done-merged.mp4");
    }

    @Test
    public void shouldDelay() {
        // when
        audio()
                .delay("work/done.mp4", 1.4);

        // then
        assertExec("ffmpeg.exe -i work/done.mp4 -itsoffset 1.4 -i work/done.mp4 -map 0:v -map 1:a -c copy work/done-delayed.mp4");
    }

    private Audio audio() {
        return new Audio(runner);
    }


}