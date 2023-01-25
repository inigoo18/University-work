#ifndef Player_h
#define Player_h

#include "Player.h"
#include <string>

typedef struct{
    double playerScore;
    std::string ID;
} tPlayer;

std::string toString(const tPlayer &player);
/*returns the information for a player as a character string (first the identifier and then the score, separated by one or more whitespace characters).*/
void modifyPlayer(tPlayer & player, int score);
/*Adds the score to the current player’s score.*/
bool operator<(const tPlayer &opLeft, const tPlayer &opRight);
/*overloads the operator < for tPlayer data (compares by identifier).*/
bool smaller(const tPlayer &p1, const tPlayer &p2);
/*returns true if the player p1 has fewer points than player p2, or if they have the same points but
p2’s identifier is smaller than player p1’s; and false otherwise.*/

#endif /* Player_hpp */

