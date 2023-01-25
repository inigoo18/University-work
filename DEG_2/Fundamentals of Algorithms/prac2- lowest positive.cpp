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
int lowestPostive(int sequence[], int numElements) {
	int lowestNum = 0;
	int resultNum = -1;
	for (int i = 0; i < numElements; i++) {
		if ((sequence[i] < lowestNum || lowestNum == 0) && sequence[i] > 0) {
			lowestNum = sequence[i];
			resultNum = sequence[i];
		}
	}

	return resultNum; // Remove this, only included here to be compilable
	// END OF YOUR CODE
}


//----------------------------------------

// Function to convert a bool in a representative string


// Function to test one single case
void testCase() {
	int numElements;
	cin >> numElements;
	int sequence[10000];
	for (int i = 0; i < numElements; i++) {
		cin >> sequence[i];
	}
	int result = lowestPostive(sequence, numElements);
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