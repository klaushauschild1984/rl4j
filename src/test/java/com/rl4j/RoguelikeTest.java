package com.rl4j;

import com.rl4j.ui.Box;

import java.awt.Color;

public class RoguelikeTest {

    public static void main(final String[] args) {
        final Roguelike roguelike = Roguelike.builder() //
                .title("Roguelike 4 Java") //
                .fpsLimit(30) //
                .build();
        final Game game = new Game(roguelike.getSize());
        roguelike.start(game, game);
    }

    private static class Game implements Update, Draw {

        private final Box box;

        private float fps;

        public Game(final Dimension size) {
            box = new Box(0, 0, size.getWidth(), size.getHeight());
            box.setTitle("Roguelike 4 Java");
        }

        @Override
        public void draw(final Console console) {
            console.clear();
            box.draw(console);
            console.put(String.format("%.0ffps", fps), console.getSize().getWidth() - 6, console.getSize().getHeight() - 1);
        }

        @Override
        public void update(final float elapsed) {
            fps = 1 / elapsed;
        }

    }

}