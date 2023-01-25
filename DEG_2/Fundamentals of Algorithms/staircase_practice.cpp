#include <iostream>
using namespace std;


int ascendingStaircase(int sequence[], int numElements, int &widest) { // returns number of elements on its left side and length of widest step
	int lastNum = sequence[0];
	int numSteps = 0;
	int recordWidest = 1;
	int wide = 1;
	bool isAsc = true;
	for (int i = 1; i < numElements; i++) {
		if (sequence[i] == lastNum) {
			wide++;
			if (isAsc) {
				numSteps++;			//cout << sequence[i] << endl;
			}
		}
		else if (sequence[i] > lastNum) {
			numSteps++;
			wide = 1;
			isAsc = true;
		}
		else if (sequence[i] < lastNum) {
			isAsc = false;
			wide = 1;
		}
		if (wide > recordWidest) {
			recordWidest = wide;
		}
		lastNum = sequence[i];
	}
	widest = recordWidest;
	return numSteps;
}

int descendingStaircase(int sequence[], int numElements, int &widest) { // returns number of elements on its left side and length of widest step
	int lastNum = sequence[0];
	int numSteps = 0;
	int recordWidest = 1;
	int wide = 1;
	bool isDesc = false;
	for (int i = 1; i < numElements; i++) {
		if (sequence[i] == lastNum) {
			if (isDesc) {
				numSteps++;
			}
			wide++;
		}
		else if (sequence[i] < lastNum) {
			numSteps++;
			isDesc = true;
			wide = 1;
		}
		else if (sequence[i] > lastNum) {
			isDesc = false;
			wide = 1;
		}
		if (wide > recordWidest) {
			recordWidest = wide;
		}
		lastNum = sequence[i];
	}
	widest = recordWidest;
	return numSteps;
}

void Staircase(int sequence[], int numElements) { // returns number of elements on its left side and length of widest step
	int idx = 0;
	int widest = 0;
	int widest2 = 0;
	bool hasDesc = false;
	bool isStaircase = true;
	while (idx < numElements) {
		if (sequence[idx] < sequence[idx + 1]) {
			if (hasDesc) { // if the element behind it is higher it means that there's another ascending staircase so its not a stairway.
				isStaircase = false;
			}
		}
		else if (sequence[idx] > sequence[idx + 1]) {
			hasDesc = true;
		}
		idx++;
	}
	int AscElems = ascendingStaircase(sequence, numElements, widest);
	int DescElems = descendingStaircase(sequence, numElements, widest2);
	if (widest2 > widest) {
		widest = widest2;
	}
	if (isStaircase) {
		cout << "YES " << widest << endl;
	}
	else {
		cout << "NO " << AscElems << " " << DescElems << endl;
	}
}



int main() {
	int numElements = 0;
	while (numElements != -1){
		int seq[1000];
		cin >> numElements;
		for (int i = 0; i < numElements; i++) {
			cin >> seq[i];
		}
		Staircase(seq, numElements);
	}

	return 0;
}