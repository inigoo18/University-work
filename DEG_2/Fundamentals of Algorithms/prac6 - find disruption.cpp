#include <iostream>
using namespace std;

int findDisruption(int array[], int fin, int ini) {
	int m, pos = -1;

	if (ini == fin + 1) {
		pos = ini;
	}

	else {
		m = (ini + fin) / 2;
		if (array[m] % 2 == 0) {
			pos = findDisruption(array, fin, m + 1);
		}
		else {
			pos = findDisruption(array, m - 1, ini);
		}

	}

	return pos;
}

void testCase() {
	int numElements;
	cin >> numElements;
	while (numElements != -1) {
		int sequence[200];
		for (int i = 0; i < numElements; i++) {
			cin >> sequence[i];
		}
		int result = findDisruption(sequence, numElements - 1, 0);
		cout << result << endl;
		cin >> numElements;
	}
} // testCase
// Main function for reading different cases until num = 0

int main() {
	testCase();
	return 0;
}


/*
Our algorithm works in the following manner:
It is supposed to find the disruption in the sequence between even and odd numbers.
It will use the Divide & Conquer method, which consists on separating the sequence in different halves.
The code will be separating the sequence into even and odd parts, until it reaches the first element
in the sequence to turn odd. Since the sequence always begins with even numbers, this method will always
work.


DATA SIZE --> n = m (the algorithm depends on m)

DIRECT CASE:
when n = fin + 1 --> T(n) = 3 (comparation to get into the first "if (ini == fin + 1)"),
assignment pos = ini and calculate fin + 1 the three instructions are made only once)

RECURSIVE CASE:
- 1 comparation (the first "if" of the algorithm)
- 4 calculations ("fin + 1" in the comparation, "(ini+fin)", "(ini + fin)/2)"
and "array[m] % 2" in the second comparation)
- 2 assignments (pos = ..., m = (ini + fin) / 2)
- 1 to evaluate descomposition ("(array, fin, m+1)" or "(array, m-1, ini)")
- T(ini) of the recursive call

RECURRENCE EQUATIONS:

		  3 if ini = fin + 1
T(n) =
		  8 + T(m) if ini != fin + 1

*/