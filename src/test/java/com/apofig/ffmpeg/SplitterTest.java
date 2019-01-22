package com.apofig.ffmpeg;

import org.junit.Test;

public class SplitterTest extends AbstractRunnerTest {

    @Test
    public void shouldExecThreeTimes() {
        // when
        new Splitter(runner)
                .input("work/input.flv")
                .part(Splitter.Part.time("00:00:00", "00:30:00").to("work/output1.flv"))
                .part(Splitter.Part.time("01:00:00", "01:30:00").to("work/output2.flv"))
                .part(Splitter.Part.time("01:30:00", "01:40:23").to("work/output3.flv"))
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/output1.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:00:00 -to 01:30:00 work/output2.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:30:00 -to 01:40:23 work/output3.flv]");
    }

    @Test
    public void shouldExecOneTimes() {
        // when
        new Splitter(runner)
                .input("work/input.flv")
                .part(Splitter.Part.time("00:00:00", "00:30:00").to("work/output1.flv"))
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/output1.flv]");
    }

}