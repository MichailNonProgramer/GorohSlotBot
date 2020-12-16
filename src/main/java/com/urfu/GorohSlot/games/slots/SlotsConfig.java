package com.urfu.GorohSlot.games.slots;

import com.urfu.GorohSlot.games.tools.Emoji;

public class SlotsConfig {
    public static Slot[] slotsArr3x3 = new Slot[]{
            new Slot(Emoji.apple.getEmojiCode(), 3),
            new Slot(Emoji.melon.getEmojiCode(), 4),
            new Slot(Emoji.cherry.getEmojiCode(), 5.5),
            new Slot(Emoji.grape.getEmojiCode(), 8),
            new Slot(Emoji.lemon.getEmojiCode(), 12)};

    public static Slot[] slotsArr4x5 = new Slot[]{
            new Slot(Emoji.tomato.getEmojiCode(), 1.5),
            new Slot(Emoji.apple.getEmojiCode(), 2),
            new Slot(Emoji.melon.getEmojiCode(), 2),
            new Slot(Emoji.avocado.getEmojiCode(), 3),
            new Slot(Emoji.cherry.getEmojiCode(), 4),
            new Slot(Emoji.peach.getEmojiCode(), 4),
            new Slot(Emoji.grape.getEmojiCode(), 6),
            new Slot(Emoji.kiwi.getEmojiCode(), 8),
            new Slot(Emoji.chili.getEmojiCode(), 10),
            new Slot(Emoji.lemon.getEmojiCode(), 13),
            new Slot(Emoji.seven.getEmojiCode(), 20)};
}
