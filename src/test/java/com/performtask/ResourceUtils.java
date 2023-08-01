package com.performtask;

import com.google.gson.Gson;

import java.io.*;
import java.util.Properties;

public class ResourceUtils {
    String user;
    String passwd;

    public ResourceUtils(int index) {
        Gson gson = new Gson();
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        InputStream in = null;
        in = classLoader.getResourceAsStream("Credentials/UserData.json");
        UserInfo client = gson.fromJson(new InputStreamReader(in), UserInfo.class);
        user = client.getUserInfo().get(index).getPersonalData().getUser();
        passwd = client.getUserInfo().get(index).getPersonalData().getPasswd();
    }

    public static Properties ticketProps() throws FileNotFoundException {
        Properties prop = new Properties();
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("Locators/locatorsHub.properties");
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

}
