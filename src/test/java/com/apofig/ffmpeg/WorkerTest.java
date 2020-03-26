package com.apofig.ffmpeg;

import org.junit.Test;

import java.io.IOException;

public class WorkerTest extends AbstractRunnerTest {

    @Test
    public void shouldProcessTwoParts() throws IOException {
        // when
        worker()
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
                .replaceAudio()
                .run();

        // then
        assertExec("ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:00:30 work/part_1_00-00-00_00-00-30.flv\n" +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:35 -to 00:01:25 work/part_1_00-00-35_00-01-25.flv\n" +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:01:30 -to 00:02:00 work/part_1_00-01-30_00-02-00.flv\n" +
                "ffmpeg -i work/input2.flv -vcodec copy -acodec copy -ss 00:01:00 -to 00:02:00 work/part_2_00-01-00_00-02-00.flv\n" +
                "ffmpeg -i work/input2.flv -vcodec copy -acodec copy -ss 00:03:00 -to 00:04:00 work/part_2_00-03-00_00-04-00.flv\n" +

                "ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.mp4\n" +

                "ffmpeg -i work/done.mp4 -vn -acodec copy work/done-audio.mp4\n" +
                "ffmpeg -i work/done.mp4 -i work/done-audio.mp4 -c:v copy -map 0:v:0 -map 1:a:0 work/done-done.mp4");

        assertFile("work/list.txt",
                "file 'part_1_00-00-00_00-00-30.flv'\n" +
                "file 'part_1_00-00-35_00-01-25.flv'\n" +
                "file 'part_1_00-01-30_00-02-00.flv'\n" +
                "file 'part_2_00-01-00_00-02-00.flv'\n" +
                "file 'part_2_00-03-00_00-04-00.flv'");
    }

    private Worker worker() {
        return new Worker(runner){
            @Override
            void clean() {
                // do nothing
            }

            @Override
            void waitAudio() {
                // do nothing
            }
        };
    }


}