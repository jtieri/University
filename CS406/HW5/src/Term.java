import java.util.Arrays;
import java.util.Comparator;

/**
 * An immutable data type that represents an autocomplete term: a query string and an associated real-valued weight.
 */
public class Term implements Comparable<Term> {
    private final String query;
    private final long weight;

    /**
     * Construct a term given the associated query string, having weight 0.
     * @param query string
     */
    public Term(String query) {
        if (query == null) {
            throw new NullPointerException("Please enter a valid query");
        }

        this.query = query;
        this.weight = 0;
    }

    /**
     * Construct a term given the associated query string and weight.
     * @param query string
     * @param weight non-negative numerical value
     */
    public Term(String query, long weight) {
        if (query == null) {
            throw new NullPointerException("Please enter a valid query");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Enter a non-negative weight");
        }

        this.query = query;
        this.weight = weight;
    }

    /**
     *  A reverse-weight comparator.
     * @return Comparator with underlying type Term which compares Terms' weight in reverse order
     */
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    /**
     * Helper reverse-weight comparator.
     */
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term v, Term w) {
            if (v.weight == w.weight) {
                return 0;
            }

            return v.weight < w.weight ? 1 : -1;
        }
    }

    /**
     * A prefix-order comparator.
     * @param r non-negative integer value used to compare n-chars of a prefix
     * @return Comparator with underlying type Term which compares Terms'
     */
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Enter a non-negative weight");
        }

        return new PrefixOrder(r);
    }

    /**
     * Helper prefix-order comparator.
     */
    private static class PrefixOrder implements Comparator<Term> {
        private int r;

        PrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term v, Term w) {
            if(r > v.query.length() || r > w.query.length()) {
                return -1;
            }
            return v.query.substring(0, r).compareTo(w.query.substring(0, r));
        }
    }

    /**
     * Compare this term to that in lexicographic order by query and
     * @param that term which that is compared to
     * @return negative, zero, or positive integer based on whether this term is smaller, equal to, or
     * larger than that term.
     */
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    /**
     * A string representation of this term.
     * @return concatenation of the term's instance variables
     */
    public String toString() {
        return weight + "\t" + query;
    }

    /**
     * Get the Terms weight
     * @return the instance variable weight of the callee
     */
    public long getWeight() {
        return this.weight;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        System.out.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
        System.out.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
        /*
        String filename = "data\\baby-names.txt";
        int k = 8;
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        System.out.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
        System.out.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            System.out.println(terms[i]);
        }
         */
    }
}
