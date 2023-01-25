#include <iostream>
#include "Deck.h"
using namespace std;

Deck readDeck();

int main() {
	int numCases;
	cin >> numCases;
	for (int i = 0; i < numCases; i++) {
		Deck deck1 = Deck();
		Deck deck2 = Deck();
		deck1 = readDeck();
		deck2 = readDeck();
		cout << deck1.numCommonStickers(deck2) << endl;
	}

	cin >> numCases;
	return 0;
}


Deck readDeck() {
	Deck newDeck = Deck();
	char c;
	char ignore;
	cin >> c; // [
	string model = "";
	int num;
	while (c != ']' && model != "]") {
			cin >> model; // model
			if (model != "]") {
				cin >> ignore; // :
				cin >> num; // num
				newDeck.addStickers(std::stoi(model), num);
				cin >> c; //; or ]
			}
	}
	return newDeck;
}
