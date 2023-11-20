import java.util.Arrays;

public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        this.coefficients = new double[1];
        this.coefficients[0] = 0;
    }

    public Polynomial(double coefficient) {
        this.coefficients = new double[1];
        this.coefficients[0] = coefficient;
    }

    public Polynomial(Polynomial polynomial) {
        this.coefficients = polynomial.coefficients.clone();
    }

    //POSTCONDITION: Adds the given amount to the coefficient of the specified exponent.
    //     Note: the exponent is allowed to be greater than the degree of the polynomial
    public void addToCoefficient(double amount, int exponent) {
        if (exponent > this.coefficients.length) {
            this.coefficients = Arrays.copyOf(this.coefficients, this.coefficients.length + exponent + 20);
        }

        this.coefficients[exponent] += amount;
    }

    //POSTCONDITION: Sets the coefficient for the specified exponent.
    //     Note: the exponent is allowed to be greater than the degree of the polynomial
    public void assignCoefficient(double coefficient, int exponent) {
        if (exponent > this.coefficients.length) {
            this.coefficients = Arrays.copyOf(this.coefficients, this.coefficients.length + exponent + 20);
        }

        this.coefficients[exponent] = coefficient;
    }

    //POSTCONDITION: Returns coefficient at specified exponent of this polynomial.
    //     Note: the exponent is allowed to be greater than the degree of the polynomial
    //     e.g. if p = x + 1; p.coeffcient(3) should return 0
    public double coefficient(int exponent) {
        if (exponent > this.coefficients.length) {
            return 0;
        } else {
            return this.coefficients[exponent];
        }
    }

    //POSTCONDITION: The return value is the value of this polynomial with the given value for the variable x.
    //     Do not use power method from Math, which is very low efficient
    public double eval(double x) {
        double p = 0;

        for (int i = this.coefficients.length - 1; i >= 0; i--) {
            p = this.coefficients[i] + (x * p);
        }

        return p;
    }

    //POSTCONDITION: return the polynomial as a string like “2x^2 + 3x + 4”
    //
    //        Important only non-zero terms unless the polynomial is 0
    public String toString() {
        StringBuilder text = new StringBuilder();

        for (int i = this.coefficients.length - 1; i >= 0; i--) {
            if (this.coefficients[i] == 0) {
                continue;
            }

            text.append(" + ");
            text.append(this.coefficients[i]);
            text.append(" ");
            text.append("x^");
            text.append(i);
        }

        return text.toString().equals("") ? "0" : text.toString();
    }

    //POSTCONDITION:
    //                this object and p are not changed
    //                return a polynomial that is the sum of   p and this polynomial
    public Polynomial add(Polynomial p) {
        Polynomial polynomial = new Polynomial();

        polynomial.coefficients = Arrays.copyOf(this.coefficients,
                this.coefficients.length > p.coefficients.length ? this.coefficients.length : p.coefficients.length);

        for (int i = 0; i < p.coefficients.length; i++) {
            polynomial.coefficients[i] = polynomial.coefficients[i] + p.coefficients[i];
        }

        return polynomial;
    }

    //POSTCONDITION:
    //     this object and p should not be changed
    //     returns a new polynomial obtained by multiplying this term and p. For example, if this polynomial is
    //     2x^2 + 3x + 4 and p is 5x^2 - 1x + 7, then at the end of this function, it will return the polynomial 1
    //     0x^4 + 13x^3 + 31x^2 + 17x + 28.
    public Polynomial multiply(Polynomial p) {
        Polynomial polynomial = new Polynomial();

        polynomial.coefficients = Arrays.copyOf(this.coefficients,
                this.coefficients.length + p.coefficients.length + 20);

        for (int i = 0; i <= this.coefficients.length - 1; i++) {
            for (int j = 0; j <= p.coefficients.length - 1; j++) {
                polynomial.coefficients[i + j] += (this.coefficients[i] * p.coefficients[j]);
            }
        }

        return polynomial;
    }
}
