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

package com.rl4j.event;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class KeyboardEventProducer implements KeyListener, EventProducer {

    private final Set<Integer> pressedKeys = new HashSet<>();

    private Handler handler;

    public KeyboardEventProducer(final JFrame frame) {
        Handlers.register(this);
        frame.addKeyListener(this);
    }

    @Override
    public void attach(final Handler handler) {
        this.handler = handler;
    }

    @Override
    public void keyTyped(final KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        final boolean added = pressedKeys.add(keyEvent.getKeyCode());
        if (added) {
            handler.handle(new KeyboardEvent(KeyboardEvent.Key.valueOd(keyEvent.getKeyCode()), true));
        }
    }

    @Override
    public void keyReleased(final KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getKeyCode());
        handler.handle(new KeyboardEvent(KeyboardEvent.Key.valueOd(keyEvent.getKeyCode()), false));
    }

    @ToString
    public static class KeyboardEvent extends Event {

        private final Key key;
        private final boolean pressed;

        public KeyboardEvent(final Key key, final boolean pressed) {
            super("key");
            this.key = key;
            this.pressed = pressed;
        }

        public enum Key {

            UP(0),

            DOWN(0),

            LEFT(0),

            RIGHT(0),

            SPACE(32),

            ENTER(10),

            ESCAPE(27),

            ;

            private final int keyCode;

            Key(final int keyCode) {
                this.keyCode = keyCode;
            }

            public static Key valueOd(final int keyCode) {
                return Arrays.stream(Key.values()) //
                        .filter(key -> key.keyCode == keyCode) //
                        .findAny() //
                        .orElseGet(() -> {
                            log.error(String.format("Unknown keyCode: %s", keyCode));
                            return null;
                        });
            }

        }

    }

}
