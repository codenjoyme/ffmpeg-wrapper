package com.apofig.ffmpeg;

import org.junit.Test;

import java.io.IOException;

public class JoinerTest extends AbstractRunnerTest {

    @Test
    public void shouldSplitThreeFiles() throws IOException {
        // when
        new Joiner(runner)
                .output("work/done.flv")
                .part("work/output1.flv")
                .part("work/output2.flv")
                .part("work/output3.flv")
                .run();

        // then
        assertExec("[ffmpeg -f concat -safe 0 -i work/list.txt -c copy work/done.flv]");
        assertFile("work/list.txt",
                "[file 'output1.flv', " +
                        "file 'output2.flv', " +
                        "file 'output3.flv']");
    }

}