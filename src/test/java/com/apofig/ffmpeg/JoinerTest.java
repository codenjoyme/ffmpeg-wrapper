package com.apofig.ffmpeg;

import org.junit.Test;

public class JoinerTest extends AbstractRunnerTest {

    @Test
    public void shouldSplitThreeFiles() {
        // when
        new Joiner(runner)
                .output("work/done.flv")
                .part("work/output1.flv")
                .part("work/output2.flv")
                .part("work/output3.flv")
                .run();

        // then
        assertExec("[ffmpeg -f concat -safe 0 -i list.txt -c copy work/done.flv]");
    }

}