// EDp7.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include "Bintree.h"
#include "List.h"
using namespace std;
void example1();
Bintree<int> readBintree();
void showBintree(Bintree<int> t);


int sum(Bintree<int> t, unsigned int d) {
	//if (t.depth() >= d) {
	//cout << t.depth() << endl;
	if (d > 0 && !t.empty()) {
		return sum(t.leftChild(), d - 1) + sum(t.rightChild(), d - 1);
	}
	else{
		if (t.empty()) {
			return 0;
		}
		else {
			return t.root() + sum(t.leftChild(), d) + sum(t.rightChild(), d);
		}
	}
	//else {
		//return 0;
	//}
}

/* Try this example by typing in the console the following tree:
(([1] 7 [3]) 4 [9])
*/
int main()
{
	int numCases;
	cin >> numCases;
	int d;
	for (int i = 0; i < numCases; i++) {
		Bintree<int> t = readBintree();
		cin >> d;
		cout << sum(t, d) << endl;
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

// Example of building and showing one tree
void example1() {
	Bintree<int> t1 = Bintree<int>(Bintree<int>(1), 7, Bintree<int>(3));
	Bintree<int> t = Bintree<int>(t1, 4, Bintree<int>(9));
	showBintree(t);
}
