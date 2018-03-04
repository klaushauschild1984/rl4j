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

import com.rl4j.event.Event;
import com.rl4j.event.KeyboardEvent;
import com.rl4j.event.KeyboardEvent.Key;
import com.rl4j.event.MouseEvent.MouseButtonEvent;
import com.rl4j.ui.Animation;
import com.rl4j.ui.Box;
import com.rl4j.ui.Sprite;

import java.awt.Color;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

public class RoguelikeTest {

    public static void main(final String[] args) {
        final Roguelike roguelike = Roguelike.builder() //
                .title("Roguelikes 4 Java") //
                .fpsLimit(30) //
                .borderless(true) //
                .build();
        final Test test = new Test(roguelike);
        roguelike.start(test);
    }

    private static class Test implements Game {

        private final Box box;
        private final Animation animation;
        private Roguelike roguelike;
        private float fps;
        private String mouseButton = "";

        public Test(final Roguelike roguelike) {
            this.roguelike = roguelike;
            box = new Box(0, 0, roguelike.getSize().getWidth(), roguelike.getSize().getHeight());
            box.setTitle("Roguelikes 4 Java");
            box.setFill(false);

            final Sprite sprite0 = new Sprite(2, 2, new Dimension(3, 3));
            sprite0.put("###", 0, 0, Color.RED);
            final Sprite sprite1 = new Sprite(2, 2, new Dimension(3, 3));
            sprite1.put("###", 0, 1, Color.RED);
            final Sprite sprite2 = new Sprite(2, 2, new Dimension(3, 3));
            sprite2.put("###", 0, 2, Color.RED);
            animation = new Animation(Arrays.asList( //
                    new SimpleEntry<>(sprite0, .3f), //
                    new SimpleEntry<>(sprite1, .3f), //
                    new SimpleEntry<>(sprite2, .3f) //
            ));
        }

        @Override
        public void draw(final Backbuffer console) {
            console.clear(Color.BLUE);
            box.draw(console);
            console.put(String.format("%.0ffps", fps), console.getSize().getWidth() - 6, console.getSize().getHeight() - 1);

            animation.draw(console);
            console.put(mouseButton, 10, 10);
        }

        @Override
        public void update(final float elapsed) {
            fps = 1 / elapsed;
            animation.update(elapsed);
        }

        @Override
        public void handle(final Event event) {
            event.as(KeyboardEvent.class) //
                    .ifPresent(keyboardEvent -> {
                        if (((KeyboardEvent) event).getKey() == Key.ESCAPE) {
                            roguelike.stop();
                        }
                    });

            event.as(MouseButtonEvent.class) //
                    .ifPresent(mouseButtonEvent -> {
                        if (!mouseButtonEvent.isPressed()) {
                            return;
                        }
                        mouseButton = String.format("%s: %s, %s", mouseButtonEvent.getButton(), mouseButtonEvent.getColumn(), mouseButtonEvent.getRow());
                    });
        }

    }

}