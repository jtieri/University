#ifndef C___CHECKINGACCOUNT_H
#define C___CHECKINGACCOUNT_H


#include <iostream>
#include "Account.h"

class CheckingAccount : Account {
private:
    int minReqBalance;
public:
    CheckingAccount(const std::string& name, int accountNumber, double balance, double interestRate, int minReqBalance)
        : Account(name, accountNumber, balance, interestRate) {
        this->minReqBalance = minReqBalance;
    }

    void print() {
        Account::print();
        std::cout << "Min Req Balance: " << minReqBalance << std::endl;
    }
};

#endif //C___CHECKINGACCOUNT_H
