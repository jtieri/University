import java.io.*;
import java.util.*;

public class Main {
    /*
        *User input-file spec:
            - first row is the alphabet seperated by comma
            - second row is the number of states in the DFSM
              0 is the starting state and its incremental
            - third row is the accepting states seperated by
              comma
            - remaining rows are functions, one for each state
                Ex: (0,0,0),(0,1,1)
                    (1,0,1),(1,1,0)
                    (start, input, end)
    */
    private static class DFSM {
        public String[] alphabet;
        public int numOfStates;
        public int[] acceptingStates;
        public List<HashMap<String, Integer>> states;

        DFSM(final String[] alphabet,
             final int numOfStates,
             final int[] acceptingStates,
             final List<HashMap<String, Integer>> states) {

            this.alphabet = alphabet;
            this.numOfStates = numOfStates;
            this.acceptingStates = acceptingStates;
            this.states = states;
        }

        boolean isStringAccepted(final String input) {
            boolean isAccepted;
            int stateCount = 0;

            for (int i = 0; i < input.length(); i++) {
                String currentToken = Character.toString(input.charAt(i));
                System.out.println("Current token: " + currentToken);

                // if a character in the user input is not in the alphabet then the string cannot be accepted by the DFSM
                if (! isTokenInAlphabet(currentToken)) {
                    return false;
                }

                HashMap<String, Integer> currentState = states.get(stateCount);
                stateCount = currentState.get(currentToken);

                System.out.println("Input: " + currentToken);
                System.out.println("Next State: " + stateCount);
                System.out.println(" ");
            }

            System.out.println("Ending State: " + stateCount);
            System.out.println(" ");
            isAccepted = isStateAccepting(stateCount);
            return isAccepted;
        }

        private boolean isTokenInAlphabet(final String token) {
            for (String letter : alphabet) {
                if (letter.equals(token)) {
                    return true;
                }
            }

            return false;
        }

        private boolean isStateAccepting(final int state) {
            boolean isAccepting = true;

            for (int acceptingState : acceptingStates) {
                if (! (acceptingState == state)) {
                    isAccepting = false;
                    break;
                }
            }

            return isAccepting;
        }
    }

    public static void main(String[] args) {
        BufferedReader inputFile;
        Scanner keyboard = new Scanner(System.in);
        String userInput;
        DFSM dfsm = null;

        // Open and read a user specified file
        System.out.println("Enter the name of a file that contains the description of a DFSM... ");
        try {
            inputFile = new BufferedReader(new FileReader(keyboard.nextLine().trim()));

            // Parse the users file and build a DFSM object out of it,
            // this will fail if the file is not formatted correctly
            final String[] tempAlphabet = inputFile.readLine().trim().split(",");
            final int tempNumOfStates = Integer.parseInt(inputFile.readLine().trim());
            final int[] tempAcceptingStates = Arrays.stream(inputFile.readLine().trim().split(",")).mapToInt(Integer::parseInt).toArray();
            final List<HashMap<String, Integer>> tempStates = new ArrayList<>();

            // Parse the functions and for each state create a map from the input to the output
            do {
                String nextLine = inputFile.readLine();
                String[] states = nextLine.split("\\)");

                HashMap<String, Integer> tempState = new HashMap<>();
                for (String state : states) {
                    final String key = Character.toString(state.charAt(3));
                    final Integer value = Integer.parseInt(Character.toString(state.charAt(5)));
                    tempState.put(key, value);
                }

                tempStates.add(tempState);
            } while (inputFile.ready());

            dfsm = new DFSM(tempAlphabet,
                            tempNumOfStates,
                            tempAcceptingStates,
                            tempStates);

        } catch (FileNotFoundException e) {
            System.out.println("The file you entered was not found... ");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.out.println("There was an error while parsing the input file you specified, " +
                               "make sure it is in proper format... ");
            e.printStackTrace();
            System.exit(1);
        }


         // Testing output
        System.out.println("Alphabet: " + Arrays.toString(dfsm.alphabet));
        System.out.println("Number of states: " + dfsm.numOfStates);
        System.out.println("Accepting States: " + Arrays.toString(dfsm.acceptingStates));
        System.out.println("Number of states in list: " + dfsm.states.size());
        System.out.println(" ");

        // Repeatedly test user specified strings against the DFSM
        boolean runAgain = true;
        do {
            // Gather user specified string to be tested in the DFSM
            System.out.println("Enter a string you would like to test, input can be empty... ");
            userInput = keyboard.nextLine().trim();

            // Test user string in the DFSM
            boolean isAccepted = dfsm.isStringAccepted(userInput);

            // Output true if the string is accepted, false otherwise
            System.out.printf("The string that you input is accepted by your specified DFSM: %b \n", isAccepted);

            // Check if the user would like to run the program again
            System.out.println("Would you like to run the program again? Enter 'y' to run again... ");
            userInput = keyboard.nextLine().trim().toLowerCase();

            if (! userInput.equals("y")) {
                runAgain = false;
                keyboard.close();
            }

        } while (runAgain);
    }
}
