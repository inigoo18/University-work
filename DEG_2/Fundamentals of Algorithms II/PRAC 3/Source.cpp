#include <iostream>
#include <stack>
#include <string>
using namespace std;

int encode() {
	int result;
	stack<int> letters;
	string c;
	cin >> c;
	while (c != "end"){
			if (c == "+") {
				int a = letters.top();
				letters.pop();
				int b = letters.top();
				letters.pop();
				result = b + a;
				letters.push(result);
			}
			else if (c == "-") {
				int a = letters.top();
				letters.pop();
				int b = letters.top();
				letters.pop();
				result = b - a;
				letters.push(result);
			}
			else if (c == "*") {
				int a = letters.top();
				letters.pop();
				int b = letters.top();
				letters.pop();
				result = b * a;
				letters.push(result);
			}
			else {
				letters.push(stoi(c));
			}
		cin >> c;
	}
	int res = letters.top();
	letters.pop();
	// Reverse the last sequence
	return res;
}

int main()
{

	int numCases;
	cin >> numCases;
	for (int i = 0; i < numCases; i++) {
		int result = encode();
		cout << result << endl;
	}
}