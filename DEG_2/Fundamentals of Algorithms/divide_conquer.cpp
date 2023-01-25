#include <iostream>
using namespace std;

int Staircase(int seq[], int numElements, int widest, int beg, int fin) {
	int m, pos= -1;

	if (widest == numElements) {
		return seq[0];
	}
	cout << "Beg is: " << beg << endl;
	cout << "Fin is: " << fin << endl;
	if (beg == fin) { // BASE CASE
		pos = beg;
	}
	else {
		m = (beg + fin) / 2;
		cout << "Current pos: " << seq[m] << " w/ idx: " << m << endl;
		int idx1 = 1;
		while (idx1 < widest && seq[m] == seq[m - idx1]) {
			idx1++;
		}
		int idx2 = 1;
		while (idx2 < widest && seq[m] == seq[m + idx2]) {
			idx2++;
		}
		if (seq[m] < seq[m - idx1]) {
			pos = Staircase(seq, numElements, widest, beg, fin - 1);
		}
		else if (seq[m] < seq[m + idx2]) {

			pos = Staircase(seq, numElements, widest, beg + 1, fin);
		}
		else {
			return seq[m];
		}
		
	}


	return pos;
}


int main() {
	int numElements = 0;
	int widest = 0;
	while (numElements != -1){
		int seq[1000];
		cin >> numElements;
		cin >> widest;
		for (int i = 0; i < numElements; i++) {
			cin >> seq[i];
		}
		cout << Staircase(seq, numElements, widest, 0, numElements - 1) << endl;
	}

	return 0;
}