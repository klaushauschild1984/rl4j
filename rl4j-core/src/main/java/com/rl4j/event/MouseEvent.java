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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class MouseEvent implements Event {

    private final int column;
    private final int row;


    @ToString(callSuper = true)
    @EqualsAndHashCode(callSuper = true)
    public static class MouseMoveEvent extends MouseEvent {

        public MouseMoveEvent(final int column, final int row) {
            super(column, row);
        }

    }

    @Getter
    @EqualsAndHashCode(callSuper = true)
    @ToString
    public static class MouseButtonEvent extends MouseEvent {

        private final Button button;
        private final boolean pressed;

        public MouseButtonEvent(final int column, final int row, final Button button, final boolean pressed) {
            super(column, row);
            this.button = button;
            this.pressed = pressed;
        }

        public enum Button {

            LEFT,

            MIDDLE,

            RIGHT,;

        }

    }

}
