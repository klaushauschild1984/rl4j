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
package com.rl4j;

import com.rl4j.event.AwtKeyboardEventProducer;
import com.rl4j.event.AwtMouseEventProducer;
import com.rl4j.event.EventBus;
import com.rl4j.event.Handler;
import com.rl4j.event.SystemEventProducer;
import com.rl4j.font.BitmapFont;
import com.rl4j.ui.Cursor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

@Slf4j
public class Roguelike {

    private static JFrame frame;

    private final String title;
    @Getter
    private final Dimension size;
    private final BitmapFont bitmapFont;
    private final Integer fpsLimit;
    private final boolean borderless;
    @Getter
    private final Cursor cursor;
    private final boolean nativeCursor;

    @Builder
    public Roguelike(final String title, final Dimension size, final BitmapFont bitmapFont, final Integer fpsLimit, final boolean borderless, final boolean nativeCursor) {
        this.title = title;
        this.size = size;
        this.bitmapFont = bitmapFont;
        this.fpsLimit = fpsLimit;
        this.borderless = borderless;
        this.nativeCursor = nativeCursor;
        cursor = new Cursor(nativeCursor);
    }

    public static RoguelikeBuilder builder() {
        return new HiddenRoguelikeBuilder();
    }

    public void start(final Game game) {
        start(game, game, game);
    }

    public void start(final Update update, final Draw draw, final Handler handler) {
        final Console console = new Console(bitmapFont, size);
        EventQueue.invokeLater(() -> {
            if (title != null) {
                Thread.currentThread().setName(title);
            }
            frame = new JFrame(title);
            frame.add(new JPanel() {

                {
                    final int width = bitmapFont.getTileSize().getWidth() * size.getWidth();
                    final int height = bitmapFont.getTileSize().getHeight() * size.getHeight();
                    final java.awt.Dimension preferredSize;
                    if (borderless) {
                        preferredSize = new java.awt.Dimension(width, height);
                    } else {
                        preferredSize = new java.awt.Dimension(width - 15, height - 15);
                    }
                    setPreferredSize(preferredSize);
                }

                @Override
                public void paint(final Graphics g) {
                    super.paint(g);
                    final Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    draw.draw(console);
                    cursor.draw(console);

                    console.flush(g2d);
                }
            });
            frame.setUndecorated(borderless);
            if (nativeCursor) {
                final BufferedImage cursor = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                frame.setCursor(frame.getToolkit().createCustomCursor(cursor, new Point(0, 0), "null"));
            }
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setVisible(true);
            final long[] currentTime = {System.currentTimeMillis()};
            new Timer(-1, event -> {
                while (fpsLimit != null && fpsLimit > 0 && frameTime(currentTime[0]) < (1f / fpsLimit)) {
                    try {
                        Thread.sleep(0);
                    } catch (final InterruptedException exception) {
                        throw new RuntimeException(exception);
                    }
                }
                final float elapsed = frameTime(currentTime[0]);
                update.update(elapsed);
                cursor.update(elapsed);
                frame.repaint();
                currentTime[0] = System.currentTimeMillis();
            }).start();
            Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
                log.error("Unexpected error.", exception);
                System.exit(-1);
            });

            final SystemEventProducer systemEventProducer = new SystemEventProducer(frame);
            new AwtKeyboardEventProducer(frame);
            new AwtMouseEventProducer(bitmapFont.getTileSize(), frame);
            try {
                Class.forName("com.rl4j.event.JInputEventProducer");
            } catch (final ClassNotFoundException exception) {
                log.debug("No com.rl4j.event.JInputEventProducer in classpath.");
            }

            EventBus.register(handler);

            systemEventProducer.startEvent();
        });
    }

    public void stop() {
        frame.dispose();
    }

    private float frameTime(final long currentTime) {
        return (System.currentTimeMillis() - currentTime) / 1000f;
    }

    private static class HiddenRoguelikeBuilder extends RoguelikeBuilder {

        private static <T> T withDefault(final T value, final T defaultValue) {
            if (value != null) {
                return value;
            }
            return defaultValue;
        }

        @Override
        public Roguelike build() {
            return new Roguelike( //
                    super.title, //
                    withDefault(super.size, new Dimension(40, 30)), //
                    withDefault(super.bitmapFont, BitmapFont.create(Roguelike.class.getResourceAsStream("font32x32.png"), new Dimension(32, 32))), //
                    super.fpsLimit, //
                    super.borderless, //
                    super.nativeCursor //
            );
        }

    }

}
