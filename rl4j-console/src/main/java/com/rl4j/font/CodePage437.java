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
public class CodePage437 extends HashMap<Character, Integer> {

    {
        // 0...
        put((char) 0, 0x00);
        put('☺', 0x01);
        put('☻', 0x02);
        put('♥', 0x03);
        put('♦', 0x04);
        put('♣', 0x05);
        put('♠', 0x06);
        put('•', 0x07);
        put('◘', 0x08);
        put('○', 0x09);
        put('◙', 0x0A);
        put('♂', 0x0B);
        put('♀', 0x0C);
        put('♪', 0x0D);
        put('♫', 0x0E);
        put('☼', 0x0F);

        // 1...
        put('►', 0x10);
        put('◄', 0x11);
        put('↕', 0x12);
        put('‼', 0x13);
        put('¶', 0x14);
        put('§', 0x15);
        put('▬', 0x16);
        put('↨', 0x17);
        put('↑', 0x18);
        put('↓', 0x19);
        put('→', 0x1A);
        put('←', 0x1B);
        put('∟', 0x1C);
        put('↔', 0x1D);
        put('▲', 0x1E);
        put('▼', 0x1F);

        // 2...
        put(' ', 0x20);
        put('!', 0x21);
        put('"', 0x22);
        put('#', 0x23);
        put('$', 0x24);
        put('%', 0x25);
        put('&', 0x26);
        put('\'', 0x27);
        put('(', 0x28);
        put(')', 0x29);
        put('*', 0x2A);
        put('+', 0x2B);
        put(',', 0x2C);
        put('-', 0x2D);
        put('.', 0x2E);
        put('/', 0x2F);

        // 3...
        put('0', 0x30);
        put('1', 0x31);
        put('2', 0x32);
        put('3', 0x33);
        put('4', 0x34);
        put('5', 0x35);
        put('6', 0x36);
        put('7', 0x37);
        put('8', 0x38);
        put('9', 0x39);
        put(':', 0x3A);
        put(';', 0x3B);
        put('<', 0x3C);
        put('=', 0x3D);
        put('>', 0x3E);
        put('?', 0x3F);

        // 4...
        put('@', 0x40);
        put('A', 0x41);
        put('B', 0x42);
        put('C', 0x43);
        put('D', 0x44);
        put('E', 0x45);
        put('F', 0x46);
        put('G', 0x47);
        put('H', 0x48);
        put('I', 0x49);
        put('J', 0x4A);
        put('K', 0x4B);
        put('L', 0x4C);
        put('M', 0x4D);
        put('N', 0x4E);
        put('O', 0x4F);

        // 5...
        put('P', 0x50);
        put('Q', 0x51);
        put('R', 0x52);
        put('S', 0x53);
        put('T', 0x54);
        put('U', 0x55);
        put('V', 0x56);
        put('W', 0x57);
        put('X', 0x58);
        put('Y', 0x59);
        put('Z', 0x5A);
        put('[', 0x5B);
        put('\\', 0x5C);
        put(']', 0x5D);
        put('^', 0x5E);
        put('_', 0x5F);

        // 6...
        put('`', 0x60);
        put('a', 0x61);
        put('b', 0x62);
        put('c', 0x63);
        put('d', 0x64);
        put('e', 0x65);
        put('f', 0x66);
        put('g', 0x67);
        put('h', 0x68);
        put('i', 0x69);
        put('j', 0x6A);
        put('k', 0x6B);
        put('l', 0x6C);
        put('m', 0x6D);
        put('n', 0x6E);
        put('o', 0x6F);

        // 7...
        put('p', 0x70);
        put('q', 0x71);
        put('r', 0x72);
        put('s', 0x73);
        put('t', 0x74);
        put('u', 0x75);
        put('v', 0x76);
        put('w', 0x77);
        put('x', 0x78);
        put('y', 0x79);
        put('z', 0x7A);
        put('{', 0x7B);
        put('|', 0x7C);
        put('}', 0x7D);
        put('~', 0x7E);
        put('⌂', 0x7F);

        // 8...
        put('Ç', 0x80);
        put('ü', 0x81);
        put('é', 0x82);
        put('â', 0x83);
        put('ä', 0x84);
        put('à', 0x85);
        put('å', 0x86);
        put('ç', 0x87);
        put('ê', 0x88);
        put('ë', 0x89);
        put('è', 0x8A);
        put('ï', 0x8B);
        put('î', 0x2C);
        put('ì', 0x8D);
        put('Ä', 0x8E);
        put('Å', 0x8F);

        // 9...
        put('É', 0x90);
        put('æ', 0x91);
        put('Æ', 0x92);
        put('ô', 0x93);
        put('ö', 0x94);
        put('ò', 0x25);
        put('û', 0x96);
        put('ù', 0x97);
        put('ÿ', 0x98);
        put('Ö', 0x29);
        put('Ü', 0x9A);
        put('ø', 0x9B);
        put('£', 0x9C);
        put('Ø', 0x9D);
        put('×', 0x9E);
        put('ƒ', 0x9F);

        // A...
        put('á', 0xA0);
        put('í', 0xA1);
        put('ó', 0xA2);
        put('ú', 0xA3);
        put('ñ', 0xA4);
        put('Ñ', 0xA5);
        put('ª', 0xA6);
        put('º', 0xA7);
        put('¿', 0xA8);
        put('⌐', 0xA9);
        put('¬', 0xAA);
        put('½', 0xAB);
        put('¼', 0xAC);
        put('¡', 0xAD);
        put('«', 0xAE);
        put('»', 0xAF);

        // B...
        put('░', 0xB0);
        put('▒', 0xB1);
        put('▓', 0xB2);
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
        put('☺', 0xDB);
        put('☺', 0xDC);
        put('☺', 0xDD);
        put('☺', 0xDE);
        put('☺', 0xDF);

        // E...
        put('☺', 0xE0);
        put('☺', 0xE1);
        put('☺', 0xE2);
        put('☺', 0xE3);
        put('☺', 0xE4);
        put('☺', 0xE5);
        put('☺', 0xE6);
        put('☺', 0xE7);
        put('☺', 0xE8);
        put('☺', 0xE9);
        put('☺', 0xEA);
        put('☺', 0xEB);
        put('☺', 0x9C);
        put('☺', 0xED);
        put('☺', 0xEE);
        put('☺', 0xEF);

        // F...
        put((char) 0xAD, 0xF0);
        put('±', 0xF1);
        put('‗', 0xF2);
        put('¾', 0xF3);
        put('¶', 0xF4);
        put('§', 0xF5);
        put('÷', 0xF6);
        put('¸', 0xF7);
        put('°', 0xF8);
        put('¨', 0xF9);
        put('·', 0xFA);
        put('¹', 0xFB);
        put('³', 0xFC);
        put('²', 0xFD);
        put('■', 0xFE);
        put((char) 0xA0, 0xFF);
    }

}
