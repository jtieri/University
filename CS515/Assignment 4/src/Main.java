import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);

        boolean runAgain = true;
        do {
            System.out.println("Enter a string and I will tell you if the string is a palindrome or not.");
            System.out.println("(Special characters will be removed from the string, aka numbers and letters only.");

            String userInput = keyboard.nextLine();
            Palindrome test = new Palindrome(userInput);
            printTests(test);

            System.out.println("Would you like to run the program again? Enter 'y' to run again or anything else to quit.");
            runAgain = keyboard.next().trim().toLowerCase().equals("y");

            keyboard.nextLine();                    // flush the input buffer essentially.
            System.out.println("               ");  // just to make things look nice
        } while (runAgain);
    }

    public static void printTests(final Palindrome palindrome) {
        System.out.println("The original string is: " + palindrome.getWordToTest());
        System.out.println("The string cleaned of special characters is: " + palindrome.cleanUserInput());
        System.out.println("The reversed word is: " + palindrome.reverseWord(palindrome.getWordToTest()));
        System.out.println("The string you entered is a palindrome: " + palindrome.isPalindrome());
    }
}
