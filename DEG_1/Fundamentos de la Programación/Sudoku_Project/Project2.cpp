#include <iostream>
#include <fstream>
#include <string>
#include <cstdio>

//#include "Game.h"
#include "SudokuList.h"
#include "playerList.h"

using namespace std;
string PlayerList = "playerList.txt";
string SudokuList = "sudokuList.txt";

int MainMenu();

int main(){
	ifstream file;
	ifstream file2;
	file.open(PlayerList);
	file2.open(SudokuList);
	if (!file.is_open() && !file2.is_open()){
		cout << "ERROR - Could not open files." << endl;
	}
	else{
		int op = -1;
		while (op != 0) {
			op = MainMenu();
			if (op == 1){
                tSudoku sudoku;
                tSudokuList list; // we create the list
				int num; // position of the sudoku list
                createList(list); // we initialize the list
                load(list); // we load the list with the info from the text file
                display(list); // we show the user the different sudokus he can play
                cout << "What sudoku file will you choose?" << endl;
				cin >> num;
                int Score = playSudoku(list.array[num-1]); // RETURNS INT!
				tListPlayers playerList;
				create(playerList);
				bool successfulLoad, successfulSave;
				successfulLoad = load(playerList);
				if (successfulLoad) {
					playerScore(playerList, Score);
					successfulSave = save(playerList);
					if (!successfulSave) {
						cout << "Problem saving!" << endl;
					}
				}
				else {
					cout << "Couldn't properly load..." << endl;
				}
				//full()
				//modifyPlayer(tPlayer &player, int score)
			}
			else if (op == 2){
				tListPlayers playerList;
				create(playerList);
				bool successfulLoad;
				successfulLoad = load(playerList);
				if (successfulLoad) {
					display(playerList);
				}
				else {
					cout << "Couldn't properly load..." << endl;
				}
			}
			else if (op == 3) {
				tListPlayers playerList;
				create(playerList);
				bool successfulLoad;
				successfulLoad = load(playerList);
				if (successfulLoad) {
					playerList = rankSort(playerList);
					display(playerList);
				}
				else {
					cout << "Couldn't properly load..." << endl;
				}
			}
			else if (op == 4) {
				bool successReg;
				tSudokuList list;
				createList(list); // we initialize the list
				load(list); // we load the list with the info from the text file
				successReg = registerSudoku(list);
				save(list);
			}
		}
	}
	getchar();
	return 0;
}

int MainMenu() {
    int op = -1;
    while ((op < 0) || (op > 4)) {
        cout << "1 - Play" << endl;
        cout << "2 - See players, sorted by ID" << endl;
        cout << "3 - See players, sorted by score" << endl;
        cout << "4 - Add sudoku" << endl;
        cout << "0 - Exit" << endl;
        cout << "Your option: ";
        cin >> op;
        if ((op < 0) || (op > 4)) {
            cout << "Invalid option! Try again..." << endl;
        }
    }

    return op;
}
