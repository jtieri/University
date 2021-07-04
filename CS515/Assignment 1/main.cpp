#include <iostream>
#include <limits>

bool isInteger(std::string& userInput);
bool isDouble(std::string& userInput);

int main() {
    int userInt;
    double userDouble;
    std::string userString;
    std::string tmpAnswer;

    // Gather an int from the user
    do {
        std::cout << "Enter an integer... " << std::endl;
        std::cin >> tmpAnswer;
    } while (!isInteger(tmpAnswer));

    userInt = std::stoi(tmpAnswer);
    std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n'); // Flush the stdin buffer so the next
                                                                              // read operation doesn't take in
                                                                              // unexpected garbage left in the buffer

    // Gather a string from the user
    std::cout << "Enter a string... " << std::endl;

    while (std::cin >> tmpAnswer) {
        userString += tmpAnswer;
        userString += ' ';

        while (std::cin.peek() == ' ') {
            std::cin.get();
        }
        if (std::cin.peek() == '\n') {
            break;
        }
    }

    // Gather a double from the user
    do {
        std::cout << "Enter a double... " << std::endl;
        std::cin >> tmpAnswer;
    } while (!isDouble(tmpAnswer));
    userDouble = std::stod(tmpAnswer);

    // Display output for testing
    std::cout << "You entered the integer: " << userInt    << std::endl;
    std::cout << "You entered the string: "  << userString << std::endl;
    std::cout << "You entered the double: "  << userDouble << std::endl;

    return 0;
}

// Determines if a string represents a valid integer value
bool isInteger(std::string& userInput) {
    for (char c : userInput) {
        if(! std::isdigit(c)) {
            return false;
        }
    }

    return true;
}

// Determines if a string represents a valid double value
bool isDouble(std::string& userInput) {
    bool oneDecimal = false;        // used to ensure, at max, only one decimal appears in a value

    for (char c : userInput) {
        if (!std::isdigit(c)) {
            if (c == '.' && !oneDecimal) { // If a second decimal point appears, the input isnt a double so return false
                oneDecimal = true;
            } else {
                return false;
            }
        }
    }

    return true;
}
