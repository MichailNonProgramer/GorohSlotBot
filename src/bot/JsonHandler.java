package bot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class JsonHandler {
    public static void writeJSON(User user, String fileName) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        File myFile = new File(getSavePath(fileName));
        PrintWriter textFileWriter = new PrintWriter(new FileWriter(myFile));
        textFileWriter.write(gson.toJson(user));
        textFileWriter.close();
    }

    public static User readJSON(String fileName) throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader(getSavePath(fileName)));
        return gson.fromJson(bufferedReader, User.class);
    }

    public static String getSavePath(String fileName) {
        return System.getProperty("user.dir")
                + System.getProperty("file.separator") + "src"
                + System.getProperty("file.separator") + "database"
                + File.separator + fileName + ".json";
    }
}
