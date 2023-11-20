// Created by: Justin Tieri
// Date: March 26, 2018
// Useful Information: Created using C++11 in Microsoft VS Community 
// Purpose: Simple selection sort algorithm for use on an array of base type int
// TODO: Allow the user to define an array of specified size n, with their own entries using dynamic arrays

#include <iostream>
#include <cstdlib>

void getArray();
//Precondition: Program must be initialized and it is assumed the user is creating an array of type in
//Postcondition: An array of specified length n will be created and initialized to contain user input values

void selectSort(int array[],int lengthOfArray);
//Precondition: An array of type int must be initialized, this function works only on arrays of basetype int
//Postcondition: The array will be sorted with the smallest value to the left and the largest value to the right

int main() {
	using namespace std;

	int unsorted[] = { 10,4,22,1,16,12,9,3 };
	int length = sizeof(unsorted) / sizeof(unsorted[0]); //Takes the size in bytes of the array divided by a single
	                                                     //element to get the total length of the array
	
	selectSort(unsorted, length);
	
	for (int i = 0; i < length; i++) {
		cout << unsorted[i] << endl; //Prints every element of the sorted int array 
	}
	
	system("PAUSE");
	return 0;
}

void getArray() {
	using namespace std;
	int size = 0, entry = 0;
	char choice;

	do {
		cout << "How many entries to you wish to input? \n";
		cin >> size;
		cout << endl;

		cout << "You have entered " << size << " as the amount of entries you wish to make. \n";
		cout << "Is this correct? \n";
		cin >> choice;

	} while (choice != 'y' || choice != 'Y');

	int* newArray = new int[size];

	for (int i = 0; i < size; i++) {
		do {
			cout << "Enter the entry for element " << i << endl;
			cin >> entry;

			cout << "You have entered " << entry << " for element " << i << endl;
			cout << "Is this correc? \n";
			cin >> choice;

		} while (choice != 'y' || choice != 'Y');

		newArray[i] = entry;
	}
	// TODO: Learn how to return an array or pointer to array[0]
}

void selectSort(int array[],int lengthOfArray) {
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
