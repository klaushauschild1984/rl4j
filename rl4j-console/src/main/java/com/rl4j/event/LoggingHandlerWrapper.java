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

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class LoggingHandlerWrapper implements Handler {

    private static final Logger allEventsLogger = LoggerFactory.getLogger(LoggingHandlerWrapper.class.getPackage().getName());
    private static final Logger keyboardEventsLogger = LoggerFactory.getLogger(LoggingHandlerWrapper.class.getPackage().getName() + ".keyboard");
    private static final Logger mouseEventsLogger = LoggerFactory.getLogger(LoggingHandlerWrapper.class.getPackage().getName() + ".mouse");

    private final Handler handler;

    @Override
    public void handle(final Event event) {
        if (allEventsLogger.isDebugEnabled()) {
            allEventsLogger.debug("{}", event);
        } else {
            event.as(KeyboardEvent.class)
                    .ifPresent(keyboardEvent -> keyboardEventsLogger.debug("{}", keyboardEvent));
            event.as(MouseEvent.class)
                    .ifPresent(mouseEvent -> mouseEventsLogger.debug("{}", mouseEvent));
        }
        handler.handle(event);
    }

}
