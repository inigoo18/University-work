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
// It determines whether a sequence of a given number of elements is palindrome
bool isPalindrome(int sequence[], int numElements) {
	bool OK = true;
	for (int i = 0; i < numElements / 2; i++) {
		//cout << sequence[i] << " - = - " << sequence[numElements - i - 1] << endl;
		if (sequence[i] != sequence[numElements - i - 1]) {
			OK = false;
		}

	}

	return OK; // Remove this, only included here to be compilable
	// END OF YOUR CODE HERE
}


//----------------------------------------

// Function to convert a bool in a representative string
string boolToStr(bool value) {
	string str;
	if (value)
		str = "true";
	else
		str = "false";
	return str;
}

// Function to test one single case
void testCase() {
	int numElements;
	cin >> numElements;
	int sequence[10000];
	for (int i = 0; i < numElements; i++) {
		cin >> sequence[i];
	}
	bool result = isPalindrome(sequence, numElements);
	cout << boolToStr(result) << endl;
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