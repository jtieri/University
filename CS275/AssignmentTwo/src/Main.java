public class Main {
    public static void main(String[] args) {
        // Polynomial One Tests
        Polynomial polynomial1 = new Polynomial();
        System.out.println(polynomial1.toString());

        polynomial1.addToCoefficient(25, 25);
        polynomial1.assignCoefficient(24, 24);
        polynomial1.assignCoefficient(26, 26);
        polynomial1.assignCoefficient(2, 0);
        System.out.println(polynomial1.toString());
        System.out.println("                                                                   ");
        // Polynomial Two Tests
        Polynomial polynomial2 = new Polynomial(polynomial1);

        System.out.println(polynomial2.toString());
        System.out.println(polynomial2.coefficient(25));
        System.out.println("                                                                   ");

        // Polynomial Three Tests
        Polynomial polynomial3 = new Polynomial(2);

        polynomial3 = polynomial3.add(polynomial2);
        System.out.println(polynomial3.toString());
        System.out.println("                                                                   ");

        // Polynomial Four Test
        Polynomial polynomial4 = new Polynomial(-1);

        polynomial4.assignCoefficient(2, 3);
        polynomial4.assignCoefficient(-6, 2);
        polynomial4.assignCoefficient(2, 1);
        System.out.println(polynomial4.toString());
        System.out.println(polynomial4.eval(3));

        System.out.println(polynomial1.multiply(polynomial4));
    }
}
