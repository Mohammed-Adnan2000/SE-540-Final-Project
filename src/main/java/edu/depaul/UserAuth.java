package edu.depaul;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserAuth {
    private static final String USER_FILE = "Users.json";

    public UserAuth() {
        initializeUserFile();
    }

    private void initializeUserFile() {
        try {
            if (!FileManager.fileExists(USER_FILE)) {
                FileManager.writeFile(USER_FILE, "[]");
            }
        } catch (IOException e) {
            System.out.println("Error initializing user file: " + e.getMessage());
        }
    }

    public boolean login(String username, String password) {
        try {
            String data = FileManager.readFile(USER_FILE);
            JSONArray users = new JSONArray(data);
            for (int i = 0; i < users.length(); i++) {
                JSONObject user = users.getJSONObject(i);
                if (user.getString("username").equals(username) && user.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }
        return false;
    }

    public boolean register(User user) {
        try {
            String data = FileManager.readFile(USER_FILE);
            JSONArray users = new JSONArray(data);
            for (int i = 0; i < users.length(); i++) {
                JSONObject existingUser = users.getJSONObject(i);
                if (existingUser.getString("username").equals(user.getUsername())) {
                    return false; // User already exists
                }
            }
            JSONObject newUser = new JSONObject();
            newUser.put("username", user.getUsername());
            newUser.put("password", user.getPassword());
            users.put(newUser);
            FileManager.writeFile(USER_FILE, users.toString(4)); // Pretty print JSON
            return true;
        } catch (IOException e) {
            System.out.println("Error accessing user file: " + e.getMessage());
            return false;
        }
    }
}
