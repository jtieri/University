import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int userInt;
        double userDouble;
        String userString;
        String tmpAnswer;

        // Gather an int from the user (will skip strings/whitespace)
        do {
            System.out.println("Enter an integer...");
            tmpAnswer = keyboard.next().trim();
        } while (! tmpAnswer.matches("-?\\d+"));
        userInt = Integer.parseInt(tmpAnswer);
        keyboard.nextLine();

        // Gather a String from the user
        System.out.println("Enter a string");
        userString = keyboard.next();
        keyboard.nextLine();

        // Gather a double from the user
        boolean runAgain = true;
        double tmpDbl = 0;
        do {
            System.out.println("Enter a decimal number...");
            tmpAnswer = keyboard.next();

            try {
                tmpDbl = Double.parseDouble(tmpAnswer);
                runAgain = false;
            } catch (NumberFormatException e) {
                runAgain = true;
            }

        } while (runAgain);
        userDouble = tmpDbl;

        System.out.printf("You entered the integer %d \n", userInt);
        System.out.printf("You entered the string %s \n", userString);
        System.out.printf("You entered the double %f \n", userDouble);
    }
}
