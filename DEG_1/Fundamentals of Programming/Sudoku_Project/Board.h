#ifndef Board_h
#define Board_h

#include "Cell.h"
#include "Set.h"
const int n = 9;


/*typedef struct{
    tCell cell;
    int counter;
}tBoard;*/
typedef tCell tBoard[n][n];

void init(tBoard board);
bool load(const std::string &filename, tBoard board);
void draw(const tBoard board);
bool place(tBoard board, int row, int col, int c);
bool erase(tBoard board, int row, int col);
bool full(const tBoard board);
void possible(const tBoard tablero, int row, int col);
void fillSimple(tBoard board);
void setColor(int color);

void updateBoard(tBoard &tablero); // changes possible values of all affected cells around the chosen one.
void updateRows(tBoard &board, int row);
void updateCols(tBoard &board, int col);
void updateRegion(tBoard &board, int row, int col);
#endif /* Board_h */
