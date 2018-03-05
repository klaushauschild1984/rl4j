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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@Getter
@ToString
@RequiredArgsConstructor
public class KeyboardEvent implements Event {

    private final Key key;
    private final boolean pressed;

    public enum Key {

        // arrow keys
        LEFT(37),
        UP(38),
        RIGHT(39),
        DOWN(40),

        SPACE(32),
        ENTER(10),
        ESCAPE(27),
        CRTL(17),

        // letters
        A(65),
        W(87),
        D(68),
        S(83),

        //
        ;

        private final int keyCode;

        Key(final int keyCode) {
            this.keyCode = keyCode;
        }

        public static Key valueOf(final int keyCode) {
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
