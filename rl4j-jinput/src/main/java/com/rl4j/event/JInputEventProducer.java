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

import lombok.extern.slf4j.Slf4j;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import org.natives.Loaders;
import org.natives.NativeLibrary;
import org.natives.Platform;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

// https://codereview.stackexchange.com/questions/107143/event-based-xbox-controller-polling
@Slf4j
public class JInputEventProducer implements EventProducer {

    static {
        new JInputEventProducer();
    }

    private Handler handler;

    private JInputEventProducer() {
        new NativeLibrary("jinput") //
                .register(Platform.Windows_x86, "jinput-dx8.dll") //
                .register(Platform.Windows_x86, "jinput-raw.dll") //
                .register(Platform.Windows_x64, "jinput-dx8_64.dll") //
                .register(Platform.Windows_x64, "jinput-raw_64.dll") //
                .register(Platform.Linux_x86, "libjinput-linux.so") //
                .register(Platform.Linux_x64, "libjinput-linux64.so") //
                .register(Platform.MacOS, "libjinput-osx.jnilib") //
                .load(Loaders.JAVA_LIBRARY_PATH__LOADER);

        final Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        final Controller gamepad = Arrays.stream(controllers) //
                .filter(controller -> controller.getType() == Controller.Type.GAMEPAD) //
                .findAny() //
                .orElseGet(() -> null);

        Handlers.register(this);

        log.debug("Registered.");

        poll(gamepad);
    }

    @Override
    public void attach(final Handler handler) {
        this.handler = handler;
    }

    private void poll(final Controller controller) {
        if (controller == null) {
            return;
        }
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                controller.poll();
                final EventQueue eventQueue = controller.getEventQueue();
                final Event event = new Event();
                while (eventQueue.getNextEvent(event)) {
                    log.debug("{}", event);
                }
            }

        }, 10);
    }

}
