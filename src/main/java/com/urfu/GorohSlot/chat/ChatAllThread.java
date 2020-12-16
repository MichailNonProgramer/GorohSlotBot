package com.urfu.GorohSlot.chat;

public class ChatAllThread extends Thread {
    public String message;

    public ChatAllThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {
            ChatController.sendAllUsers(message);
        } catch (Exception e) {
            System.out.println("У Артема ошибочка в Тредике");
        }
    }
}
