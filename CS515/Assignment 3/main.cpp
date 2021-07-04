#include <iostream>
#include <vector>
#include <iomanip>

std::vector<int> readAllInts();
std::vector<std::string> readAllStrings();
std::vector<double> readAllDoubles();

// Driver program to test the functions
int main() {
    std::vector<int> intArray;
    std::vector<std::string> stringArray;
    std::vector<double> doubleArray;

    std::cout << "Please enter as many integers as you'd like on a single line then press enter... \n";
    intArray = readAllInts();

    std::cout << "Please enter as many strings as you'd like on a single line then press enter... \n";
    stringArray = readAllStrings();

    std::cout << "Please enter as many doubles as you'd like on a single line then press enter... \n";
    doubleArray = readAllDoubles();

    std::cout << "\n -------------------------------------------------------- \n ";

    std::cout << "Here are the integers you entered... \n";
    for (int num : intArray) {
        std::cout << num << " ";
    }

    std::cout << "\n -------------------------------------------------------- \n ";

    std::cout << "Here are the strings you entered... \n";
    for (const std::string& string : stringArray) {
        std::cout << string << " ";
    }

    std::cout << "\n -------------------------------------------------------- \n ";

    std::cout << "Here are the doubles you entered... \n";
    for (double num : doubleArray) {
        std::cout << std::setprecision(10) << num << " ";
    }

    std::cout << "\n -------------------------------------------------------- \n ";
     */
}

// Read a line of integers and stick them in a vector
std::vector<int> readAllInts() {
    std::vector<int> answer;

    int tmpAnswer = 0;
    while (std::cin >> tmpAnswer) {

        if(! std::cin.fail()){
            answer.push_back(tmpAnswer);
        } else {
            std::cout << "Found unexpected data type, ignoring " << tmpAnswer << std::endl;
            std::cin.clear();
        }

        // Ignore trailing whitespaces
        while (std::cin.peek() == ' ') {
            std::cin.get();
        }

        // Stop reading input at newline
        if (std::cin.peek() == '\n') {
            break;
        }
    }

    return answer;
}

// Read a line of strings and stick them in a vector
std::vector<std::string> readAllStrings() {
    std::vector<std::string> answer;

    std::string tmpAnswer = " ";
    while(std::cin >> tmpAnswer) {

        answer.push_back(tmpAnswer);

        // Ignore trailing whitespaces
        while (std::cin.peek() == ' ') {
            std::cin.get();
        }

        // Stop reading input at newline
        if (std::cin.peek() == '\n') {
            break;
        }
    }

    return answer;
}

// Read a line of doubles and stick them in a vector
std::vector<double> readAllDoubles() {
    std::vector<double> answer;

    double tmpAnswer;
    while (std::cin >> tmpAnswer) {

        if(! std::cin.fail()){
            answer.push_back(tmpAnswer);
        } else {
            std::cout << "Found unexpected data type, ignoring " << tmpAnswer << std::endl;
            std::cin.clear();
        }

        // Ignore trailing whitespaces
        while (std::cin.peek() == ' ') {
            std::cin.get();
        }

        // Stop reading input at newline
        if (std::cin.peek() == '\n') {
            break;
        }
    }

    return answer;
}