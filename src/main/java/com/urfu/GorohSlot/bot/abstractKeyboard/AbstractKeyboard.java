package com.urfu.GorohSlot.bot.abstractKeyboard;

import java.util.ArrayList;

public abstract class AbstractKeyboard {

    public final Object keyboard;
    private ArrayList<Object> KeyboardRows;

    public AbstractKeyboard(Object keyboard){
        this.keyboard = keyboard;
    }

    public Object SetKeyboard(){
        return this.keyboard;
    }

    public abstract void AddSetting();

    public abstract void AddButtonOneLine(String text);

    public abstract void AddButtonTwoLine(String text);

    public abstract void AddButtonThreeLine(String text);

    public abstract void SaveKeyboard();
}