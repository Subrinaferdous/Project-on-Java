import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Question {
    String question;
    String[] options;
    int answerKey;

    public Question(String question, String[] options, int answerKey) {
        this.question = question;
        this.options = options;
        this.answerKey = answerKey;
    }
}

public class MyJsonList {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);
        List<Question> questions = new ArrayList<>();


        while (true) {
            System.out.println("System:> Input your question");
            System.out.print("Admin:> ");
            String question = input.nextLine();

            String[] options = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.println("System:> Input option " + (i + 1) + ":");
                System.out.print("Admin:> ");
                options[i] = input.nextLine();
            }

            System.out.println("System:> What is the answer key? (1-4)");
            System.out.print("Admin:> ");
            int answerKey = Integer.parseInt(input.nextLine());

            questions.add(new Question(question, options, answerKey));
            System.out.println("System:> Saved successfully! Do you want to add more questions? (press s for start and q for quit)");
            if (input.nextLine().equalsIgnoreCase("q"))
                break;
        }

        input.close();
        QuizBank.createJSONList(questions);
    }
}
class QuizBank {

    public static void createJSONList(List<Question> newQuestions) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray mcqArray = (JSONArray) parser.parse(new FileReader("./src/main/resources/quiz.json"));

        // Add new questions to the JSONArray
        if (newQuestions != null) {
            for (Question q : newQuestions) {
                JSONObject quiz = new JSONObject();
                quiz.put("question", q.question);
                quiz.put("option 1", q.options[0]);
                quiz.put("option 2", q.options[1]);
                quiz.put("option 3", q.options[2]);
                quiz.put("option 4", q.options[3]);
                quiz.put("answer", q.answerKey);

                mcqArray.add(quiz);
            }
        }


        try (FileWriter writer = new FileWriter("./src/main/resources/quiz.json")) {
            writer.write(mcqArray.toJSONString());
            writer.flush();
        }
    }
}