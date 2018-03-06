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
import com.rl4j.Draw;
import com.rl4j.Update;

import java.util.Map;

public class AnimatedSprite implements Update, Draw {

    private final Map<String, Animation> phases;

    private Animation animation;

    public AnimatedSprite(final Map<String, Animation> phases, final String initialPhase) {
        this.phases = phases;
        setPhase(initialPhase);
    }

    @Override
    public void draw(final BackBuffer console) {
        animation.draw(console);
    }

    @Override
    public void update(final float elapsed) {
        animation.update(elapsed);
    }

    public void setPhase(final String phase) {
        if (!phases.containsKey(phase)) {
            throw new IllegalArgumentException(String.format("Phase %s does not exist.", phase));
        }
        animation = phases.get(phase);
    }

}
