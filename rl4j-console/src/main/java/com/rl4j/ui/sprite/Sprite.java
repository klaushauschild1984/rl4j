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
package com.rl4j.ui.sprite;

import com.rl4j.BackBuffer;
import com.rl4j.Character;
import com.rl4j.Dimension;
import com.rl4j.Draw;
import lombok.Setter;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Sprite extends BackBuffer implements Draw {

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

    public Sprite(final InputStream inputStream) throws IOException {
        this(new ObjectInputStream(new BufferedInputStream(inputStream)));
    }

    private Sprite(final ObjectInputStream objectInputStream) throws IOException {
        this(0, 0, readSize(objectInputStream), readTransparent(objectInputStream));
        for (int i = 0; i < getSize().getWidth() * getSize().getHeight(); i++) {
            final char c = objectInputStream.readChar();
            final Color foreground = new Color(objectInputStream.readInt());
            Color background = null;
            if (!transparent) {
                background = new Color(objectInputStream.readInt());
            }
            getBackBuffer()[i] = new Character(c, foreground, background);
        }
    }

    private static Dimension readSize(final ObjectInputStream objectInputStream) throws IOException {
        final int width = objectInputStream.readInt();
        final int height = objectInputStream.readInt();
        return new Dimension(width, height);
    }

    private static boolean readTransparent(final ObjectInputStream objectInputStream) throws IOException {
        return objectInputStream.readBoolean();
    }

    @Override
    public void draw(final BackBuffer console) {
        for (int column = 0; column < getSize().getWidth(); column++) {
            for (int row = 0; row < getSize().getHeight(); row++) {
                final int index = row * getSize().getWidth() + column;
                final Character character = getBackBuffer()[index];
                if (character == null) {
                    continue;
                }
                final Color background;
                if (transparent) {
                    background = console.get(left + column, top + row).getBackground();
                } else {
                    background = character.getBackground();
                }
                console.put(character.getC(), left + column, top + row, character.getForeground(), background);
            }
        }
    }

    public void save(final OutputStream outputStream) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(outputStream))) {
            // header
            objectOutputStream.writeInt(getSize().getWidth());
            objectOutputStream.writeInt(getSize().getHeight());
            objectOutputStream.writeBoolean(transparent);

            // body
            for (final Character character : getBackBuffer()) {
                objectOutputStream.writeChar(character.getC());
                objectOutputStream.writeInt(character.getForeground().getRGB());
                if (transparent) {
                    continue;
                }
                objectOutputStream.writeInt(character.getBackground().getRGB());
            }
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
