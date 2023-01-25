#ifndef playerList_h
#define playerList_h

#include "Player.h"
#include <string>

const int MAXPlayers = 10;
const std::string playerFile = "playerList.txt";

typedef tPlayer tPlayerArray[MAXPlayers];

typedef struct{
    tPlayerArray array;
    int counter;
}tListPlayers;

void create(tListPlayers &list);
/*initializes list as an empty list.*/
bool load(tListPlayers &list);
/*loads into list the content of playerslist.txt, returning a bool indicating whether everything worked ok.*/
void display(const tListPlayers &list);
/*displays the player list on the screen.*/
bool save(const tListPlayers &list);
/*stores in playerslist.txt the content of list and returns a boolean value indicating the correctness of the action.
Must follow the file’s formatting rules.*/
void playerScore(tListPlayers &list, int score);
/*asks for a player ID and her position in list is updated. The update can modify the score (if the player
was on the list) or add the player to the list (with the score) if the list is not empty.*/
bool find(const tListPlayers &list, std::string id, int &pos);
/*locates the
player with the identifier id in the list; returns true and the position (pos) where the player is, or false and the position where the player should be if not on the list. Must be implemented with a binary search.*/
tListPlayers rankSort(const tListPlayers &list);
/*returns a copy of the list sorted by score (descending, and if score matches another, by ID in descending
order).*/
void bubbleSort(tListPlayers &list);

#endif
