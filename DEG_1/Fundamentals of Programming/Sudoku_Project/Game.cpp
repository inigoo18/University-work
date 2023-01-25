#include <iostream>
#include "Game.h"
#include "SudokuList.h"
#include <string>

using namespace std;

void initGame(tGame &game, const tSudoku &sudoku){
    game.fullBoard = false;
    game.sudoku.score = sudoku.score;
    game.sudoku.fileName = sudoku.fileName;
    init(game.board);
} /*takes sudoku, containing the information about the chosen sudoku, and initializes the parameter game as a non-finished game with the sudoku information and a board initialized with the previous module’s functions.*/

void displayGame(const tGame &game){
    cout << "Game's score: " << game.sudoku.score << endl;
    cout << "Game's filename: " << game.sudoku.fileName << endl;
    cout << "Game's board: " << endl;
    draw(game.board);
}

bool loadGame(tGame &game, const tSudoku &sudoku){
    bool hasLoaded = load(sudoku.fileName,game.board);
    return hasLoaded;
} /*updates the game board with the content of the file corresponding to the filename of sudoku.*/

int playSudoku(const tSudoku &sudoku){
    int returnValue = 0;
    
    tGame game; // we create the game
    initGame(game, sudoku); // its initialized
    loadGame(game, sudoku); // gets loaded
	updateBoard(game.board);
	int menuOp = -1;
    while (menuOp != 0){
        if (menuOp == 1){
            int row, col;
            cout << "Row: ";
            cin >> row;
            cout << "Col: ";
            cin >> col;
            possible(game.board, row, col);
        }
        else if (menuOp == 2){
            int row, col, num;
			bool success;
            cout << "Row: ";
            cin >> row;
            cout << "Col: ";
            cin >> col;
            cout << "Num to place: ";
            cin >> num;
            cout << endl;
            success = place(game.board, row, col, num);
			if (success) {
				game.board[col][row].state = filled;
			}
			
        }
        else if (menuOp == 3){
            int row, col;
            cout << "Row: ";
            cin >> row;
            cout << "Col: ";
            cin >> col;
            bool possible = erase(game.board, row, col);
            if (!possible){
                cout << "The deletion wasn't possible! Fixed value in coordinates given." << endl;
            }
        }
        else if (menuOp == 4){
            for (int row = 0; row < n; row++){
                for (int col = 0; col < n; col++){
                    erase(game.board, row, col);
                }
            }
            game.sudoku.score = 0;
        }
        else if (menuOp == 5){
            fillSimple(game.board);
        }
		updateBoard(game.board);
		draw(game.board);
        menuOp = playMenu();
    }
	bool isFull = full(game.board);
	if (isFull) {
		returnValue = sudoku.score;
	}
    return returnValue;
    
} /*given the information of a sudoku, carries out all actions corresponding to choosing 1 from the menu and returns the information obtained (0 if the sudoku is quit before finishing, or the sudoku’s associated score if the game is solved).*/

int playMenu(){
    int op = -1;
    while ((op < 0) || (op > 5)) {
        cout << "1. Possible values for a cell" << endl;
        cout << "2. Put a value in a cell" << endl;
        cout << "3. Delete cell's value" << endl;
        cout << "4. Restart the board" << endl;
        cout << "5. Autofill simple cells" << endl;
        cout << "0. Go back to the main menu" << endl;
        cout << "Your option: ";
        cin >> op;
        if ((op < 0) || (op > 5)) {
            cout << "Invalid option! Try again..." << endl;
        }
    }
    return op;
}
