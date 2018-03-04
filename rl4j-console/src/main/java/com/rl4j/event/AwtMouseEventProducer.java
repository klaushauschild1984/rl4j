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
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Slf4j
public class AwtMouseEventProducer extends MouseAdapter implements EventProducer {

    private final Dimension tileSize;
    private final boolean[] pressed = new boolean[3];

    private Handler handler;

    public AwtMouseEventProducer(final Dimension tileSize, final JFrame frame) {
        this.tileSize = tileSize;
        Handlers.register(this);
        frame.addMouseListener(this);
        // TODO frame.addMouseMotionListener(this);
        // TODO frame.addMouseWheelListener(this);
    }

    @Override
    public void attach(final Handler handler) {
        this.handler = handler;
    }

    @Override
    public void mousePressed(final MouseEvent event) {
        handler.handle(fromMouseEvent(event));
    }

    @Override
    public void mouseReleased(final MouseEvent event) {
        handler.handle(fromMouseEvent(event));
    }

    private MouseButtonEvent fromMouseEvent(final MouseEvent event) {
        final int buttonIndex = event.getButton() - 1;
        final Button button = Button.values()[buttonIndex];
        pressed[buttonIndex] = !pressed[buttonIndex];
        final int column = event.getX() / tileSize.getWidth();
        final int row = event.getY() / tileSize.getHeight();
        return new MouseButtonEvent(button, pressed[buttonIndex], column, row);
    }

}
