package com.urfu.GorohSlot.games.tools;

public enum Emoji {
    tilda("\u3030\ufe0f"),
    dollar("\ud83d\udcb0"),
    apple("\ud83c\udf4e"),
    lemon("\ud83c\udf4b"),
    melon("\ud83c\udf49"),
    cherry("\ud83c\udf52"),
    grape("\ud83c\udf47"),
    kiwi("\ud83e\udd5d"),
    chili("\ud83c\udf36"),
    seven("7\ufe0f\u20e3"),
    cross("\u274c"),
    peach("\ud83c\udf51"),
    avocado("\ud83e\udd51"),
    tomato("\ud83c\udf45"),
    sign("\u2705"),
    background("\ud83c\udf2b"),
    machine("\ud83c\udfb0"),
    attention("\u26a0\ufe0f"),
    ;

    private final String emojiCode;

    Emoji(String code) {
        this.emojiCode = code;
    }

    public String getEmojiCode() {
        return this.emojiCode;
    }
}
