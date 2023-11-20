// SortSuite.cpp : Defines the entry point for the console application.
// Created by: Justin Tieri
// Date: April 6, 2018
// Useful Information: Created using C++11 in Microsoft VS Community 
// Purpose: A suite of sorting algorithms to be applied to user created dynamic arrays of type int
// TODO: Create a menu where a user can choose from the various sorting algorithms

#include "stdafx.h"
#include <iostream>
#include <cstdlib>
#include <string>

int* createArray(std::string arrayName, int &arrayLength);
//Precondition: Var to hold the array's length & a pointer var to hold the returned pointer to the array must be declared.
//Postcondition: The arrays length will be initialized & the pointer to the new array's first element will be returned.

int setEntry();
//Precondition: An array must be declared for the values to be inserted into.
//Postcondition: A new int value will be returned ready to be inserted into the array.

std::string toLower(std::string &upperCase);
//Precondition: A variable of type std::string must be defined.
//Postcondition: The argument string will be returned in all lowercase.

void selectSort(int array[], int lengthOfArray);
//Precondition: An array of type int must be initialized, this function works only on arrays of basetype int
//Postcondition: The array will be sorted with the smallest value to the left and the largest value to the right

void insertSort(int array[], int lengthOfArray);
//Precondition: An array of type int must be initialized, this function works only on arrays of basetype int
//Postcondition: The array will be sorted with the smallest value to the left and the largest value to the right

void mainMenu();
//Precondition: 
//Postcondition: 

int main() {
	using namespace std;

	typedef int* IntPtr;
	int arrayLength = 0;

	IntPtr newArray = createArray("arrayOne", arrayLength);

	for (int i = 0; i < arrayLength; i++) {
		newArray[i] = setEntry();
	}
	
	system("PAUSE");
	return 0;
}

std::string toLower(std::string &upperCase) {
	for (int i = 0; i < upperCase.length(); i++) {
		upperCase.at(i) = static_cast<char> (tolower(upperCase.at(i)));
	}
	return upperCase;
}

int* createArray(std::string arrayName, int &arrayLength) {
	using namespace std;
	int newLength = 0;
	string choice;
	typedef int* IntPtr;

	do {
		cout << "How many entries will you be entering into your array? \n";
		cin >> newLength;

		cout << endl << "You have entered " << newLength << " is this correct? (Enter yes or no) \n";
		cin >> choice;
		cout << endl;

	} while ( toLower(choice) != "yes" );

	IntPtr pointer = new int[newLength];

	arrayLength = newLength;
	return pointer;
}

int setEntry() {
	using namespace std;
	int tmpEntry = 0;
	string choice;

	do {
		cout << "Enter a value to be inserted into the array. \n";
		cin >> tmpEntry;
		cout << endl;

		cout << "You have entered " << tmpEntry << " would you like to add this value to the array? (Enter yes or no) \n";
		cin >> choice;
		cout << endl;

	} while ( toLower(choice) != "yes");

	return tmpEntry;
}

void selectSort(int array[], int lengthOfArray) {
	using namespace std;

	int smallest = 0, index = 0;

	for (int i = 0; i < lengthOfArray; i++) {
		smallest = array[i];
		index = i;

		for (int j = i; j < lengthOfArray; j++) {
			if (array[j] < smallest) {
				smallest = array[j];
				index = j;
			}

			swap(array[i], array[index]);
		}
	}

	return;
}

void insertSort(int anArray[], int arrayLength) {
	using namespace std;

	for (int i = 1; i < arrayLength; i++) {
		int j = i;
		while (j > 0 && anArray[j] < anArray[j - 1]) {
			swap(anArray[j], anArray[j - 1]);
			j = j - 1;
		}
	}

	return;
}