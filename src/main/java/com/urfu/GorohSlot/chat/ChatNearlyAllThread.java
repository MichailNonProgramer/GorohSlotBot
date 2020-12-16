package com.urfu.GorohSlot.chat;

import com.urfu.GorohSlot.bot.User;

public class ChatNearlyAllThread extends Thread {
    public String message;
    public User user;

    public ChatNearlyAllThread(String message, User user) {
        this.message = message;
        this.user = user;
    }

    @Override
    public void run() {
        try {
            ChatController.handleMessage(message, user);
        } catch (Exception e) {
            System.out.println("У Мишки ошибочка в тредике");
        }
    }
}