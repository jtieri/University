#include <iostream>
#include <cstdlib>

void swap(int& a, int& b);
//
//

void insertSort(int anArray[], int arrayLength);
//Precondition:
//Postcondition:

int main(){
	using namespace std;

	int testArray[] = { 8,10,1,15,2,4,12,6 };
	int length = sizeof(testArray) / sizeof(testArray[0]);

	insertSort(testArray, length);

	for (int i = 0; i < length; i++) {
		cout << testArray[i] << endl;
	}

	system("PAUSE");
	return 0;
}

void swap(int& a, int& b) {
	int tmp = a;
	a = b;
	b = tmp;
	return;
}

void insertSort(int anArray[], int arrayLength) {

	for (int i = 1; i < arrayLength; i++) {
		int j = i;
		while (j > 0 && anArray[j] < anArray[j - 1]) {
			swap(anArray[j], anArray[j - 1]);
			j = j - 1;
		}
	}
	return;
}