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

import java.util.HashMap;

/**
 * @see <a href="https://en.wikipedia.org/wiki/Code_page_437">Code page 437</a>
 */
class CodePage437 extends HashMap<Character, Integer> {

    {
        // A...
        put('»', 0xAF);

        // B...
        put('│', 0xB3);
        put('┤', 0xB4);
        put('╡', 0xB5);
        put('╢', 0xB6);
        put('╖', 0xB7);
        put('╕', 0xB8);
        put('╣', 0xB9);
        put('║', 0xBA);
        put('╗', 0xBB);
        put('╝', 0xBC);
        put('╜', 0xBD);
        put('╛', 0xBE);
        put('┐', 0xBF);

        // C...
        put('└', 0xC0);
        put('┴', 0xC1);
        put('┬', 0xC2);
        put('├', 0xC3);
        put('─', 0xC4);
        put('┼', 0xC5);
        put('╞', 0xC6);
        put('╟', 0xC7);
        put('╚', 0xC8);
        put('╔', 0xC9);
        put('╩', 0xCA);
        put('╦', 0xCB);
        put('╠', 0xCC);
        put('═', 0xCD);
        put('╬', 0xCE);
        put('╧', 0xCF);

        // D...
        put('╨', 0xD0);
        put('╤', 0xD1);
        put('╥', 0xD2);
        put('╙', 0xD3);
        put('╘', 0xD4);
        put('╒', 0xD5);
        put('╓', 0xD6);
        put('╫', 0xD7);
        put('╪', 0xD8);
        put('┘', 0xD9);
        put('┌', 0xDA);
    }

}
