#ifndef C___ACCOUNT_H
#define C___ACCOUNT_H

#include <string>

class Account {
private:
    std::string name;
    int accountNumber;
    double accountBalance;
    double interestRate;
public:
    Account(const std::string& name, int accountNumber, double balance, double interestRate) {
        this->name = name;
        this->accountNumber = accountNumber;
        this->accountBalance = balance;
        this->interestRate = interestRate;
    }

    void print() {
        std::cout << "Name: " + this->name << std::endl;
        std::cout << "Account Number: " << this->accountNumber << std::endl;
        std::cout << "Account Balance: " << this->accountBalance << std::endl;
        std::cout << "Interest Rate: " << this->interestRate << std::endl;
    }
};

#endif //C___ACCOUNT_H