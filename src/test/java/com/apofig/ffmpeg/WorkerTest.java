package com.apofig.ffmpeg;

import org.junit.Test;

import java.io.IOException;

public class WorkerTest extends AbstractRunnerTest {

    @Test
    public void shouldProcessTwoParts() throws IOException {
        // when
        new Worker(runner)
                .output("work/done.mp4")
                .input("work/input.flv")
                    .start("00:00:00")
                    .cut("00:00:30", "00:00:35")
                    .cut("00:01:25", "00:01:30")
                    .end("00:02:00")
                .input("work/input2.flv")
                    .start("00:01:00")
                    .cut("00:02:00", "00:03:00")
                    .end("00:04:00")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:00:30 work/part_1_00-00-00_00-00-30.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:35 -to 00:01:25 work/part_1_00-00-35_00-01-25.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:01:30 -to 00:02:00 work/part_1_00-01-30_00-02-00.flv, " +
                "ffmpeg -i work/input2.flv -vcodec copy -acodec copy -ss 00:01:00 -to 00:02:00 work/part_2_00-01-00_00-02-00.flv, " +
                "ffmpeg -i work/input2.flv -vcodec copy -acodec copy -ss 00:03:00 -to 00:04:00 work/part_2_00-03-00_00-04-00.flv, " +
                "ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.mp4]");

        assertFile("work/list.txt",
                "[file 'part_1_00-00-00_00-00-30.flv', " +
                "file 'part_1_00-00-35_00-01-25.flv', " +
                "file 'part_1_00-01-30_00-02-00.flv', " +
                "file 'part_2_00-01-00_00-02-00.flv', " +
                "file 'part_2_00-03-00_00-04-00.flv']");
    }


}