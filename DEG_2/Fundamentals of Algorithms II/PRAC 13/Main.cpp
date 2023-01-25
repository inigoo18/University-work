#include <iostream>
#include "LawFirm.h"
using namespace std;

LawFirm law = LawFirm();


void readCommand() {
	string cmd;
	string id;
	string name;
	string type;
	int priority;
	cin >> cmd;
	if (cmd == "add") {
		cin >> id;
		cin >> name;
		cin >> type;
		cin >> priority;
		law.addCase(id, name, type, priority);
	}
	else if (cmd == "get") {
		cin >> id;
		try {
			law.getCase(id, name, type);
			cout << name << " " << type << endl;
		}
		catch (EWrongKey & e) {
			cout << e.msg() << endl;
		}
	}
	else if (cmd == "next") {
		try {
			law.nextCase(id, priority);
			cout << id << " " << priority << endl;
		}
		catch (EEmptyList & e) {
			cout << e.msg() << endl;
		}
	}
	else if (cmd == "remove") {
		law.removeNextCase();
	}
	else if (cmd == "empty") {
		bool res = law.empty();
		if (res) {
			cout << "yes" << endl;
		}
		else {
			cout << "no" << endl;
		}
	}
}


int main() {
	int numCases = 0;
	cin >> numCases;
	int operations = 0;
	for (int i = 0; i < numCases; i++) {
		law = LawFirm();
		cin >> operations;
		for (int j = 0; j < operations; j++) {
			readCommand();
		}
	}
	return 0;
}