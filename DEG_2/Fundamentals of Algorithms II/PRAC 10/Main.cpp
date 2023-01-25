// SearchTree.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "SearchTree.h"
#include <string>
using namespace std;

void stringKeysExample() {
	SearchTree<string, int> st = SearchTree<string, int>();
	int numEvents;
	cin >> numEvents;
	for (int i = 0; i < numEvents; i++) {
		string name;
		int num;
		cin >> name >> num;
		if (st.exists(name)) {
			st.insert(name, st.get(name) + num);
		}
		else {
			st.insert(name, num);
		}
	}
	SearchTree<string, int>::Iterator it = st.begin();
	while (it != st.end()) {
		if (it.value() > 0) {
			cout << "(" << it.key() << ":" << it.value() << ")";

		}
		it.next();
	}
	cout << endl;
}


int main()
{
	int numCases;
	cin >> numCases;
	for (int i = 0; i < numCases; i++) {
		stringKeysExample();

	}

}

