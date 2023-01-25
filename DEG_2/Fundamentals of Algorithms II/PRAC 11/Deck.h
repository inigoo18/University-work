/*
 * Circle.h
 *
 *  Created on: 5 feb. 2020
 *      Author: usuario_local
 */

#ifndef DECK_H_
#define DECK_H_

#include "SearchTree.h";

class Deck {
public:
	Deck();
	void addStickers(int model, int num);
	int getNumStickers(int model);
	int numCommonStickers(Deck deck);
	SearchTree<int, int> deckParams;

private:
	
	int compare(int stickers1, int stickers2);

};

#endif



// DECK

//Models: 1000, 1001, 1002

//1000: 5
//1001: 7
//1002: 0

