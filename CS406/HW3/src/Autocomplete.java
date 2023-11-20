import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A data type that provides autocomplete functionality for a given set of string and weights,
 * using Term and BinarySearchDeluxe.
  */
public class Autocomplete {
    private Term[] terms;

    /**
     * Initialize the data structure from the given array of terms.
     */
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException();
        }
        Arrays.sort(terms,  Term::compareTo); // Sort the terms in lexicographical order before copying to instance var
        this.terms = Arrays.copyOf(terms, terms.length);
    }

    /**
     * Retrieves all terms that start with the given prefix, then sorts them in descending order of weight.
     * @param prefix prefix used to search for Terms
     * @return array of Terms that matched the prefix parameter, sorted in descending order of weight.
     */
    public Term[] allMatches(String prefix) {
        Term[] matches = findMatches(prefix); // Binary search to find set of terms with given prefix
        Arrays.sort(matches, Term.byReverseWeightOrder()); // Sort terms in reverse weight order
        return matches;
    }

    /**
     * Helper method for finding all terms that match the given prefix
     * @param prefix prefix used to search for Terms
     * @return array of Terms that matches the prefix parameter
     */
    private Term[] findMatches(String prefix) {
        Term term = new Term(prefix);
        Term[] copyOfTerms = Arrays.copyOf(terms, terms.length);
        ArrayList<Term> matches = new ArrayList<>();
        var prefixOrder = Term.byPrefixOrder(prefix.length());

        int index = BinarySearchDeluxe.firstIndexOf(copyOfTerms, term, prefixOrder);
        while (index != -1) {
            matches.add(copyOfTerms[index]); // add term at index to result array
            copyOfTerms = Arrays.copyOfRange(copyOfTerms, index+1, copyOfTerms.length); // update list of terms to remove said term
            index = BinarySearchDeluxe.firstIndexOf(copyOfTerms, term, prefixOrder); // check if there are more matches
        }

        Term[] answer = new Term[matches.size()]; // only used so that the toArray() call can infer the type of its underlying elements
        return matches.toArray(answer);
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        /*
        Term term = new Term(prefix);
        var prefixOrder = Term.byPrefixOrder(prefix.length());

        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        return count;
         */
        return 0;
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) throws IOException {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                System.out.println(results[i]);
            }
        }
        /*
        String filename = "data/cities.txt";
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt("7");
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                System.out.println(results[i]);
            }
        }
         */
    }
}
