package com.rl4j;

import com.rl4j.ui.Box;

import java.awt.Color;

public class RoguelikeTest {

    public static void main(final String[] args) {
        final Roguelike roguelike = Roguelike.builder() //
                .title("Roguelike 4 Java") //
                .fpsLimit(30) //
                .build();
        final Game game = new Game();
        roguelike.start(game, game);
    }

    private static class Game implements Update, Draw {

        private float fps;

        @Override
        public void draw(final Console console) {
            console.clear();
            final Box box = new Box(0, 0, console.getSize().getWidth(), console.getSize().getHeight());
            box.draw(console);
            console.put(String.format("%.0ffps", fps), console.getSize().getWidth() - 6, console.getSize().getHeight() - 1);
        }

        @Override
        public void update(final float elapsed) {
            fps = 1 / elapsed;
        }

    }

}