// Iñigo Sanz and Berta De Pablo.
// This algorithm has the order of complexity O(n) because if we assume it contains n different nodes,
// then the recursive method will be also executed n times.


#include <iostream>
#include "Bintree.h"
#include "List.h"
using namespace std;
Bintree<int> readBintree();
void showBintree(Bintree<int> t);
int isSpecial(Bintree<int> t, int topNum, int bottomNum);


int special(Bintree<int> t, int topNum, int &result) {
	int bottomNum = 1;
	if (t.empty()) {
		return 1;
	}
	else {
		bottomNum = bottomNum * special(t.leftChild(), topNum * t.root(), result);
		bottomNum = bottomNum * special(t.rightChild(), topNum * t.root(), result);
		result += isSpecial(t, topNum, bottomNum);
	}
	return bottomNum * t.root();
}

int isSpecial(Bintree<int> t, int topNum, int bottomNum) {
	if (t.empty()) {
		return 0;
	}
	if (!t.empty() && topNum == bottomNum) {
		return 1;
	}
	return 0;
}

int main()
{
	int numCases;
	cin >> numCases;
	for (int i = 0; i < numCases; i++) {
		Bintree<int> t = readBintree();
		int result = 0;
		special(t, 1, result);
		cout << result << endl;
	}

}

// It reads a binary tree of integers from the standard input
Bintree<int> readBintree() {
	char c;
	cin >> c;
	switch (c) {
	case '#': return Bintree<int>();
	case '[': {
		int raiz;
		cin >> raiz;
		cin >> c;
		return Bintree<int>(raiz);
	}
	case '(': {
		Bintree<int> left = readBintree();
		int root;
		cin >> root;
		Bintree<int> right = readBintree();
		cin >> c;
		return Bintree<int>(left, root, right);
	}
	default:
		return Bintree<int>();
	}
}

// It shows one tree using in-order tour
void showBintree(Bintree<int> t) {
	List<int> l = t.inorder();
	List<int>::Iterator it = l.cbegin();
	while (it != l.cend()) {
		cout << it.elem() << " ";
		it.next();
	}
}
