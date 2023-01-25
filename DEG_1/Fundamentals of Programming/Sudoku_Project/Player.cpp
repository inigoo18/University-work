#include <iostream>
#include "Player.h"
using namespace std;
#include <fstream>


string toString(const tPlayer &player){
    string charString;
    charString = player.ID + "  " + to_string(player.playerScore);
    return charString;
}

void modifyPlayer(tPlayer &player, int score){
	cout << "Score of " << player.ID << " with " << player.playerScore << " and adding " << score << endl;
    player.playerScore += score;
}
/*Adds the score to the current player’s score.*/

bool operator<(const tPlayer &opLeft, const tPlayer &opRight){
    return opLeft.ID < opRight.ID;
}

bool smaller(const tPlayer &p1, const tPlayer &p2){
    bool isSmaller = false;
    if ((p1.playerScore <= p2.playerScore) || (p1.playerScore == p2.playerScore && p2 < p1)){
        isSmaller = true;
    }
    return isSmaller;
}
/*returns true if the player p1 has fewer points than player p2, or if they have the same points but
 p2’s identifier is smaller than player p1’s; and false otherwise.*/
