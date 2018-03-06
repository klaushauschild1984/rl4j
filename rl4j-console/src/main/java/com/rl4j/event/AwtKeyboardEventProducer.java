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

import com.rl4j.event.KeyboardEvent.Key;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AwtKeyboardEventProducer implements KeyListener {

    private final Set<Integer> pressedKeys = new HashSet<>();

    public AwtKeyboardEventProducer(final JFrame frame) {
        frame.addKeyListener(this);
    }

    @Override
    public void keyTyped(final KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(final KeyEvent keyEvent) {
        final boolean added = pressedKeys.add(keyEvent.getKeyCode());
        if (added) {
            final Key key = Key.valueOf(keyEvent.getKeyCode());
            if (key != null) {
                EventBus.dispatch(new KeyboardEvent(key, true));
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getKeyCode());
        final Key key = Key.valueOf(keyEvent.getKeyCode());
        if (key != null) {
            EventBus.dispatch(new KeyboardEvent(key, false));
        }
    }

}
