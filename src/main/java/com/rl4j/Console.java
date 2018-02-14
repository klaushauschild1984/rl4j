package com.rl4j;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Console extends Backbuffer {

    private final BitmapFont bitmapFont;
    private final Character[] frontBuffer;
    private final BufferedImage renderBuffer;

    public Console(final BitmapFont bitmapFont, final Dimension size) {
        super(size);
        this.bitmapFont = bitmapFont;
        frontBuffer = new Character[size.getWidth() * size.getHeight()];
        renderBuffer = new BufferedImage( //
                size.getWidth() * bitmapFont.getTileSize().getWidth(), //
                size.getHeight() * bitmapFont.getTileSize().getHeight(),//
                BufferedImage.TYPE_INT_RGB
        );
        clear();
    }

    void flush(final Graphics2D context) {
        for (int i = 0; i < getBackBuffer().length; i++) {
            Character character = getBackBuffer()[i];

            if (Objects.equals(frontBuffer[i], character)) {
                continue;
            }
            frontBuffer[i] = character;

            final int x = i % getSize().getWidth();
            final int y = i / getSize().getWidth();
            bitmapFont.draw( //
                    renderBuffer.createGraphics(), //
                    character.getC(), //
                    x * bitmapFont.getTileSize().getWidth(), //
                    y * bitmapFont.getTileSize().getHeight(), //
                    character.getForeground(), //
                    character.getBackground() //
            );
        }

        context.drawImage(renderBuffer, null, 0, 0);
    }

}
