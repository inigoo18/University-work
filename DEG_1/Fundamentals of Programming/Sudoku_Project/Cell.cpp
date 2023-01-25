#include <iostream>

using namespace std;

#include "Cell.h"
#include "Misc.h"
#include "Set.h"
#include "Board.h"

void initCell(tCell &cell){
	cell.state = Empty;
	fullSet(cell.set);
}

void fillCell(tCell &cell, char c, bool Fixed){
    int num = charToInt(c);
    cell.number = num;
    if (!Fixed){
        cell.state = filled;
    }
    else{
        cell.state = fix;
    }
}

void drawCell(const tCell &cell){
    if (cell.state == fix){
        setColor(1);
    }
    else if (cell.state == filled){
        setColor(4);
    }
    else{
        setColor(0);
    }
	//cout << cell.number << endl;
	if (cell.number > 0) {
		cout << cell.number;
	}
	else {
		cout << " "; // we insert a space for negative numbers
	}
	setColor(0);
}

bool isSimple(const tCell &cell, int &number){
   // bool Simple = false;
	//cout << "isSimple returns size: " << size(cell.set) << endl;
   // if /*(size(cell.set) == 1 &&*/ (cell.state == Empty){
        bool Simple = one(cell.set, number);
   // }
    return Simple;
}
// primero miro el tamaño de set (que solo haya 1 valor posible) y si es 1, hago one.
