package com.rl4j.ui;

import com.rl4j.Console;
import com.rl4j.Draw;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.awt.Color;

@RequiredArgsConstructor
public class Box implements Draw {

    private final int column;
    private final int row;
    private final int width;
    private final int height;

    @Setter
    private Color foreground;
    @Setter
    private Color background;
    @Setter
    private String sliceNine = "╔═╗║ ║╚═╝";
    private String title;
    @Setter
    private boolean fill = true;

    @Override
    public void draw(final Console console) {
        if (foreground == null) {
            foreground = console.getForeground();
        }
        if (background == null) {
            background = console.getBackground();
        }

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                Character character = null;
                if (column == 0) {
                    character = sliceNine.charAt(3);
                } else if (column == width - 1) {
                    character = sliceNine.charAt(5);
                } else if (row == 0) {
                    character = sliceNine.charAt(1);
                } else if (row == height - 1) {
                    character = sliceNine.charAt(7);
                } else if (fill) {
                    character = sliceNine.charAt(4);
                }
                if (character != null) {
                    console.put(character, this.column + column, this.row + row, foreground, background);
                }
            }
        }

        console.put(sliceNine.charAt(0), column, row, foreground, background);
        console.put(sliceNine.charAt(2), column + width - 1, row, foreground, background);
        console.put(sliceNine.charAt(6), column, row + height - 1, foreground, background);
        console.put(sliceNine.charAt(8), column + width - 1, row + height - 1, foreground, background);

        if (title != null) {
            final int titleColumn = column + (width / 2 - title.length() / 2);
            console.put(title, titleColumn, row, foreground, background);
        }
    }

    public void setTitle(final String title) {
        this.title = title.substring(0, Math.min(width - 2, title.length()));
    }

}
