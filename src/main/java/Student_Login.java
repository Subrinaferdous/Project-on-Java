import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Information {
    String username;
    String password;
    String role;

    public Information(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
class Questions {
    String question;
    String[] options;
    int answerKey;

    public Questions(String question, String[] options, int answerKey) {
        this.question = question;
        this.options = options;
        this.answerKey = answerKey;
    }
}
public class Student_Login {

    public static void main(String[] args) throws IOException, ParseException {
        String studentUsername = "subrina";
        String studentPassword = "1234";
        String role = "student";

        Scanner scanner = new Scanner(System.in);

        System.out.println("System:> Enter your username");
        System.out.print("User:> ");
        String username = scanner.next();

        System.out.println("System:> Enter password");
        System.out.print("User:> ");
        String password = scanner.next();

        if (username.equals(studentUsername) && password.equals(studentPassword)) {
            System.out.println("System:> Welcome subrina to the quiz! We will throw you 10 questions. Each MCQ mark is 1 and no negative marking. Are you ready? Press 's' to start.");
            System.out.print("Student:> ");
            //add(new Info(username, password, role));
            if (scanner.next().equalsIgnoreCase("s")) {
                takeQuiz();
            }

        } else {
            System.out.println("Wrong Credentials");
        }
    }

    private static void takeQuiz() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray quizArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/quiz.json"));

        List<Questions> questions = new ArrayList<>();
        for (Object obj : quizArray) {
            JSONObject quizObj = (JSONObject) obj;
            String question = (String) quizObj.get("question");
            String[] options = new String[4];
            options[0] = (String) quizObj.get("option 1");
            options[1] = (String) quizObj.get("option 2");
            options[2] = (String) quizObj.get("option 3");
            options[3] = (String) quizObj.get("option 4");
            int answerKey = ((Long) quizObj.get("answer")).intValue();
            questions.add(new Questions(question, options, answerKey));
        }

        Collections.shuffle(questions);
        List<Questions> selectedQuestions = questions.subList(0, Math.min(10, questions.size()));

        Scanner input = new Scanner(System.in);
        int score = 0;

        for (int i = 0; i < selectedQuestions.size(); i++) {
            Questions q = selectedQuestions.get(i);
            System.out.println("[Question " + (i + 1) + "] " + q.question);
            for (int j = 0; j < q.options.length; j++) {
                System.out.println((j + 1) + ". " + q.options[j]);
            }
            System.out.print("Your answer: ");
            int answer = input.nextInt();
            if (answer == q.answerKey) {
                score++;
            }
        }
        System.out.println("Quiz completed! Your score is: " + score + "/" + selectedQuestions.size());

        if (score >= 8) {
            System.out.println("Excellent! You have got " + score + " out of 10.");
        } else if (score >= 5) {
            System.out.println("Good. You have got " + score + " out of 10.");
        } else if (score >= 2) {
            System.out.println("Very poor! You have got " + score + " out of 10.");
        } else {
            System.out.println("Very sorry you have failed. You have got " + score + " out of 10.");
        }

        System.out.println("Would you like to start again? Press 's' for start or 'q' for quit");
        String response = input.next();
        if (response.equalsIgnoreCase("s")) {
            takeQuiz();
        }
    }


    public static void add(Information info) throws IOException, ParseException {
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
