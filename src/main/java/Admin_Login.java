import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Info {
    String username;
    String password;
    String role;

    public Info(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

public class Admin_Login {

    public static void main(String[] args) throws IOException, ParseException {
        String adminUsername = "admin";
        String adminPassword = "1234";
        String role = "admin";

        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Enter your username");
        System.out.print("User:> ");
        String username = scanner.next();

        System.out.println("System:> Enter password");
        System.out.print("User:> ");
        String password = scanner.next();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("System:> Welcome admin! Please create new questions in the question bank.");

            //add(new Info(username, password, role));
            MyJsonList.main(new String[]{});
        } else {
            System.out.println("Wrong Credentials");
        }
    }

    public static void add(Info info) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray infoArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/users.json"));

        JSONObject infoObj = new JSONObject();
        infoObj.put("username", info.username);
        infoObj.put("password", info.password);
        infoObj.put("role", info.role);

        infoArray.add(infoObj);

        try (FileWriter writer = new FileWriter("./src/main/resources/users.json")) {
            writer.write(infoArray.toJSONString());
            writer.flush();
        }
    }
}
