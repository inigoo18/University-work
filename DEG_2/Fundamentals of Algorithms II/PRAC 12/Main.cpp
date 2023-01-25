#include <iostream>
#include <string>
#include "Table.h"

using namespace std;

Table<string, Table<string,int>> t;
//Table<string, int> s;



void deposit(string p, int m, string a) {
	if (!t.exists(p)) {
		Table<string, int> s = Table<string, int>();
		t.insert(p, s);
	}
	Table<string, int> s = t.get(p);
	if (!s.exists(a)) {
		s.insert(a, m);
	}
	else {
		s.insert(a, s.get(a) + m);
	}
	t.insert(p, s);
}

void transfer(string p1, string a1, string p2, string a2, int m) {
	if (!t.exists(p2)) {
		Table<string, int> s = Table<string, int>();
		t.insert(p2, s);
	}
	if (t.exists(p1)) {
		Table <string, int> s = t.get(p1);
		if (s.get(a1) >= m) {
			s.insert(a1, s.get(a1) - m);
			Table <string, int> v = t.get(p2);
			if (!v.exists(a2)) {
				v.insert(a2, m);
			}
			else {
				v.insert(a2, v.get(a2) + m);
			}
			t.insert(p2, v);
		}
		t.insert(p1, s);
	}

}

void money(string p) {
	Table<string, int> s = t.get(p);
	Table<string, int>::Iterator it = s.begin();
	int result = 0;
	while (it != s.end()) {
		result += it.value();
		it.next();
	}
	cout << result << endl;
}

void readCommand() {
	char cmd;
	string p1, p2;
	string a1, a2;
	int m;
	cin >> cmd;
	if (cmd == 'd') {
		cin >> p1;
		cin >> a1;
		cin >> m;
		//cout << p1 << a1 << m;
		deposit(p1, m, a1);
	}
	else if (cmd == 't') {
		cin >> p1;
		cin >> a1;
		cin >> p2;
		cin >> a2;
		cin >> m;
		transfer(p1, a1, p2, a2, m);
	}
	else if (cmd == 'm') {
		cin >> p1;
		money(p1);
	}
}

int main() {
	//t = Table<string, Table<string, int>>();
	int numCases = 0;
	cin >> numCases;
	int operations = 0;
	for (int i = 0; i < numCases; i++) {
		t = Table<string, Table<string, int>>();
		cin >> operations;
		for (int j = 0; j < operations; j++) {
			readCommand();
		}
	}
	//s = Table<string, int>();
	return 0;
}