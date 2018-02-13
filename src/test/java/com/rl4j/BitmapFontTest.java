package com.rl4j;

import org.testng.annotations.Test;

public class BitmapFontTest {

    @Test
    public void bitmapFontTest() {
        BitmapFont.create(getClass().getResourceAsStream("font32x32.png"), new Dimension(32, 32));
    }

}