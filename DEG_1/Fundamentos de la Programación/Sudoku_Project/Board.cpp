#include <iostream>
#include <windows.h>
#include "Board.h"
#include "Set.h"
#include "Cell.h" 
using namespace std;
#include <fstream>
#include <cmath>
#include <string>
#include "Misc.h"


void setColor(int color) {
	HANDLE handle = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(handle, 15 | (color << 4));
}

void init(tBoard board) {
	for (int j = 0; j < n; j++) {
		for (int i = 0; i < n; i++) {
			board[i][j].state = Empty;
			fullSet(board[i][j].set);
		}
	}
}

bool load(const string &filename, tBoard board) {
	bool Successful = true;
	init(board);
	ifstream file;
	file.open(filename);
	if (!file.is_open()) {
		cout << "ERROR!" << endl;
		Successful = false;
	}
	else {
		char loadedNum;
		int numToPlace;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				file.get(loadedNum);
				numToPlace = charToInt(loadedNum);
				if (numToPlace > 0) {
					//board[i][j].state = fix;
					place(board, j, i, numToPlace);
					board[i][j].state = fix;
				}
            }
			file.get(loadedNum); // we discard the end line.
        }
    }
    return Successful;
}

void draw(const tBoard board){
	//string(25, '-');
    for (int j = 0; j < n; j++){
		cout << string(37, '-') << endl;
		cout << "| ";
        for (int i = 0; i < n; i++){
			drawCell(board[i][j]);
            cout << " | ";
        }
		cout << endl;
	}
	cout << string(37, '-') << endl;
}

bool place(tBoard board, int row, int col, int c){
    bool isPossible = false;
	/*cout << "-= " << c << " =-" << endl;
	cout << "isIn: " << isIn(board[col][row].set, c-1) << endl;
	cout << "state: " << (board[col][row].state == Empty) << endl;*/
    if (isIn(board[col][row].set, c-1) && board[col][row].state == Empty){
		//cout << "||" << c << "||" << endl;
        isPossible = true;
        board[col][row].number = c;
    }
    // possible(board, row, col);
    
    return isPossible;
}

bool erase(tBoard board, int row, int col){
    bool isPossible = false;
    if (board[col][row].state == filled){
        isPossible = true;
        board[col][row].state = Empty;
        board[col][row].number = 0;
    }
    return isPossible;
}

void updateBoard(tBoard &board) {
	for (int i = 0; i < MaxNums; i++) {
		for (int j = 0; j < MaxNums; j++) {
			fullSet(board[i][j].set);
		}
	}
	for (int i = 0; i < MaxNums; i++) {
		updateCols(board, i);
	}
	for (int j = 0; j < MaxNums; j++) {
		updateRows(board, j);
	}

	for (int i = 1; i < MaxNums; i = i + 3) {
		for (int j = 1; j < MaxNums; j = j + 3) { // 1, 4, 7 < 9. 10 isnt.
			updateRegion(board, j, i);
		}
	}
}

void updateRows(tBoard &board, int row) {
	for (int i = 0; i < MaxNums; i++) {
		if (board[i][row].state != fix) { // we only want cells that are filled or empty
			//fullSet(board[i][row].set); // we take the cell thats gonna be compared with the whole row and fullSet it.
			for (int j = 0; j < MaxNums; j++) {
				if (i != j && isIn(board[i][row].set, board[j][row].number - 1)) {
					board[i][row].set[board[j][row].number - 1] = false;
				}
			}
		}
		//system("pause");
	}
}

void updateCols(tBoard &board, int col) {
	for (int i = 0; i < MaxNums; i++) {
		if (board[col][i].state != fix) { // we only want cells that are filled or empty
			//fullSet(board[col][i].set); // we take the cell thats gonna be compared with the whole row and fullSet it.
			for (int j = 0; j < MaxNums; j++) {
				if (i != j && isIn(board[col][i].set, board[col][j].number - 1)) {
					//cout << "COMPARING: " << col << i << " WITH " << col << j << " WITH VALUE " << board[col][j].number << endl;
					board[col][i].set[board[col][j].number - 1] = false;
				}
			}
		}
	}
}

void updateRegion(tBoard &board, int row, int col) {
	//int SquareX, SquareY; //these two values are used in order to find the center of the square the cell is in. After finding it, we'll check all possible values in each cell in the square.
	//SquareX = 3 * floor(col / 3) + 1;
	//SquareY = 3 * floor(row / 3) + 1; // POSSIBLE VALUES: 1 or 4 or 7 (in a board from 0 to 8)
	for (int j = row - 1; j <= row + 1; j++) {
		for (int i = col - 1; i <= col + 1; i++) {
			//fullSet(board[i][j].set); // we full set the cell we're working on now.

			for (int y = row - 1; y <= row + 1; y++) { // in this part we want to compare that concrete cell to every other cell in the region.
				for (int x = col - 1; x <= col + 1; x++) {
					if ((i != x || j != y) && isIn(board[i][j].set, board[x][y].number - 1)) {
						//cout << "COMPARING: " << i << j << " WITH " << x << y << " WITH VALUE " << board[x][y].number << endl;
						board[i][j].set[board[x][y].number - 1] = false;
					}
				}
			}
		}
	}
}

bool full(const tBoard board){
    bool isFull = true;
    for (int j = 0; j < n; j++){
        for (int i = 0 ; i < n; i++){
            if (board[i][j].state == Empty){
                isFull = false;
            }
        }
    }
    return isFull;
}

void possible(const tBoard tablero, int row, int col){
    cout << "Possible values for i= " << col << " and j= " << row << endl;
    for (int i = 0; i < n; i++){
        if (isIn(tablero[col][row].set, i)) {
            cout << i + 1 << " ";
        }
    }
    cout << endl;
    
}

void fillSimple(tBoard board){
    int num = 0;
    for (int j = 0; j < n; j++){
        for (int i = 0 ; i < n; i++){
			//cout << "--BOARD: " << i << j << endl;
            if (isSimple(board[i][j], num) && board[i][j].state != fix){ // num changes with each loop so no need to reinitiate num with 0
				bool success = place(board, j, i, num + 1);
				if (success) {
					board[i][j].state = filled;
				}
            }
        }
    }
}
//for each cell that has only one possible value, the value is placed and all the cells whose valid values might change are updated.
