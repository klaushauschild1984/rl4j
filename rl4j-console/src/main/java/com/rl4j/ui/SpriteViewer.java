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

import com.rl4j.BackBuffer;
import com.rl4j.Game;
import com.rl4j.Roguelike;
import com.rl4j.event.Event;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SpriteViewer {

    public static void main(final String[] args) throws IOException {
        if (args.length < 1) {
            throw new IllegalArgumentException("No sprite file provided.");
        }
        final File spriteFile = new File(args[0]);
        final Sprite sprite = loadSprite(spriteFile);
        final Roguelike spriteViewer = Roguelike.builder() //
                .title(spriteFile.getName()) //
                .fpsLimit(30) //
                .borderless(true) //
                .nativeCursor(false) //
                .size(sprite.getSize()) //
                .build();
        spriteViewer.start(new Game() {

            @Override
            public void draw(final BackBuffer console) {
                sprite.draw(console);
            }

            @Override
            public void update(final float elapsed) {

            }

            @Override
            public void handle(final Event event) {

            }

        });
    }

    private static Sprite loadSprite(final File spriteFile) throws IOException {
        try (final InputStream inputStream = new BufferedInputStream(new FileInputStream(spriteFile))) {
            return new Sprite(inputStream);
        }
    }

}
