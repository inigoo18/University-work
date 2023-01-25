#include <iostream>
#include "Game.h"
#include "SudokuList.h"
using namespace std;
#include <fstream>
#include <string>

void createList(tSudokuList &list) {
	for (int i = 0; i < MAXSudokus; i++) {
		list.array[i].fileName = "";
		list.array[i].score = 0;
	}
} //initializes list as an empty list.

bool load(tSudokuList &list) {
	bool isLoaded = true;
	string Test;
	list.counter = 0;
	ifstream SaveFile;
	SaveFile.open(sudokuList_Name);
	if (!SaveFile.is_open()) {
		cout << "ERROR - couldn't open sudoku list" << endl;
		isLoaded = false;
	}
	else {
		SaveFile >> Test;
		while (Test != "L") {
			list.array[list.counter].fileName = Test;
			SaveFile >> list.array[list.counter].score;
			list.counter++;
			SaveFile >> Test;
		}
	}
	return isLoaded;
} /*saves the content of the file sudokulist.txt in list, returning a boolean value that indicates whether the load was successful.*/

void display(const tSudokuList &list) {
	cout << "List of sudokus:" << endl;
	for (int i = 0; i < list.counter; i++) {
		cout << i+1 << " - " << list.array[i].fileName << " - " << list.array[i].score << endl;
	}
	cout << endl;
} /*displays the list of sudokus on the screen.*/


// NEW CODE FROM HERE.

bool save(const tSudokuList &list){
    bool isSaved = false;
    ofstream SaveFile;
    SaveFile.open(sudokuList_Name);
    if (!SaveFile.is_open()) {
        cout << "ERROR." << endl;
    } else {
        isSaved = true;
        for (int i = 0; i < list.counter; i++){
            SaveFile << list.array[i].fileName << " " << list.array[i].score << endl;
        }
    }
	SaveFile << "L";
    return isSaved;
}
/*stores the content of list in sudokulist.txt and returns a boolean value indicating whether the save process
 was correct. It must follow the aforementioned formatting guidelines.*/

bool registerSudoku(tSudokuList &list){
    tSudoku sudoku;
    bool success = true;
    cout << "Insert new sudoku's name: ";
    cin >> sudoku.fileName;
    cout << "Insert sudoku's score: ";
    cin >> sudoku.score;
    cout << endl;
    success = findFile(list, sudoku.fileName);
    if (list.counter == MAXSudokus){
        success = false;
		cout << "The maximum number of sudokus has been reached!" << endl;
    }
    if (success){
        list.counter++;
        int pos = findPosition(list, sudoku);
        for (int arrayPos = list.counter - 1; arrayPos >= pos; arrayPos--){
            list.array[arrayPos+1] = list.array[arrayPos];
        }
        list.array[pos] = sudoku;
    }
	else {
		cout << "Couldn't load... check code!" << endl;
	}
    return success;
}
/*asks for a new sudoku’s data (filename and score) and, if no other sudoku with that filename exists in list, this function inserts the sudoku in the appropriate position, respecting the order. A boolean indicating if the registration was successful must be returned. It has to be taken into account that the list could be full.*/

bool findFile(const tSudokuList &list, const string &filename){
    bool found = true;
    for (int i = 0; i < list.counter; i++){
        if (filename == list.array[i].fileName){
            found = false;
        }
    }
    return found;
}
/*returns a boolean indicating whether a sudoku with the filename already exists.*/

int findPosition(const tSudokuList &list, const tSudoku &sudoku){
    int pos = -1, beg = 0, end = list.counter - 1, middle;
    //bool found = false;
    while ((beg <= end)) { // do we need found bool???
        middle = (beg + end) / 2; // Integer division
		int strCompared = sudoku.fileName.compare(list.array[middle].fileName);
        if (strCompared > 0) {
			beg = middle + 1;
        }
        else if (strCompared < 0) {
            end = middle - 1;
        }
    }
    return middle;
}
/*returns the position in list where a new sudoku should be inserted.
Must be implemented as a binary search.*/
