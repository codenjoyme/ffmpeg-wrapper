package com.apofig.ffmpeg;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SplitterTest {

    @Test
    public void part() {
        // given
        Runner runner = mock(Runner.class);

        // when
        new Splitter(runner)
                .input("work/input.flv")
                .part(Splitter.Part.time("00:00:00", "00:30:00").to("work/output1.flv"))
                .part(Splitter.Part.time("01:00:00", "01:30:00").to("work/output2.flv"))
                .part(Splitter.Part.time("01:30:00", "01:40:23").to("work/output3.flv"))
                .run();

        // then
        ArgumentCaptor<String> commandCaptor = ArgumentCaptor.forClass(String.class);
        verify(runner, atLeastOnce()).exec(commandCaptor.capture());

        assertEquals("[ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 00:00:00 -to 00:30:00 work/output1.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:00:00 -to 01:30:00 work/output2.flv, " +
                "ffmpeg -i work/input.flv -vcodec copy -acodec copy -ss 01:30:00 -to 01:40:23 work/output3.flv]",
                commandCaptor.getAllValues().toString());
    }
}