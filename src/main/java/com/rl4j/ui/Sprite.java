/*
 * Roguelikes 4 Java Copyright (C) 2018 Klaus Hauschild
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package com.rl4j.ui;

import com.rl4j.Backbuffer;
import com.rl4j.Console;
import com.rl4j.Dimension;
import com.rl4j.Draw;
import lombok.Setter;

import java.awt.Color;

public class Sprite extends Backbuffer implements Draw {

    private final boolean transparent;
    @Setter
    private int top;
    @Setter
    private int left;

    public Sprite(final int top, final int left, final Dimension size) {
        this(top, left, size, true);
    }

    public Sprite(final int top, final int left, final Dimension size, final boolean transparent) {
        super(size);
        this.top = top;
        this.left = left;
        this.transparent = transparent;
    }

    @Override
    public void draw(final Console console) {
        for (int column = 0; column < getSize().getWidth(); column++) {
            for (int row = 0; row < getSize().getHeight(); row++) {
                final int index = row * getSize().getWidth() + column;
                final Character character = getBackBuffer()[index];
                if (character == null) {
                    continue;
                }
                final Color background;
                if (transparent) {
                    background = console.getBackBuffer()[(top + row) * console.getSize().getWidth() + (left + column)].getBackground();
                } else {
                    background = character.getBackground();
                }
                console.put(character.getC(), left + column, top + row, character.getForeground(), background);
            }
        }
    }

}
