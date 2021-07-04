#include <iostream>
#include "Account.h"
#include "SavingsAccount.h"
#include "CheckingAccount.h"

int main() {
    Account account1("Account 1", 1, 1234, 1.23);
    SavingsAccount account2("Account 2", 2, 1234, 1.23, true);
    CheckingAccount account3("Account 3", 3, 1234, 1.23, 100);

    account1.print();
    std::cout << "------------------------------------------------ " << std::endl;
    account2.print();
    std::cout << "------------------------------------------------ " << std::endl;
    account3.print();

    return 0;
}
