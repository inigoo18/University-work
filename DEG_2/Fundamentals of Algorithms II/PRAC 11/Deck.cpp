#include "Deck.h"
#include <iostream>
#include <math.h>
#include <cmath>
#include <algorithm>


Deck::Deck() {
	deckParams = SearchTree<int, int>();
	addStickers(0, 0);
}

void Deck::addStickers(int stickerModel, int numStickers) {
	deckParams.insert(stickerModel, numStickers);
}

int Deck::getNumStickers(int stickerModel) {
	if (deckParams.exists(stickerModel)) {
		return deckParams.get(stickerModel);
	}
	return 0;
}

int Deck::numCommonStickers(Deck deck) {
	SearchTree<int, int>::Iterator it = deckParams.begin();
	int result = 0;
	while (it != deckParams.end()) {
		result += compare(deckParams.get(it.key()), deck.getNumStickers(it.key()));
		it.next();
		
	}
	return result; 
}

int Deck::compare(int stickers1, int stickers2) {
	return std::min(stickers1, stickers2);
}
