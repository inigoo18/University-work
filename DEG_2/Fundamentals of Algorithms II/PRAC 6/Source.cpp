#include <iostream>
using namespace std;

#include "Stack.h"


bool isEntry(char c) {
	if (c == '{' || c == '(' || c == '[') {
		return true;
	}
	return false;
}

bool isOpposite(char c, char fromStack) {
	if (fromStack == '{' && c == '}') {
		return true;
	}
	else if (fromStack == '(' && c == ')') {
		return true;
	}
	else if (fromStack == '[' && c == ']') {
		return true;
	}
	return false;
}

bool solveCase(string msg) {
	StackLL<char> brackets;
	int counter = 0;
	for (int i = 0; i < msg.length(); i++) {
		if (isEntry(msg[i])) {
			brackets.push(msg[i]);
		}
		else {
			if (brackets.numElems() > 0) {
				char lastChar = brackets.top();
				if (isOpposite(msg[i], lastChar)) {
					brackets.pop();
					counter++;
				}
			}
		}
	}
	if ((brackets.numElems() == 0) && (counter > 0)) {
		return true;
	}
	else {
		return false;
	}
}

int main() {
	int num;
	cin >> num;
	string msg;
	for (int i = 0; i < num; i++) {
		cin >> msg;
		if (solveCase(msg)) {
			cout << "yes" << endl;
		}
		else {
			cout << "no" << endl;
		}
	}

	return 0;
}



