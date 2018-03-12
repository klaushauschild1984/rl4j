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

import com.rl4j.Dimension;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SpriteWriter {

    public static void main(final String[] args) throws IOException {
        final Sprite sprite = new Sprite(0, 0, new Dimension(38, 7), false);
        sprite.setForeground(Color.YELLOW);
        sprite.setBackground(Color.RED);
        sprite.put(".########..##.......##..............##", 0, 0);
        sprite.put(".##.....##.##.......##....##........##", 0, 1);
        sprite.put(".##.....##.##.......##....##........##", 0, 2);
        sprite.put(".########..##.......##....##........##", 0, 3);
        sprite.put(".##...##...##.......#########.##....##", 0, 4);
        sprite.put(".##....##..##.............##..##....##", 0, 5);
        sprite.put(".##.....##.########.......##...######.", 0, 6);
        final File spriteFile = new File("target", "rl4j.sprite");
        spriteFile.getParentFile().mkdirs();
        try (final FileOutputStream fileOutputStream = new FileOutputStream(spriteFile)) {
            sprite.save(fileOutputStream);
        }
    }

}
