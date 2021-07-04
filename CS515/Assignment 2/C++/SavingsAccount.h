#ifndef C___SAVINGSACCOUNT_H
#define C___SAVINGSACCOUNT_H


#include <iostream>
#include "Account.h"

class SavingsAccount : Account {
private:
    bool eligibleHighRisk;
public:
    SavingsAccount(const std::string& name, int accountNumber, double balance, double interestRate, bool eligibleHighRisk)
        : Account(name, accountNumber, balance, interestRate) {
        this->eligibleHighRisk = eligibleHighRisk;
    }

    void print() {
        Account::print();
        std::cout << "Eligible Short-Term Investment: " << eligibleHighRisk << std::endl;
    }
};

#endif //C___SAVINGSACCOUNT_H
