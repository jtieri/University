public class Main {
    public static void main(String[] args) {
        Account checkingAccount1 = new CheckingAccount("Account 1", 1, 0, 1.23, 100);
        Account savingsAccount2 = new SavingsAccount("Account 2", 2, 0, 1.23, true);
        Account account3 = new Account("Account 1", 1, 0, 1.23);

        Account[] accounts = { checkingAccount1, savingsAccount2, account3 };

        for (Account account : accounts) {
            account.print();
            System.out.println("-----------------------------------------------------------------------------------");
        }
    }
}
