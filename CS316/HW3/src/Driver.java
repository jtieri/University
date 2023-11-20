import java.io.*;
import java.nio.charset.StandardCharsets;

public class Driver {
    public static void main(String[] args) {
        String filename = "front.in";

        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), StandardCharsets.UTF_8))) {
            LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(input);

            // Main loop to run the input through the lexical analyzer
            lexicalAnalyzer.getChar();
            do {
                lexicalAnalyzer.lex();
            } while (input.ready());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
