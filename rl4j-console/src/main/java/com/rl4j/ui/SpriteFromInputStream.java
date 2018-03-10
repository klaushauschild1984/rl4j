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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Creates a {@link Sprite} from an {@link InputStream} typically read from a text file.
 * <p>
 * The meta file format is:
 * <pre>
 * [optional foreground]
 * [optional background]
 * [character array]
 *
 * [optional foreground array]
 * [optional background array]
 * </pre>
 * A color is coded like: <code>([RED],[GREEN],[BLUE])</code>
 * </p>
 * The general foreground and background can be either omitted or represented as blank line. The blank line is useful as
 * background. In this case the resulting sprite will be transparent and will use as background the background color of
 * the underlying backbuffer.<br/>
 * If foreground and/or background array is specified it will override the general foreground and/or background. The
 * specified arrays has to have the same size like the character array.
 *
 * @author Klaus Hauschild
 */
public class SpriteFromInputStream {

    public static Sprite load(final InputStream inputStream) {
        final List<String> lines = readLines(inputStream);
        final Color foreground = readColor(lines);
        final Color background = readColor(lines);
        final Sprite sprite = new Sprite(0, 0, new Dimension(lines.get(0).length(), lines.size()), background != null);
        sprite.setForeground(foreground);
        sprite.setBackground(background);
        final Color[][] foregrounds = readColorArray(lines, foreground);
        final Color[][] backgrounds = readColorArray(lines, background);

        IntStream.range(0, lines.size()) //
                .forEach(row -> {
                    final String line = lines.get(row);
                    IntStream.range(0, line.length()) //
                            .forEach(column -> {
                                final char c = line.charAt(column);
                                sprite.put(c, column, row, foregrounds[column][row], backgrounds[column][row]);
                            });
                });
        return sprite;
    }

    private static Color[][] readColorArray(final List<String> lines, final Color color) {
        if (!lines.contains("")) {
            // color array is not present -> created it from default color
            final int rows = lines.size();
            final int columns = lines.get(0).length();
            final Color[][] colors = new Color[columns][];
            for (int column = 0; column < columns; column++) {
                colors[column] = new Color[rows];
                Arrays.fill(colors[column], color);
            }
            return colors;
        }
        // color array is present -> read it and remove it
        // TODO

        return null;
    }

    private static Color readColor(final List<String> lines) {
        final String line = lines.get(0);
        if (line.isEmpty()) {
            lines.remove(0);
            return null;
        }
        if (!(line.startsWith("(") && line.endsWith(")"))) {
            return null;
        }
        lines.remove(0);
        return readColor(line);
    }

    private static Color readColor(final String line) {
        final String[] colorValues = line.replace("(", "").replace(")", "").split(",");
        final List<Integer> color = Arrays.stream(colorValues) //
                .map(Integer::parseInt) //
                .collect(Collectors.toList());
        return new Color(color.get(0), color.get(1), color.get(2));
    }

    private static List<String> readLines(final InputStream inputStream) {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            final List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
