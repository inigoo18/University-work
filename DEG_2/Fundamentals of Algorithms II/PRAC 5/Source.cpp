#include <iostream>
using namespace std;

#include "List.h";

int main() {
	int people = 0;
	int fingers;
	while (people != -1) {
		List<int> list;
		cin >> people;
		if (people != -1) {
			cin >> fingers;
			for (int i = 1; i <= people; i++) {
				list.push_back(i);
			}
			list.sortOut(fingers);
		}
	}
	return 0;
}