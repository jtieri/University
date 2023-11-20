// Created by: Justin Tieri
// Date: April 9, 2018
// Useful Information: Created using C++11 in Microsoft VS Community 
// Purpose: Takes in user input, greater than 0 & less than 100 chars, and stores in std::string.  
//			Then removes excess whitespace, makes all words lowercase, and capitalizes the first letter of the string.
// TODO: 

#include <string>
#include <iostream>
#include <cstdlib>

bool isValid(std::string originalPhrase);
//Precondition: User must input a proper variable of type std::string.
//Postcondition: Returns true if phrase is greater than 0 characters and less than 100 characters. False otherwise.

bool isNextSpace(std::string phrase, int index);
//Precondition: A proper std::string must be initialized and the index of a whitespace must be passed with phrase.
//Postcondition: Returns true if the current indexed character is a whitespace and the next sequential char is whitespace also.

void clearSpace(std::string &originalPhrase);
//Precondition: A proper std::string must be initialized and there must be consecutive characters of whitespace.
//Postcondition: All occurences of sequential whitespace will be reduced to just one character of whitespace.

void toLowerCase(std::string &originalPhrase);
//Precondition: A proper std::string must be initialized.
//Postcondition: All characters in the std::string will be made lowercase.

void trim(std::string &originalPhrase);
//Precondition: A proper std::string must be initialized.
//Postcondition: Will delete leading and trailing whitespace if applicable.

int main() {
	using namespace std;

	string phrase; 
	
	do {

		cout << "Please enter a string that is neither empty and also less than 100 characters in length. \n";
		getline(cin, phrase);
		cout << endl;

	} while (!isValid(phrase)); // Runs until user inputs string that is 0 < string < 100 in length size.

	clearSpace(phrase); // Eliminates all occurences of excess whitespace.
	toLowerCase(phrase); // Changes all characters to lowercase.
	trim(phrase); // Removes leading & trailing whitespace if necessary.
	phrase.at(0) = toupper(phrase.at(0)); // Changes the first letter of the string to uppercase.

	cout << phrase << endl;

	system("PAUSE"); // Bad practice.
	return 0;
}

bool isValid(std::string originalPhrase) {
	if (originalPhrase.empty() || originalPhrase.length() > 100)
		return false;
	return true;
}

bool isNextSpace(std::string phrase, int index) {
	return isspace(phrase.at(index + 1));
}

void clearSpace(std::string &originalPhrase) {
	for (int i = 0; i < originalPhrase.length(); i++) {
		if (isspace(originalPhrase.at(i)) && isNextSpace(originalPhrase, i)) {
			originalPhrase.erase(i, 1);
			i -= 1;
		}
	}
	return;
}

void toLowerCase(std::string &originalPhrase) {
	for (int i = 1; i < originalPhrase.length(); i++) {
		originalPhrase.at(i) = tolower(originalPhrase.at(i));
	}
	return;
}

void trim(std::string &originalPhrase) {
	if (isspace(originalPhrase.at(0)))
		originalPhrase.erase(0, 1);

	if (isspace(originalPhrase.back()))
		originalPhrase.erase(originalPhrase.back(), 1);

	return;
}

