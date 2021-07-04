public class Palindrome {
    final private String wordToTest;

    public Palindrome(final String userInput) {
        wordToTest = userInput;
    }

    public boolean isPalindrome() {
        return this.wordToTest.equals(this.reverseWord(this.cleanUserInput()));
    }

    public String reverseWord(final String word) {
        char[] reverseWord = new char[word.length()];
        int counter = word.length() - 1;

        for (final char c : word.toCharArray()) {
            reverseWord[counter] = c;
            counter--;
        }

        return new String(reverseWord);
    }

    public String cleanUserInput() {
        StringBuilder stringBuilder = new StringBuilder();
        for (final char c : this.wordToTest.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public String getWordToTest() {
        return this.wordToTest;
    }
}
