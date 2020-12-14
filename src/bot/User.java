package bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class User {
    private final String userId;
    private final String userName;
    private final String userFirstname;
    private final String userLastName;
    private long balance;
    private int bet;
    private String mode;
    private ReplyKeyboardMarkup replyKeyboardMarkup;
    private ArrayList<KeyboardRow> keyboard;
    private KeyboardRow keyboardFirstRow;
    private KeyboardRow keyboardSecondRow;
    private KeyboardRow keyboardThirdRow;

    public KeyboardRow getKeyboardFirstRow() {
        return keyboardFirstRow;
    }

    public ArrayList<KeyboardRow> getKeyboard() {
        return keyboard;
    }

    public KeyboardRow getKeyboardSecondRow() {
        return keyboardSecondRow;
    }

    public KeyboardRow getKeyboardThirdRow() {
        return keyboardThirdRow;
    }

    public User(String id, String firstName, String lastName, String name, long balance, int bet) {
        this.userId = id;
        this.userFirstname = firstName;
        this.userLastName = lastName;
        this.userName = name;
        this.balance = balance;
        this.bet = bet;
        this.mode = "Автомат 3x3";
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.replyKeyboardMarkup.setSelective(true);
        this.replyKeyboardMarkup.setResizeKeyboard(true);
        this.replyKeyboardMarkup.setOneTimeKeyboard(false);
        this.keyboard = new ArrayList<>();
        this.keyboardFirstRow = new KeyboardRow();
        this.keyboardSecondRow = new KeyboardRow();
        this.keyboardThirdRow = new KeyboardRow();
    }

    public void setReplyKeyboardMarkup(ReplyKeyboardMarkup replyKeyboardMarkup) {
        this.replyKeyboardMarkup = replyKeyboardMarkup;
    }

    public void keyBoardUpdate(){
        this.replyKeyboardMarkup = new ReplyKeyboardMarkup();
        this.keyboard = new ArrayList<>();
        this.keyboardFirstRow = new KeyboardRow();
        this.keyboardSecondRow = new KeyboardRow();
        this.keyboardThirdRow = new KeyboardRow();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
    }

    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        return replyKeyboardMarkup;
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

    private void setBalance(int balance) {
        this.balance = balance;
    }
}
