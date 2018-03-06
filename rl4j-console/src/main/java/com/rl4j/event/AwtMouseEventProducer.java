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

import com.rl4j.Dimension;
import com.rl4j.event.MouseEvent.MouseButtonEvent;
import com.rl4j.event.MouseEvent.MouseButtonEvent.Button;
import com.rl4j.event.MouseEvent.MouseMoveEvent;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

@Slf4j
public class AwtMouseEventProducer extends MouseAdapter {

    private final Dimension tileSize;
    private final boolean[] pressed = new boolean[3];

    private com.rl4j.event.MouseEvent lastMouseMoveEvent;

    public AwtMouseEventProducer(final Dimension tileSize, final JFrame frame) {
        this.tileSize = tileSize;
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        // TODO frame.addMouseWheelListener(this);
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        EventBus.dispatch(fromMouseEvent(event));
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        EventBus.dispatch(fromMouseEvent(event));
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        final com.rl4j.event.MouseEvent mouseMoveEvent = fromMouseEvent(e);
        if (!Objects.equals(lastMouseMoveEvent, mouseMoveEvent)) {
            EventBus.dispatch(mouseMoveEvent);
            lastMouseMoveEvent = mouseMoveEvent;
        }
    }

    private com.rl4j.event.MouseEvent fromMouseEvent(final MouseEvent event) {
        final int column = event.getX() / tileSize.getWidth();
        final int row = event.getY() / tileSize.getHeight();

        final int buttonIndex = event.getButton() - 1;
        if (buttonIndex == -1) {
            return new MouseMoveEvent(column, row);
        }

        final Button button = Button.values()[buttonIndex];
        pressed[buttonIndex] = !pressed[buttonIndex];
        return new MouseButtonEvent(column, row, button, pressed[buttonIndex]);
    }

}
