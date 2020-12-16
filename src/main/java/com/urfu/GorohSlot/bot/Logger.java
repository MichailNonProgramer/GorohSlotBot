package com.urfu.GorohSlot.bot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public static void log(String firstName,
                           String lastName,
                           String userName,
                           String userId,
                           String text,
                           String botAnswer) {
        System.out.println("\n----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + userName + " " + firstName + " " + lastName
                + ". (id = " + userId + ")\nUserText: " + text);
        System.out.println("Bot answer:\n" + botAnswer);
    }
}
