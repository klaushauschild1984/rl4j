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
package com.rl4j.font;

import com.rl4j.Character;
import com.rl4j.Dimension;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class BitmapFont {

    private final CodePage437 codePage437 = new CodePage437();
    private final Map<Character, BufferedImage> characters = new HashMap<>();
    private final BufferedImage bitmap;
    @Getter
    private final Dimension tileSize;

    public static BitmapFont create(final InputStream inputStream, final Dimension tileSize) {
        try {
            final BufferedImage bitmap = ImageIO.read(inputStream);
            if (bitmap.getWidth() % tileSize.getWidth() != 0) {
                throw new IllegalArgumentException();
            }
            if (bitmap.getHeight() % tileSize.getHeight() != 0) {
                throw new IllegalArgumentException();
            }
            return new BitmapFont(bitmap, tileSize);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void draw(final Graphics2D context, final Character character, final int x, final int y) {
        final BufferedImage characterImage = drawCharacter(character);
        context.drawImage(characterImage, x, y, null);
    }

    private BufferedImage drawCharacter(final Character character) {
        if (!characters.containsKey(character)) {
            final int index = codePage437.getOrDefault(character.getC(), (int) character.getC());
            final int w = index * tileSize.getWidth();
            final int charX = w % bitmap.getWidth();
            final int charY = (w / bitmap.getWidth()) * tileSize.getHeight();
            final BitmapFontFilter bitmapFontFilter = new BitmapFontFilter(tileSize, charX, charY, character.getForeground(), character.getBackground());
            final BufferedImage characterImage = new BufferedImage(tileSize.getWidth(), tileSize.getHeight(), BufferedImage.TYPE_INT_RGB);
            characterImage.createGraphics().drawImage(bitmap, bitmapFontFilter, 0, 0);
            characters.put(character, characterImage);
        }
        return characters.get(character);
    }

}
