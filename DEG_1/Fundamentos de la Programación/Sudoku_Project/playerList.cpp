#include <iostream>
using namespace std;
#include <fstream>
#include <iomanip>
#include "playerList.h"

void create(tListPlayers &list){
    for (int i = 0; i < MAXPlayers; i++){
        list.array[i].ID = "";
        list.array[i].playerScore = 0;
    }
}

bool load(tListPlayers &list) {
	bool isLoaded = false;
	ifstream SaveFile;
	SaveFile.open(playerFile);
	if (!SaveFile.is_open()) {
		cout << "ERROR - Couldn't load playerfile" << endl;
	}
	else {
		isLoaded = true;
		tPlayer testPlr;
		list.counter = 0;
		SaveFile >> testPlr.ID;
		while (testPlr.ID != "L") {
			SaveFile >> list.array[list.counter].playerScore;
			list.array[list.counter].ID = testPlr.ID;
			SaveFile >> testPlr.ID;
			list.counter++;
		}
		bubbleSort(list);
	}
	return isLoaded;
}
/*loads into list the content of playerslist.txt, returning a bool indicating whether everything worked ok.*/

void display(const tListPlayers &list){
	cout << endl << "List of players:" << endl;
    for (int i = 0; i < list.counter; i++){
        cout << i + 1 << ". - " << toString(list.array[i]) << endl;
    }
	cout << endl;
}
/*displays the player list on the screen.*/

bool save(const tListPlayers &list){
    bool isSaved = false;
    ofstream SaveFile;
    SaveFile.open(playerFile);
    if (!SaveFile.is_open()) {
        cout << "ERROR - couldn't open player file." << endl;
    } else {
		isSaved = true;
        for (int i = 0; i < list.counter; i++){ // setw's config is probably wrong, keep looking
            SaveFile << list.array[i].ID << setw(6) << right << list.array[i].playerScore << endl;
        }
		SaveFile << "L";
    }
    return isSaved;
}
/*stores in playerslist.txt the content of list and returns a boolean value indicating the correctness of the action.
Must follow the file’s formatting rules*/

void playerScore(tListPlayers &list, int score){
    tPlayer player;
    int pos = 0;
	bool canAdd = true;
    cout << "What's the player's ID?" << endl;
    cin >> player.ID;
	player.playerScore = 0;
    bool isFound = find(list, player.ID, pos);
    if (!isFound){
        if (list.counter < MAXPlayers){
            for (int arrayPos = list.counter - 1; arrayPos >= pos; arrayPos--){
                list.array[arrayPos+1] = list.array[arrayPos];
            }
            list.array[pos] = player;
			list.counter++;
        }
        else{
            cout << "List is full so can't add new tPlayer..." << endl;
			canAdd = false;
        }
    }
	if (canAdd) {
		modifyPlayer(list.array[pos], score);
	}
}
/*asks for a player ID and her position in list is updated. The update can modify the score (if the player
 was on the list) or add the player to the list (with the score) if the list is not empty.*/

bool find(const tListPlayers &list, string id, int &pos){
    bool isFound = false;
    int beg = 0, end = list.counter - 1, middle;
	while ((beg <= end) && !isFound) {
		middle = (beg + end) / 2; // Integer division
		int strCompared = id.compare(list.array[middle].ID);
		if (strCompared == 0) {
			isFound = true;
		}
		else if (strCompared < 0) {
			end = middle - 1;
		}
		else {
			beg = middle + 1;
		}
	}
	if (isFound) {
		pos = middle;
	}
    return isFound;
}
/*locates the player with the identifier id in the list; returns true and the position (pos) where
the player is, or false and the position where the player should be if not on the
list. Must be implemented with a binary search.*/

tListPlayers rankSort(const tListPlayers &list){
    tListPlayers playerList;
    playerList = list;
    for (int i = 0; i < list.counter; i++) {
        for (int j = list.counter - 1; j > i; j--) {
            if (playerList.array[j].playerScore < playerList.array[j - 1].playerScore) {
                tPlayer tmp;
                tmp = playerList.array[j];
                playerList.array[j] = playerList.array[j - 1];
                playerList.array[j - 1] = tmp;
            }
            else if (playerList.array[j].playerScore == playerList.array[j - 1].playerScore){
                if (playerList.array[j].ID < playerList.array[j - 1].ID){
                    tPlayer tmp;
                    tmp = playerList.array[j];
                    playerList.array[j] = playerList.array[j - 1];
                    playerList.array[j - 1] = tmp;
                }
            }
        }
    }
    return playerList;
}
/*returns a copy of the list sorted by score (descending, and if score matches another, by ID in descending order).*/

void bubbleSort(tListPlayers &list) {
	for (int i = 0; i < list.counter - 1; i++) {
		for (int j = list.counter - 1; j > i; j--) {
			if (list.array[j] < list.array[j - 1]) {
				tPlayer tmp;
				tmp = list.array[j];
				list.array[j] = list.array[j - 1];
				list.array[j - 1] = tmp;
			}
		}
	}
}