package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import models.User;

public class JsonHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static User readJson() {
        try {
            File file = new File("data/user.json");
            if (!file.exists()) {
                return null;
            }
            FileReader reader = new FileReader(file);
            User user = gson.fromJson(reader, User.class);
            reader.close();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeJson(User user) {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter writer = new FileWriter("data/user.json");
            gson.toJson(user, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
