//---------------------------------------------------------
// Problem Palindrome Sequences
//
// http://fal.fdi.ucm.es
//
// <Include your names and surnames here>
//---------------------------------------------------------

#include <iostream>
using namespace std;
#include <string>

// ---------------------------------
// It returns the lowest positive or -1 if not any positive value
int subsequenceSum(int sequence[], int numElements, int length) {
	int highestResult = 0;
	int result = 0;
	int highestIdx = 0;
	for (int i = 0; i <= numElements - length; i++) {
		result = 0;
		for (int j = i; j < i + length; j++) {
			result += sequence[j];
		}
		if (result > highestResult){
			highestResult = result;
			highestIdx = i;
		}
	}
	return highestIdx;
}


//----------------------------------------

// Function to convert a bool in a representative string


// Function to test one single case
void testCase() {
	int numElements;
	int length;
	cin >> numElements;
	cin >> length;
	int sequence[100];
	for (int i = 0; i < numElements; i++) {
		cin >> sequence[i];
	}
	int result = subsequenceSum(sequence, numElements, length);
	cout << result << endl;
} // testCase

//---------------------------------------------------------

// Main function for reading different cases
int main() {
	unsigned int numCases, i;
	cin >> numCases;
	for (i = 0; i < numCases; ++i) {
		testCase();
	}
	return 0;
} // main