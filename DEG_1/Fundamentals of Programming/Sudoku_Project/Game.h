
#ifndef Game_h
#define Game_h
#include "Board.h"

typedef struct{
    double score;
    std::string fileName;
}tSudoku;

typedef struct{
    tSudoku sudoku;
    tBoard board;
    bool fullBoard;
}tGame;

void initGame(tGame &game, const tSudoku &sudoku); /*takes sudoku, containing the information about the chosen sudoku, and initializes the parameter game as a non-finished game with the sudoku information and a board initialized with the previous module’s functions.*/
void displayGame(const tGame &game); //displays the sudoku’s information and the board on screen.
bool loadGame(tGame &game, const tSudoku &sudoku); /*updates the game board with the content of the file corresponding to the filename of sudoku.*/
int playSudoku(const tSudoku &sudoku); /*given the information of a sudoku, carries out all actions corresponding to choosing 1 from the menu and returns the information obtained (0 if the sudoku is quit before finishing, or the sudoku’s associated score if the game is solved).*/
int playMenu(); // shows menu for play mode.

#endif /* Game_h */
