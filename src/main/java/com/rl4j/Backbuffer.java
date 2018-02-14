package com.rl4j;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.awt.Color;

public abstract class Backbuffer {

    @Getter
    private final Dimension size;
    @Getter
    private final Character[] backBuffer;
    @Getter
    @Setter
    private Color foreground = Color.WHITE;
    @Getter
    @Setter
    private Color background = Color.BLACK;

    public Backbuffer(final Dimension size) {
        this.size = size;
        backBuffer = new Character[size.getWidth() * size.getHeight()];
    }

    public void put(final char c, final int column, final int row) {
        put(c, column, row, foreground, background);
    }

    public void put(final char c, final int column, final int row, final Color foreground) {
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

    public void put(final String s, final int column, final int row, final Color foreground) {
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

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class Character {

        private final char c;
        private final Color foreground;
        private final Color background;

    }

}
