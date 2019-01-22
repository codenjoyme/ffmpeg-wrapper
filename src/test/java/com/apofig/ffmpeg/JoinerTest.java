package com.apofig.ffmpeg;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
                "[file 'work/output1.flv', " +
                        "file 'work/output2.flv', " +
                        "file 'work/output3.flv']");
    }

    private void assertFile(String file, String expected) throws IOException {
        List<String> actual = IOUtils.readLines(new InputStreamReader(new FileInputStream(file), "utf-8"));
        assertEquals(expected, actual.toString());
    }

}