package com.apofig.ffmpeg;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class Splitter2Test {

    private Runner runner;

    @Before
    public void setUp() {
        runner = mock(Runner.class);
    }

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

    private void assertExec(String expected) {
        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(runner, atLeastOnce()).exec(commandCaptor.capture());
        assertEquals(expected, commandCaptor.getAllValues().toString());
    }
}