package com.urfu.GorohSlot.bot;

import com.urfu.GorohSlot.bot.telegramBot.KeyboardTelegram;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class User {
    private final String userId;
    private final String userName;
    private final String userFirstname;
    private final String userLastName;
    private KeyboardTelegram keyboardTelegram;
    private long balance;
    private int bet;
    private String mode;
    private String keyboardState;

    public User(String id, String firstName, String lastName, String name, long balance, int bet, String mode, String keyboardState) {
        this.userId = id;
        this.userFirstname = firstName;
        this.userLastName = lastName;
        this.userName = name;
        this.balance = balance;
        this.bet = bet;
        this.mode = mode;
        this.keyboardTelegram = new KeyboardTelegram(new ReplyKeyboardMarkup());
        this.keyboardState = keyboardState;
    }

    public KeyboardTelegram getKeyboard(){
        return this.keyboardTelegram;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return this.bet;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public String getUserName() {
        return userName;
    }

    public long getBalance() {
        return this.balance;
    }

    public void AddMoney(int count){
        this.balance += count;
    }

    public void TakeOffMoney(int count){
        this.balance -= count;
    }

    public String getKeyboardState(){return this.keyboardState;}

    public void setKeyboardState(String keyboardState) {
        this.keyboardState = keyboardState;
    }
}
