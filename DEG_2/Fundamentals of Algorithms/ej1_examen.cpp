#include <iostream>
using namespace std;

int ej1(int seq[], int n) {
	int numPares = 0;
	int maxPares = 0;
	for (int i = 0; i < n; i++) {
		if (seq[i] % 2 == 0) {
			numPares++;
			if (numPares > maxPares) {
				maxPares = numPares;
			}
		}
		else {
			if (numPares > maxPares) {
				maxPares = numPares;
			}
			numPares = 0;
		}
	}
	return maxPares;
}



int main() {

	int n;
	int seq[1000];
	cin >> n;
	while (n != -1) {
		for (int i = 0; i < n; i++) {
			cin >> seq[i];
		}
		cout << ej1(seq, n) << endl;
		cin >> n;
	}

	return 0;
}