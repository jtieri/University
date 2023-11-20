import java.util.Iterator;
import java.util.LinkedList;

public class Polynomial  {
    public static class Term {
        public double coefficient;
        public int exponent;

        public Term(int exp, double coeff) {
            coefficient= coeff;
            exponent = exp;
        }
    }

    public LinkedList<Term> termList;

    /**
     * Postcondition:  Creates a polynomial which is 0.
     * **/
    public Polynomial() {
        termList = new LinkedList<>();
        termList.add(new Term(0,0));
    }

    /**
     * Postcondition:  Creates a polynomial which has a single term a0*x^0
     * @param a0 The value to be set as the coefficient of the constant (x^0) term.
     * **/
    public Polynomial(double a0) {
        termList = new LinkedList<>();
        termList.add(new Term(0, a0));
    }

    /**
     * Postcondition:  Creates a copy of Polynomial p
     * @param p the Polynomial which is to be copied.
     * **/
    public Polynomial(Polynomial p) {
        termList = new LinkedList<>(p.termList);
    }

    /**
     * Postcondition:  Adds the given amount to the coefficient of the specified exponent.
     * @param amount The amount to be added to the coefficient.
     * @param exponent The degree of the term whose coefficient is to be modified.
     * (1) Note that the exponent can be arbitrary
     * (2) If you want, you can assume the amount is not 0, however, it is possible that
     *   after you add the amount, the coefficient becomes 0, in which case, you should delete the TermNode
     * **/
    public void add_to_coef(double amount, int exponent) {
        if (amount != 0) {
            if (exponent > termList.size() - 1) {
                for (int i = termList.size(); i < exponent; i++) {
                    termList.add(new Term(i, 0));
                }

                termList.add(new Term(exponent, amount));
            } else {
                termList.get(exponent).coefficient += amount;
            }
        }
    }

    /**
     * Postcondition:  Sets the coefficient of a specified term to a specified value.
     * @param coefficient The new value of the coefficient.
     * @param exponent The degree of the term whose coefficient is to be modified.
     * (1) Note that the exponent can be arbitrary
     * (2) The coefficient may be 0
     * **/
    public void assign_coef(double coefficient, int exponent) {
        if (exponent > termList.size() - 1) {
            for (int i = termList.size(); i < exponent; i++) {
                termList.add(new Term(i, 0));
            }

            termList.add(new Term(exponent, coefficient));
        } else {
            termList.get(exponent).coefficient = coefficient;
        }
    }

    /**
     * Postcondition:   Returns coefficient at specified exponent of this polynomial.
     * @param exponent The exponent of the term whose coefficient is sought.
     * @return The coefficient of the term.
     * **/
    public double coefficient(int exponent) {
        if (exponent > termList.size() - 1) {
            return 0;
        } else {
             return termList.get(exponent).coefficient;
        }
    }

    /**
     * @return The value of this Polynomial with the given value for the variable x.
     * @param x The value at which the Polynomial is to be evaluated.
     ***/
    public double eval(double x) {
        double total = 0;

        for (int i = termList.size() - 1; i >= 0; i--) {
            total = termList.get(i).coefficient + (x * total);
        }

        return total;
    }


    /**
     * @return Returns a string representing the polynomial expression with coefficients displayed to the tenths place,
     * omitting any coefficients that are zero. If all coefficients are 0, then the zero function is reported.
     **/
    public String toString() {
        int counter = 0;

        StringBuilder textualRepresentation = new StringBuilder();
        Iterator<Term> iterator = termList.descendingIterator();

        while (iterator.hasNext()) {
            Term term = iterator.next();

            // Handle the first term appropriately.
            if (term.coefficient != 0 && counter == 0) {
                textualRepresentation.append(term.coefficient);
                textualRepresentation.append("x^");
                textualRepresentation.append(term.exponent);
                textualRepresentation.append(" ");
            }

            // Handle all other non-zero terms.
            if (term.coefficient != 0 && counter != 0) {
                textualRepresentation.append("+");
                textualRepresentation.append(" ");
                textualRepresentation.append(term.coefficient);
                textualRepresentation.append("x^");
                textualRepresentation.append(term.exponent);
                textualRepresentation.append(" ");
            }

            counter++;
        }

        return textualRepresentation.toString().equals("") ? "0" : textualRepresentation.toString();
    }

    /**
     * @return Returns a Polynomial that is the sum of p and this Polynomial.
     * @param p The Polynomial to be added to the activating Polynomial.
     * **/
    public Polynomial add(Polynomial p) {
        Polynomial newPolynomial = new Polynomial(this);

        int i = newPolynomial.termList.size() > p.termList.size() ?
                newPolynomial.termList.size() - 1 : p.termList.size() - 1;

        while (i > 0) {
            newPolynomial.termList.get(i).coefficient += p.termList.get(i).coefficient;
            i--;
        }

        return newPolynomial;
    }


    /**
     * Postcondition:  Returns a new polynomial obtained by multiplying this term and p. For example, if this polynomial
     * is 2x^2 + 3x + 4 and p is 5x^2 - 1x + 7, then at the end of this function, it will return the polynomial
     * 10x^4 + 13x^3 + 31x^2 + 17x + 28.
     *
     * @param p The polynomial to be multiplied.
     * @return The product of the activating Polynomial and p.
     **/
    public Polynomial multiply(Polynomial p) {
        // Set newPolynomial to be a copy of the whichever Polynomial has more terms.
        Polynomial newPolynomial = new Polynomial(this.termList.size() > p.termList.size() ? this : p);

        // Get an iterator for the smaller sized Polynomial.
        Iterator<Term> otherIterator = this.termList.size() < p.termList.size() ? this.termList.iterator() : p.termList.iterator();

        while (otherIterator.hasNext()) {
            Term term = otherIterator.next();

            if (newPolynomial.termList.get(term.exponent).coefficient == 0) {
                newPolynomial.termList.set(term.exponent, term);
            } else if (term.coefficient == 0) {
                // If the term's coefficient is 0 just ignore
            } else {
                newPolynomial.termList.get(term.exponent).coefficient *= term.coefficient;
                newPolynomial.termList.get(term.exponent).exponent += term.exponent ;
            }
        }

        return newPolynomial;
    }
}
