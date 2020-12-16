package com.urfu.GorohSlot.bot.telegramBot;

import com.urfu.GorohSlot.bot.abstractKeyboard.AbstractKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class KeyboardTelegram extends AbstractKeyboard {

    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ArrayList<KeyboardRow> keyboard;
    private KeyboardRow keyboardFirstRow;
    private KeyboardRow keyboardSecondRow;
    private KeyboardRow keyboardThirdRow;

    public KeyboardTelegram(ReplyKeyboardMarkup replyKeyboardMarkup) {
        super(replyKeyboardMarkup);
    }

    @Override
    public void AddSetting() {
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.keyboard = new ArrayList<>();
        this.keyboardFirstRow = new KeyboardRow();
        this.keyboardSecondRow = new KeyboardRow();
        this.keyboardThirdRow = new KeyboardRow();
        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    @Override
    public void AddButtonOneLine(String text) {
        this.keyboardFirstRow.add(text);
    }

    @Override
    public void AddButtonTwoLine(String text) {
        this.keyboardSecondRow.add(text);
    }

    @Override
    public void AddButtonThreeLine(String text) {
        this.keyboardThirdRow.add(text);
    }

    @Override
    public void SaveKeyboard() {
        this.keyboard.add(keyboardFirstRow);
        this.keyboard.add(keyboardSecondRow);
        this.keyboard.add(keyboardThirdRow);
        this.replyKeyboardMarkup.setKeyboard(this.keyboard);
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup(){
        return this.replyKeyboardMarkup;
    }

    public void setReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup) {
        this.replyKeyboardMarkup = replyKeyboardMarkup;
    }
}
