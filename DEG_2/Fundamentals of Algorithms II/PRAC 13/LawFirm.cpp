#include <iostream>

#include "LawFirm.h"

using namespace std;

// Linear cost O(1).
LawFirm::LawFirm() {
	firstPriorityNum = 0;
}

//If priority is either 0 or 2 it will only execute once, giving us O(1).
//However if the priority is 1, the worst case scenario would be to have the element at the end of
//the list, thus having O(n).
void LawFirm::addCase(string id, string name, string type, int priority) {
	tStruct.ID = id;
	tStruct.name = name;
	tStruct.type = type;
	tStruct.priority = priority;
	getCaseTable.insert(id, tStruct);
	if (priority == 0) {
		cases.push_front(tStruct);
		firstPriorityNum++;
	}
	else if (priority == 1) {
		List<law>::MutableIterator it = cases.begin();
		int a = 0;
		while (a != firstPriorityNum) {
			a++;
			it.next();
			
		}
		cases.insert(tStruct, it);
	}
	else if (priority == 2) {
		cases.push_back(tStruct);
	}
}


//O(1)
void LawFirm::getCase(string id, string &name, string &type) {
	if (getCaseTable.exists(id)) {
		name = getCaseTable.get(id).name;
		type = getCaseTable.get(id).type;
	}
	else {
		throw EWrongKey("This case does not exist");
	}
}

// No loops, so O(1).
void LawFirm::nextCase(string &id, int &priority) {
	if (cases.empty()) {
		throw EEmptyList("There are no cases");
	}
	else {
		id = cases.front().ID;
		priority = cases.front().priority;
	}
}

//No loops so O(1).
void LawFirm::removeNextCase() {
	if (!cases.empty()) {
		if (cases.front().priority == 0) {
			firstPriorityNum--;
		}
		getCaseTable.remove(cases.front().ID);
		cases.pop_front();
	}
}

// No loops so O(1).
bool LawFirm::empty() {
	if (cases.empty()) {
		return true;
	}
	return false;
}