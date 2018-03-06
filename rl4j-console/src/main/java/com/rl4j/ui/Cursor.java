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
import com.rl4j.BackBuffer.Character;
import com.rl4j.Draw;
import com.rl4j.Update;
import com.rl4j.event.Event;
import com.rl4j.event.EventBus;
import com.rl4j.event.Handler;
import com.rl4j.event.MouseEvent.MouseMoveEvent;
import lombok.Getter;
import lombok.Setter;

public class Cursor implements Update, Draw, Handler {

    private final boolean nativeCursor;
    private float time;
    private boolean blink = true;
    @Setter
    private float blinkInterval = 0.25f;
    @Getter
    @Setter
    private int row;
    @Getter
    @Setter
    private int column;

    public Cursor(final boolean nativeCursor) {
        this.nativeCursor = nativeCursor;
        EventBus.register(this);
        hide();
    }

    @Override
    public void draw(final BackBuffer console) {
        final Character character = console.get(column, row);
        if (character == null) {
            return;
        }
        if (blink) {
            console.put(character.getC(), column, row, character.getBackground(), character.getForeground());
        }
    }

    @Override
    public void update(final float elapsed) {
        time += elapsed;
        if (blinkInterval > 0 && time > blinkInterval) {
            blink = !blink;
            time = 0;
        }
    }

    @Override
    public void handle(final Event event) {
        event.as(MouseMoveEvent.class) //
                .ifPresent(mouseMoveEvent -> {
                    if (!nativeCursor) {
                        return;
                    }
                    column = mouseMoveEvent.getColumn();
                    row = mouseMoveEvent.getRow();
                });
    }

    public void hide() {
        column = -1;
        row = -1;
    }

}
