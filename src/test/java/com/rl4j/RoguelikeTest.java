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
package com.rl4j;

import com.rl4j.ui.Box;

public class RoguelikeTest {

    public static void main(final String[] args) {
        final Roguelike roguelike = Roguelike.builder() //
                .title("Roguelike 4 Java") //
                .fpsLimit(30) //
                .build();
        final Game game = new Game(roguelike.getSize());
        roguelike.start(game, game);
    }

    private static class Game implements Update, Draw {

        private final Box box;

        private float fps;

        public Game(final Dimension size) {
            box = new Box(0, 0, size.getWidth(), size.getHeight());
            box.setTitle("Roguelike 4 Java");
        }

        @Override
        public void draw(final Console console) {
            console.clear();
            box.draw(console);
            console.put(String.format("%.0ffps", fps), console.getSize().getWidth() - 6, console.getSize().getHeight() - 1);
        }

        @Override
        public void update(final float elapsed) {
            fps = 1 / elapsed;
        }

    }

}