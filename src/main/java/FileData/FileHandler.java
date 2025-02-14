package FileData;

import java.io.*;
import java.util.Properties;

public class FileHandler {
    private static final String FILE_NAME = "data.txt";

    public static void saveData(int balance, long cooldownTime) {
        Properties properties = new Properties();
        properties.setProperty("balance", String.valueOf(balance));
        properties.setProperty("cooldownTime", String.valueOf(cooldownTime));

        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME)) {
            properties.store(fileOutputStream, "User Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int loadBalance() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME)) {
            properties.load(fileInputStream);
            return Integer.parseInt(properties.getProperty("balance", "1000"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return 1000;
    }

    public static long loadCooldownTime() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(FILE_NAME)) {
            properties.load(fileInputStream);
            return Long.parseLong(properties.getProperty("cooldownTime", "0"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
