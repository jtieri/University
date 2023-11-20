/**
 * A simple class that represents a Quadratic Expression in mathematics.
 *
 * @author Justin T.
 */
public class QuadraticExpression implements Cloneable{
    private double a, b, c;

    /**
     * Default constructor that will return a new instance of the class with instance variables all set to 0.
     */
    public QuadraticExpression() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
    }

    /**
     * Constructor for setting the values of the instance variables at the time of creation.
     * @param a The coefficient leading x^2
     * @param b The coefficient leadin x
     * @param c The trailing coefficient
     */
    public QuadraticExpression(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Evaluates the calling QuadraticExpression for a specific user provided value of x.
     * @param x The value at which the QuadraticExpression will be evaluated at.
     * @return The value of a * x^2 + b * x + c
     */
    public double evaluate(double x) {
        return (this.a * Math.pow(x, 2)) + (this.b * x) + this.c;
    }

    /**
     * Returns the amount of real roots (solutions) for the calling QuadraticExpression.
     * @return 0, 1, 2, or 3 for infinite roots.
     */
    public int numberOfRoots() {
        // Infinite real roots.
        if (this.a == 0 && this.b == 0 && this.c == 0) {
            return 3;
        }

        // No real roots.
        if ((this.a == 0 && this.b == 0) && this.c != 0) {
            return 0;
        }

        // Only real root is x = -c/b
        if (this.a == 0 && this.b != 0) {
            return 1;
        }

        // No real roots.
        if (this.a != 0 && ((this.b * this.b) < 4 * this.a * this.c)) {
            return 0;
        }

        // Only real root is x = -b/2a
        if ((this.a != 0) && ((this.b * this.b) == 4 * this.a * this.c )) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Uses the Quadratic formula to solve the calling QuadraticExpression and returns it's smallest root.
     * @return The smallest root for the calling QuadraticExpression.
     * @throws Exception The calling QuadraticExpression has no real roots.
     */
    public double smallerRoot() throws Exception {
        /*
      Depending on the equation ax^2 + bx + c = 0:
        if no roots, throw exception
        if single root, return it
        if two roots, return  the smaller root
        if infinite root, return -Double.MAX_VALUE
         */
        if (this.numberOfRoots() == 0) {
            throw new Exception("This quadratic has no real roots.");
        }

        double sqrtValue = Math.sqrt(Math.abs(this.b*this.b - 4*this.a*this.c));
        double rootOne = ( (-this.b + sqrtValue) / (2 * this.a) );
        double rootTwo = ( (-this.b - sqrtValue) / (2 * this.a) );

        if (rootOne == rootTwo) {
            return rootOne;
        } else if (rootOne > rootTwo) {
            return rootTwo;
        } else {
            return -Double.MAX_VALUE;
        }
    }

    /**
     * Uses the Quadratic formula to solve the calling QuadraticExpression and returns it's largest root.
     * @return The largest root for the calling QuadraticExpression.
     * @throws Exception The calling QuadraticExpression has no real roots.
     */
    public double largerRoot() throws Exception {
        /*
      Depending on the equation ax^2 + bx + c = 0:
        if no roots, throw exception
        if single root, return it
        if two roots, return  the larger root
        if infinite root, return Double.MAX_VALUE
         */
        if (this.numberOfRoots() == 0) {
            throw new Exception("This quadratic has no real roots.");
        }

        double sqrtValue = Math.sqrt(Math.abs(this.b*this.b - 4*this.a*this.c));
        double rootOne = ( (-this.b + sqrtValue) / (2 * this.a) );
        double rootTwo = ( (-this.b - sqrtValue) / (2 * this.a) );

        if (rootOne == rootTwo) {
            return rootOne;
        } else if (rootOne > rootTwo) {
            return rootOne;
        } else {
            return Double.MAX_VALUE;
        }
    }

    /**
     * Adds the coefficients of one QuadraticExpression to another.
     * @param q QuadraticExpression that is non-null.
     */
    public void add(QuadraticExpression q) {
        this.a += q.getA();
        this.b += q.getB();
        this.c += q.getC();
    }

    /**
     * Attempts to clone the calling QuadraticExpression and provide a deep copy of it.
     * @return An exact copy of the calling QuadraticExpression.
     */
    @Override
    public QuadraticExpression clone() {
        QuadraticExpression quadraticExpression;

        try {
            quadraticExpression = (QuadraticExpression) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Error: calling object is not cloneable.");
        }

        return quadraticExpression;
    }

    /**
     * Tests whether another QuadraticExpression has the same coefficients as the calling QuadraticExpression.
     * @param obj An object to test against the calling QuadraticExpression.
     * @return true if the objects have the same values or are the same instance, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof QuadraticExpression)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        QuadraticExpression temp = (QuadraticExpression) obj;

        return this.a == temp.getA() && this.b == temp.getB() && this.c == temp.getC();
    }

    /**
     * Returns a textual representation of the calling QuadraticExpression.
     * @return Text based representation of the calling object.
     */
    @Override
    public String toString() {
        return this.a  + "x^2" + " " + "+" + this.b + "x" + " " + "+" + this.c;
    }

    /**
     * Sets new value for a.
     * @param newA New value of a.
     */
    public void setA(double newA) {
        this.a = newA;
    }

    /**
     * Sets new value for b
     * @param newB New value of b.
     */
    public void setB(double newB) {
        this.b = newB;
    }

    /**
     * Sets new value for c.
     * @param newC New value of c.
     */
    public void setC(double newC) {
        this.c = newC;
    }

    /**
     * Retrieve the value of a.
     * @return The value of a.
     */
    public double getA() {
        return this.a;
    }

    /**
     * Retrieve the value of b.
     * @return The value of b.
     */
    public double getB() {
        return this.b;
    }

    /**
     * Retrieve the value of c.
     * @return The value of c.
     */
    public double getC() {
        return this.c;
    }

    /**
     * Creates a new QuadraticExpression whose coefficients are the sum of the provided QuadraticExpressions' coefficients.
     * @param q1 QuadraticExpression that is non-null.
     * @param q2 QuadraticExpression that is non-null.
     * @return New QuadraticExpression whose coefficients are the sum of the provided QuadraticExpressions' coefficients.
     */
    public static QuadraticExpression sum(QuadraticExpression q1, QuadraticExpression q2) {
        return new QuadraticExpression(q1.getA() + q2.getA(), q1.getB() + q2.getB(), q1.getC() + q2.getC());
    }

    /**
     * Creates a new QuadraticExpression whose coefficients are the product of the provided QuadraticExpression's
     * coefficients and the value r.
     * @param r Value which will be multiplied by the Quadratic's coefficients.
     * @param q QuadraticExpression that is non-null.
     * @return New QuadraticExpression whose coefficients are the product of the provided QuadraticExpression's
     *         coefficients and the value r.
     */
    public static QuadraticExpression scale(double r, QuadraticExpression q) {
        return new QuadraticExpression(q.getA() * r, q.getB() * r, q.getC() * r);
    }
}
