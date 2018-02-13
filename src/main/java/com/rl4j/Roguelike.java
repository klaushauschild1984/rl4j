package com.rl4j;

import lombok.Builder;
import lombok.Getter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

@Builder
public class Roguelike {

    private String title;
    @Getter
    private Dimension size;
    private BitmapFont bitmapFont;
    private Integer fpsLimit;

    public static RoguelikeBuilder builder() {
        return new HiddenRoguelikeBuilder();
    }

    public void start(final Update update, final Draw draw) {
        final Console console = new Console(bitmapFont, size);
        EventQueue.invokeLater(() -> {
            final JFrame frame = new JFrame(title);
            frame.add(new JPanel() {

                {
                    final int width = bitmapFont.getTileSize().getWidth() * size.getWidth();
                    final int height = bitmapFont.getTileSize().getHeight() * size.getHeight();
                    setPreferredSize(new java.awt.Dimension(width - 15, height - 15));
                }

                @Override
                public void paint(final Graphics g) {
                    super.paint(g);
                    final Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

                    draw.draw(console);

                    console.flush(g2d);
                }
            });
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
                frame.repaint();
                currentTime[0] = System.currentTimeMillis();
            }).start();
        });
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
                    super.fpsLimit //
            );
        }

    }

}
