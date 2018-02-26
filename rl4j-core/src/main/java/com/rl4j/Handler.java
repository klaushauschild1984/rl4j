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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@FunctionalInterface
public interface Handler {

    void handle(Event event);

    @FunctionalInterface
    interface EventProducer {

        void attach(Handler handler);

    }

    @RequiredArgsConstructor
    @ToString
    class Event {

        @Getter
        private final String type;

    }

}
