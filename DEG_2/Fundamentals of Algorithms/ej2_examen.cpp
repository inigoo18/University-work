#include <iostream>
using namespace std;

bool ej2(int num, int seq[], int length) {
	int digit = num % 10;
	seq[length] = digit;
	length++;
	int prevSum = 0;
	int afterSum = 0;
	int backUpNum = num;
	cout << "Num is : " << num << endl;
	while (backUpNum > 0) {
		int num = backUpNum / 10;
		backUpNum = backUpNum / 10;
		int digit2 = num % 10;
		prevSum += digit2;
	}
	for (int i = 0; i < length; i++) {
		afterSum += seq[i];
	}

	if (num != 0) {
		ej2(num / 10, seq, length);
		return prevSum % digit == 0 && afterSum % digit == 0;
	}
	else {
		return true;
	}
}



int main() {

	int n;
	int num;
	int seq[10];
	cin >> n;
	for (int i = 0; i < n; i++) {
		cin >> num;
		cout << ej2(num, seq, 0) << endl;
	}

	return 0;
}