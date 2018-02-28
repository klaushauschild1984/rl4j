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

import java.util.Collection;
import java.util.HashSet;

public class Handlers {

    private static final Collection<EventProducer> EVENT_PRODUCERS = new HashSet<>();

    public static void register(final EventProducer eventProducer) {
        EVENT_PRODUCERS.add(eventProducer);
    }

    public static void attach(final Handler handler) {
        EVENT_PRODUCERS.forEach(eventProducer -> eventProducer.attach(handler));
    }

}
