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
import com.rl4j.GameObject;
import com.rl4j.Roguelike;
import com.rl4j.event.Event;
import com.rl4j.event.MouseEvent.MouseButtonEvent;
import com.rl4j.event.MouseEvent.MouseButtonEvent.Button;
import com.rl4j.event.MouseEvent.MouseMoveEvent;
import com.rl4j.font.CodePage437;
import com.rl4j.ui.Box;

import java.awt.Color;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

public class SpriteEditor implements GameObject {

    private final Box toolsBox = new Box(0, 0, 18, 30).withTitle("Tools");
    private final CodePage437 codePage437 = new CodePage437();

    private Integer highlightedIndex;
    private Integer selectedIndex = codePage437.get(' ');
    private boolean incrementForegroundRed;
    private boolean incrementForegroundGreen;
    private boolean incrementForegroundBlue;
    private boolean incrementBackgroundRed;
    private boolean incrementBackgroundGreen;
    private boolean incrementBackgroundBlue;
    private boolean decrementForegroundRed;
    private boolean decrementForegroundGreen;
    private boolean decrementForegroundBlue;
    private boolean decrementBackgroundRed;
    private boolean decrementBackgroundGreen;
    private boolean decrementBackgroundBlue;
    private Color foreground = Color.WHITE;
    private Color background = Color.BLACK;

    public static void main(final String[] args) {
        final Roguelike spriteEditor = Roguelike.builder() //
                .title("Sprite Editor") //
                .fpsLimit(30) //
                .borderless(true) //
                .nativeCursor(false) //
                .build();
        spriteEditor.start(new SpriteEditor());
    }

    @Override
    public void draw(final BackBuffer console) {
        toolsBox.draw(console);
        console.put("Glyph:", 1, 1);
        IntStream.range(0, 256) //
                .forEach(index -> {
                    findCharacter(index) //
                            .ifPresent(character -> {
                                Color foreground = Color.WHITE;
                                Color background = Color.BLACK;
                                if (selectedIndex != null && selectedIndex.equals(index)) {
                                    foreground = this.foreground;
                                    background = this.background;
                                }
                                if (highlightedIndex != null && highlightedIndex.equals(index)) {
                                    foreground = Color.BLACK;
                                    background = Color.WHITE;
                                }
                                console.put(character, (index % 16) + 1, ((int) (index / 16f) + 2), foreground, background);
                            });
                });
        console.put("Foreground:", 1, 19);
        console.put(' ', 13, 19, Color.WHITE, foreground);
        console.put(String.format("red:   < %s >", pad(foreground.getRed())), 2, 20);
        console.put(String.format("green: < %s >", pad(foreground.getGreen())), 2, 21);
        console.put(String.format("blue:  < %s >", pad(foreground.getBlue())), 2, 22);
        console.put("Background:", 1, 24);
        console.put(' ', 13, 24, Color.WHITE, background);
        console.put(String.format("red:   < %s >", pad(background.getRed())), 2, 25);
        console.put(String.format("green: < %s >", pad(background.getGreen())), 2, 26);
        console.put(String.format("blue:  < %s >", pad(background.getBlue())), 2, 27);
    }

    private String pad(final int i) {
        return String.format("%3s", i);
    }

    private Optional<Character> findCharacter(final Integer index) {
        return codePage437.entrySet().stream() //
                .filter(entry -> entry.getValue().equals(index)) //
                .findAny() //
                .map(Map.Entry::getKey);
    }

    @Override
    public void update(final float elapsed) {
        try {
            // increment
            if (incrementForegroundRed) {
                foreground = new Color(foreground.getRed() + 1, foreground.getGreen(), foreground.getBlue());
                Thread.sleep(50);
            }
            if (incrementForegroundGreen) {
                foreground = new Color(foreground.getRed(), foreground.getGreen() + 1, foreground.getBlue());
                Thread.sleep(50);
            }
            if (incrementForegroundBlue) {
                foreground = new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue() + 1);
                Thread.sleep(50);
            }
            if (incrementBackgroundRed) {
                background = new Color(background.getRed() + 1, background.getGreen(), background.getBlue());
                Thread.sleep(50);
            }
            if (incrementBackgroundGreen) {
                background = new Color(background.getRed(), background.getGreen() + 1, background.getBlue());
                Thread.sleep(50);
            }
            if (incrementBackgroundBlue) {
                background = new Color(background.getRed(), background.getGreen(), background.getBlue() + 1);
                Thread.sleep(50);
            }

            // decrement
            if (incrementForegroundRed) {
                foreground = new Color(foreground.getRed() - 1, foreground.getGreen(), foreground.getBlue());
                Thread.sleep(50);
            }
            if (incrementForegroundGreen) {
                foreground = new Color(foreground.getRed(), foreground.getGreen() - 1, foreground.getBlue());
                Thread.sleep(50);
            }
            if (incrementForegroundBlue) {
                foreground = new Color(foreground.getRed(), foreground.getGreen(), foreground.getBlue() - 1);
                Thread.sleep(50);
            }
            if (incrementBackgroundRed) {
                background = new Color(background.getRed() - 1, background.getGreen(), background.getBlue());
                Thread.sleep(50);
            }
            if (incrementBackgroundGreen) {
                background = new Color(background.getRed(), background.getGreen() - 1, background.getBlue());
                Thread.sleep(50);
            }
            if (incrementBackgroundBlue) {
                background = new Color(background.getRed(), background.getGreen(), background.getBlue() - 1);
                Thread.sleep(50);
            }
        } catch (final Exception exception) {
            // swallow
        }
    }

    @Override
    public void handle(final Event event) {
        event.as(MouseMoveEvent.class) //
                .ifPresent(mouseMoveEvent -> {
                    final int column = mouseMoveEvent.getColumn();
                    final int row = mouseMoveEvent.getRow();
                    if (column > 0 && column < 18 &&
                            row > 0 && row < 18) {
                        highlightedIndex = (row - 2) * 16 + (column - 1);
                    } else {
                        highlightedIndex = null;
                    }
                });
        event.as(MouseButtonEvent.class) //
                .ifPresent(mouseButtonEvent -> {
                    if (mouseButtonEvent.isPressed() && mouseButtonEvent.getButton() == Button.LEFT && highlightedIndex != null) {
                        selectedIndex = highlightedIndex;
                        return;
                    }
                    if (!mouseButtonEvent.isPressed() && mouseButtonEvent.getButton() == Button.LEFT) {
                        incrementForegroundRed = false;
                        incrementForegroundGreen = false;
                        incrementForegroundBlue = false;
                        incrementBackgroundRed = false;
                        incrementBackgroundGreen = false;
                        incrementBackgroundBlue = false;
                        decrementForegroundRed = false;
                        decrementForegroundGreen = false;
                        decrementForegroundBlue = false;
                        decrementBackgroundRed = false;
                        decrementBackgroundGreen = false;
                        decrementBackgroundBlue = false;
                    }
                    if (mouseButtonEvent.isPressed() && mouseButtonEvent.getButton() == Button.LEFT) {
                        if (mouseButtonEvent.getColumn() == 9) {
                            // decrement
                            switch (mouseButtonEvent.getRow()) {
                                case 20:
                                    decrementForegroundRed = true;
                                    break;
                                case 21:
                                    decrementForegroundGreen = true;
                                    break;
                                case 22:
                                    decrementForegroundBlue = true;
                                    break;
                                case 25:
                                    decrementBackgroundRed = true;
                                    break;
                                case 26:
                                    decrementBackgroundGreen = true;
                                    break;
                                case 27:
                                    decrementBackgroundBlue = true;
                                    break;
                            }
                        }
                        if (mouseButtonEvent.getColumn() == 15) {
                            // increment
                            switch (mouseButtonEvent.getRow()) {
                                case 20:
                                    incrementForegroundRed = true;
                                    break;
                                case 21:
                                    incrementForegroundGreen = true;
                                    break;
                                case 22:
                                    incrementForegroundBlue = true;
                                    break;
                                case 25:
                                    incrementBackgroundRed = true;
                                    break;
                                case 26:
                                    incrementBackgroundGreen = true;
                                    break;
                                case 27:
                                    incrementBackgroundBlue = true;
                                    break;
                            }
                        }
                    }
                });
    }

}
