package com.rl4j;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Console {

    private final BitmapFont bitmapFont;
    @Getter
    private final Dimension size;
    private final Character[] frontBuffer;
    private final Character[] backBuffer;
    private final BufferedImage renderBuffer;
    @Getter
    @Setter
    private Color foreground = Color.WHITE;
    @Getter
    @Setter
    private Color background = Color.BLACK;

    public Console(final BitmapFont bitmapFont, final Dimension size) {
        this.bitmapFont = bitmapFont;
        this.size = size;
        frontBuffer = new Character[size.getWidth() * size.getHeight()];
        backBuffer = new Character[size.getWidth() * size.getHeight()];
        renderBuffer = new BufferedImage( //
                size.getWidth() * bitmapFont.getTileSize().getWidth(), //
                size.getHeight() * bitmapFont.getTileSize().getHeight(),//
                BufferedImage.TYPE_INT_RGB
        );
        clear();
    }

    public void put(final char c, final int column, final int row) {
        put(c, column, row, foreground, background);
    }

    public void put(final char c, final int column, final int row, final Color foreground, final Color background) {
        if (!contains(column, row)) {
            return;
        }
        final Character character = new Character(c, foreground, background);
        backBuffer[row * size.getWidth() + column] = character;
    }

    public void put(final String s, final int column, final int row) {
        put(s, column, row, foreground, background);
    }

    public void put(final String s, final int column, final int row, final Color foreground, final Color background) {
        for (int i = 0; i < s.length(); i++) {
            put(s.charAt(i), column + i, row, foreground, background);
        }
    }

    public void clear() {
        fill(' ', foreground, background);
    }

    public void clear(final Color background) {
        fill(' ', foreground, background);
    }

    public void fill(final char c) {
        fill(c, foreground, background);
    }

    public void fill(final char c, final Color foreground, final Color background) {
        fill(c, 0, 0, size.getWidth(), size.getHeight(), foreground, background);
    }

    public void fill(final char c, final int column, final int row, final int width, final int height) {
        fill(c, column, row, width, height, foreground, background);
    }

    public void fill(final char c, final int column, final int row, final int width, final int height, final Color foreground, final Color background) {
        for (int y = row; y < height; y++) {
            for (int x = column; x < width; x++) {
                put(' ', x, y, foreground, background);
            }
        }
    }

    public boolean contains(final int column, final int row) {
        if (column < 0) {
            return false;
        }
        if (row < 0) {
            return false;
        }
        if (column >= size.getWidth()) {
            return false;
        }
        if (row >= size.getHeight()) {
            return false;
        }
        return true;
    }

    void flush(final Graphics2D context) {
        for (int i = 0; i < backBuffer.length; i++) {
            Character character = backBuffer[i];

            if (Objects.equals(frontBuffer[i], character)) {
                continue;
            }
            frontBuffer[i] = character;

            final int x = i % size.getWidth();
            final int y = i / size.getWidth();
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

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    private static class Character {

        private final char c;
        private final Color foreground;
        private final Color background;

    }

}
