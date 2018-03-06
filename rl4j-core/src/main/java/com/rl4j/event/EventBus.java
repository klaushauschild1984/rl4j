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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public enum EventBus {

    ;

    private static final Logger allEventsLogger = LoggerFactory.getLogger(EventBus.class.getPackage().getName());
    private static final Logger keyboardEventsLogger = LoggerFactory.getLogger(EventBus.class.getPackage().getName() + ".keyboard");
    private static final Logger mouseEventsLogger = LoggerFactory.getLogger(EventBus.class.getPackage().getName() + ".mouse");

    private static final List<Handler> HANDLERS = new ArrayList<>();

    public static void register(final Handler handler) {
        if (HANDLERS.contains(handler)) {
            return;
        }
        HANDLERS.add(handler);
    }

    public static void dispatch(final Event event) {
        if (allEventsLogger.isDebugEnabled()) {
            allEventsLogger.debug("{}", event);
        } else {
            event.as(KeyboardEvent.class)
                    .ifPresent(keyboardEvent -> keyboardEventsLogger.debug("{}", keyboardEvent));
            event.as(MouseEvent.class)
                    .ifPresent(mouseEvent -> mouseEventsLogger.debug("{}", mouseEvent));
        }

        HANDLERS.forEach(handler -> handler.handle(event));
    }

}
