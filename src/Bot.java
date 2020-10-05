import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        if(update.getMessage().getText().equals("Go")){
            sendMessage.setText("ИГрааааа НАЧИНАЕТСЯЯЯ!!");
            try{
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "@GorohSlotBot";
    }

    @Override
    public String getBotToken() {
        return "1386672879:AAGmPajn6Bp_rvjyH1dyMINn5H_AxGmHd5U";
    }
}
