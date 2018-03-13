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
package com.rl4j.ui.sprite;

import com.rl4j.BackBuffer;
import com.rl4j.Draw;
import com.rl4j.Update;

import java.util.List;
import java.util.Map.Entry;

public class Animation implements Update, Draw {

    private final List<Entry<Sprite, Float>> frames;
    private final Playback playback;
    private final float totalTime;

    private float currentTime;
    private int direction = 1;

    public Animation(final List<Entry<Sprite, Float>> frames) {
        this(frames, Playback.LOOP);
    }

    public Animation(final List<Entry<Sprite, Float>> frames, final Playback playback) {
        this.frames = frames;
        this.playback = playback;
        this.totalTime = this.frames.stream().map(Entry::getValue).reduce((a, b) -> a + b).get();
    }

    @Override
    public void draw(final BackBuffer console) {
        int frame = 0;
        float frameTime = 0f;
        while (frameTime <= currentTime) {
            frameTime += frames.get(frame).getValue();
            frame++;
        }
        frames.get(frame - 1).getKey().draw(console);
    }

    @Override
    public void update(final float elapsed) {
        currentTime += direction * elapsed;
        switch (playback) {
            case LOOP:
                while (currentTime > totalTime) {
                    currentTime = currentTime - totalTime;
                }
                break;
            case SINGLE:
                // TODO need fixing
                if (currentTime > totalTime) {
                    currentTime = totalTime;
                }
                break;
            case FORWARD_BACKWARD:
                // TODO need fixing
                if (currentTime > totalTime) {
                    currentTime = totalTime - (currentTime - totalTime);
                } else if (currentTime < 0) {
                    currentTime = -currentTime;
                }
                direction *= -1;
                break;
        }
    }

    public enum Playback {

        LOOP,

        SINGLE,

        FORWARD_BACKWARD,;

    }

}
