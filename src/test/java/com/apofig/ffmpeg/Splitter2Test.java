package com.apofig.ffmpeg;

import org.junit.Test;

public class Splitter2Test extends AbstractRunnerTest {

    @Test
    public void shouldExecThreeTimes() {
        // when
        new Splitter2(runner)
                .input("work/input.flv")
                .time("00:00:00")
                .save("work/output1.flv")
                .time("00:30:00")
                .remove()
                .time("01:00:00")
                .save("work/output2.flv")
                .time("01:30:00")
                .save("work/output3.flv")
                .time("01:40:23")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/output1.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:00:00 -to 01:30:00 work/output2.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:30:00 -to 01:40:23 work/output3.flv]");
    }

    @Test
    public void shouldExecOneTimes() {
        // when
        new Splitter2(runner)
                .input("work/input.flv")
                .time("00:00:00")
                .save("work/output1.flv")
                .time("00:30:00")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/output1.flv]");
    }

}