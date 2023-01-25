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
int sumaDeMierda(int num) {
	int decimas = num / 10;
	int unidades = num % 10;
	int result = 0;
	if (decimas > 0) {
		result += sumaDeMierda((decimas - 1) * 10 + unidades);
		if (decimas >= 10) {
			decimas = decimas % 9;
		}
		return result + 45 + decimas;
	}
	else if (unidades > 0) {
		result += sumaDeMierda((decimas) * 10 + unidades - 1);
		return result + unidades;
	}
	else {
		return 0;
	}

}


int main() {
	int num = 1;
	while (num != 0) {
		cin >> num;
		cout << sumaDeMierda(num) << endl;
	}
	return 0;
} // main