#ifndef Sudoku_h
#define Sudoku_h

#include "Game.h"

const int MAXSudokus = 20;
const std::string sudokuList_Name = "sudokuList.txt";

typedef tSudoku tSudokuArray[MAXSudokus];

typedef struct{
    tSudokuArray array;
    int counter;
}tSudokuList;

void createList(tSudokuList &list); //initializes list as an empty list.
bool load(tSudokuList &list); /*saves the content of the file sudokulist.txt
in list, returning a boolean value that indicates whether the load was successful.*/
void display(const tSudokuList &list); /*displays the list of sudokus on the
screen.*/

bool save(const tSudokuList &list);
/*stores the content of list in sudokulist.txt and returns a boolean value indicating whether the save process
was correct. It must follow the aforementioned formatting guidelines.*/
bool registerSudoku(tSudokuList &list);
/*asks for a new sudoku’s data (filename and score) and, if no other sudoku with that filename exists in list, this function inserts the sudoku in the appropriate position, respecting the order. A boolean indicating if the registration was successful must be returned. It has to be taken into account that the list could be full.*/
bool findFile(const tSudokuList &list, const std::string &filename);
/*returns a boolean indicating whether a sudoku with the filename already exists.*/
int findPosition(const tSudokuList &list, const tSudoku &sudoku);
/*returns the position in list where a new sudoku should be inserted.
Must be implemented as a binary search.*/

#endif /* Sudoku_h */
