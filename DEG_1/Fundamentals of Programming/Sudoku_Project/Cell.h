#ifndef CELL_H_
#define CELL_H_

#include "Set.h"

typedef enum {Empty, fix, filled} tState;
typedef struct{
	tState state;
    int number;
	tSet set;
}tCell;

void initCell(tCell &cell);
void fillCell(tCell &cell, char c, bool fixed = false);
void drawCell(const tCell &cell);
bool isSimple(const tCell &cell, int &number);

#endif /* CELL_H_ */
