package com.apofig.ffmpeg;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class CutterTest extends AbstractRunnerTest {

    @Test
    public void shouldCutOnePart() throws IOException {
        // when
        cutter()
                .input("work/input.flv")
                .output("work/done.flv")
                .start("00:00:00")
                .cut("00:30:00", "01:00:00")
                .end("01:40:23")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/part_00-00-00_00-30-00.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:00:00 -to 01:40:23 work/part_01-00-00_01-40-23.flv, " +
                "ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.flv]");

        assertFile("work/list.txt",
                "[file 'part_00-00-00_00-30-00.flv', " +
                "file 'part_01-00-00_01-40-23.flv']");
    }

    private Cutter cutter() {
        return new Cutter(runner){
            @Override
            void clean() {
                // do nothing
            }
        };
    }

    @Test
    public void shouldCutThreeParts() throws IOException {
        // when
        cutter()
                .input("work/input.flv")
                .output("work/done.flv")
                .start("00:00:00")
                .cut("00:10:00", "00:20:00")
                .cut("00:30:00", "00:40:00")
                .cut("00:50:00", "01:00:00")
                .end("01:10:00")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:10:00 work/part_00-00-00_00-10-00.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:20:00 -to 00:30:00 work/part_00-20-00_00-30-00.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:40:00 -to 00:50:00 work/part_00-40-00_00-50-00.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:00:00 -to 01:10:00 work/part_01-00-00_01-10-00.flv, " +
                "ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.flv]");

        assertFile("work/list.txt",
                "[file 'part_00-00-00_00-10-00.flv', " +
                "file 'part_00-20-00_00-30-00.flv', " +
                "file 'part_00-40-00_00-50-00.flv', " +
                "file 'part_01-00-00_01-10-00.flv']");
    }

    @Test
    public void shouldWithoutCut() throws IOException {
        // when
        cutter()
                .input("work/input.flv")
                .output("work/done.flv")
                .start("00:00:00")
                // TODO сказать что ошибка, потому что смысл тулзы как раз в вырезании
                .end("01:00:00")
                .run();

        // then
        assertExec("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 01:00:00 work/part_00-00-00_01-00-00.flv, " +
                "ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.flv]");

        assertFile("work/list.txt",
                    "[file 'part_00-00-00_01-00-00.flv']");
    }



}