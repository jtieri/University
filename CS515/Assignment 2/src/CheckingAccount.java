public class CheckingAccount extends Account {
    private double minReqBalance;

    public CheckingAccount(final String name, final int accountNumber, final double balance,
                           final double interestRate, final double minReqBalance) {

        super(name, accountNumber, balance, interestRate);
        this.minReqBalance = minReqBalance;
    }

    public void print() {
        super.print();
        System.out.println("Minimum Required Balance: " + minReqBalance);
    }

    public double getMinReqBalance() {
        return minReqBalance;
    }

    public void setMinReqBalance(final double minReqBalance) {
        this.minReqBalance = minReqBalance;
    }
}
