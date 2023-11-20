import java.util.Arrays;
import java.util.Comparator;

/**
 * Implements binary search for clients that may want to know the index of
 * either the first or last key in a (sorted) collection of keys.
 */
public class BinarySearchDeluxe {
    /**
     * The index of the first key in a[] that equals the search key, or -1 if no such key.
     * @param a array of keys
     * @param key key that is being sought out in a
     * @param comparator used for comparing the keys to one another
     * @param <Key> the underlying data structure being compared
     * @return the index where the first occurrence of key is found in the array of keys or -1 if the key is not found
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if(comparator == null || a == null || key == null) {
            throw new NullPointerException("Check that all of the parameters have been initialized");
        }
        final int len = a.length;
        int low = 0;
        int high = len - 1;
        int result = -1;

        while(low <= high) {
            int mid = ((low + high) / 2);
            int index = comparator.compare(key, a[mid]);
            if(index == 0) {
                result = mid;
                high = mid - 1;
                low = 0;
            } else if(index < 0) {
                high = mid - 1;
                low = 0;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    /**
     * The index of the last key in a[] that equals the search key, or -1 if no such key.
     * @param a array of keys
     * @param key key that is being sought out in a
     * @param comparator used for comparing the keys to one another
     * @param <Key> the underlying data structure being compared
     * @return the index where the last occurrence of key is found in the array of keys or -1 if the key is not found
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if(comparator == null || a == null || key == null) {
            throw new NullPointerException("Check that all of the parameters have been initialized");
        }
        int low = 0;
        int high = a.length - 1;
        int result = -1;

        while( low <= high) {
            int mid = (low + high) / 2;
            int index = comparator.compare(key, a[mid]);
            if(index == 0) {
                result = mid;
                low = mid + 1;
            } else if(index < 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return result;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        System.out.println(count);
        /*
        String filename = "data\\wiktionary.txt";
        String prefix = "the";
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        //System.out.println(i);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        //System.out.println(j);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        System.out.println(count);
         */
    }
}
