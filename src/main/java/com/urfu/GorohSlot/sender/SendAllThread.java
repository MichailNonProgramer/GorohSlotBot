package com.urfu.GorohSlot.sender;

import com.urfu.GorohSlot.bot.User;
import java.util.ArrayList;

public class SendAllThread extends Thread {
    private final String text;
    private final User user;
    private final ArrayList<User> list;

    public SendAllThread(String message, User user, ArrayList<User> list) {
        this.text = message;
        this.user = user;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            Sender.sendAllUsers(text, user, list);
        } catch (Exception e) {
            System.out.println("У Мишки ошибочка в тредике");
        }
    }
}
