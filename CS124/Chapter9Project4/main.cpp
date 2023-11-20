// Created by: Justin Tieri
// Date: April 19, 2018
// Useful Information: Created using C++11 in CLion
// Purpose: Function that takes in a C-String parameter and then reverses it.

#include <iostream>
#include <cstring>

void swap(char &front, char &rear) {
    char tmp = front;
    front = rear;
    rear = tmp;
    return;
}

void reverse(char cString[]) {
    typedef char* ChrPtr;

    ChrPtr front, rear;
    front = &cString[0];
    rear = &cString[strlen(cString)-1];

    // Tests
    //std::cout << *front << std::endl;
    //std::cout << *rear << std::endl;

    while(front != rear) {
        swap(*front, *rear);
        front++;
        rear--;
    }

    return;
}


int main() {

    char phrase[] = "hello sir";
    char phraseTwo[] = "Now mow brown cow";
    char phraseThree[] = "Test.";

    std::cout << phrase << std::endl;
    reverse(phrase);
    std::cout << phrase << std::endl;

    std::cout << std::endl;

    std::cout << phraseTwo << std::endl;
    reverse(phraseTwo);
    std::cout << phraseTwo << std::endl;

    std::cout << std::endl;

    std::cout << phraseThree << std::endl;
    reverse(phraseThree);
    std::cout << phraseThree << std::endl;

    system("PAUSE");
    return 0;
}