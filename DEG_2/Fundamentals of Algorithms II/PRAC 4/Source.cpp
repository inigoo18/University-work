#include <iostream>
using namespace std;
#include "queue_linked_list.h"

int main() {
	int cases;
	cin >> cases;
	for (int i = 0; i < cases; i++) {
		Queue<int> queue;
		int numElems;
		cin >> numElems;
		for (int i = 0; i < numElems; i++) {
			int num;
			cin >> num;
			queue.push(num);
		}
		int penElem;
		cin >> penElem;
		queue.penalizeElem(penElem);
		for (int i = 0; i < numElems; i++) {
			int m;
			m = queue.front();
			queue.pop();
			cout << m << " ";
		}
		cout << endl;
	}
}