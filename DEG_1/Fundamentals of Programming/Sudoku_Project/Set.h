/*
 * Set.h
 *
 *  Created on: 27 mar. 2019
 *      Author: usuario_local
 */

#ifndef SET_H_
#define SET_H_

const int MaxNums = 9;
typedef bool tSet[MaxNums];

void emptySet(tSet &set);
void fullSet(tSet &set);
bool isIn(const tSet &set, int n);
void add(tSet &set, int n);
void erase(tSet &set, int n);
int size(const tSet &set);
bool one(const tSet &set, int &n);
void display(const tSet &set);



#endif /* SET_H_ */
