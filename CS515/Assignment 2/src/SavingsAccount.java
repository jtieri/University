public class SavingsAccount extends Account {
    private boolean eligibleHighRisk;

    public SavingsAccount(final String name, final int accountNumber, final double balance,
                          final double interestRate, final boolean eligibleHighRisk) {

        super(name, accountNumber, balance, interestRate);
        this.eligibleHighRisk = eligibleHighRisk;
    }

    public void print() {
        super.print();
        System.out.println("Eligible for short-term investments: " + eligibleHighRisk);
    }

    public boolean isEligibleHighRisk() {
        return eligibleHighRisk;
    }

    public void setEligibleHighRisk(final boolean eligibleHighRisk) {
        this.eligibleHighRisk = eligibleHighRisk;
    }
}
