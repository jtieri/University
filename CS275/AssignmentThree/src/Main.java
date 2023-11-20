public class Main {
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();
        p1.assign_coef(-1, 50);
        p1.assign_coef(0, 25);
        p1.assign_coef(5, 100);
        System.out.println(p1);

        Polynomial p2 = new Polynomial(p1);
        System.out.println(p2);
        p2.add_to_coef(1, 50);
        System.out.println(p2);
        System.out.println(p1 == p2);
        System.out.println(p1.equals(p2));

        Polynomial p3 = new Polynomial(2);
        p3.assign_coef(2, 2);
        System.out.println(p3);
        System.out.println(p3.eval(2));
        System.out.println(p3.eval(0));
        System.out.println(p3.eval(1));
    }
}
