#include <iostream>

using namespace std;
#include "Set.h"

void emptySet(tSet &set){
	for (int i = 0; i < MaxNums; i++){
		set[i] = false;
	}
}

void fullSet(tSet &set){
	for (int i = 0; i < MaxNums; i++){
		set[i] = true;
	}
}

bool isIn(const tSet &set, int n){
	bool Found = false;
	for (int i = 0; i < MaxNums; i++){
		if (set[i] && (i == n)){
			Found = true;
		}
	}
	return Found;
}

void add(tSet &set, int n){
    set[n] = true;
}

void erase(tSet &set, int n){
    set[n] = false;
}

int size(const tSet &set){
	int SetSize = 0;
	for (int i = 0; i < MaxNums; i++){
		if (set[i]){
			//cout << "True value: " << i << endl;
			SetSize++;
		}
	}
	return SetSize;
}

bool one(const tSet &set, int &n){
	bool Singleton = false;
	int SizeofSet = size(set);
	if (SizeofSet == 1){
        for (int i = 0; i < MaxNums; i++){ // we dont need bool written cause theres only one element > 0
			if (set[i]) {
				n = i;
			}
		}
		Singleton = true;
	}
	//cout << "Function one returning: " << n << " with bool " << Singleton << endl;
	return Singleton;
}

void display(const tSet &set){
	for (int i = 0; i < MaxNums; i++){
		cout << i << " - " << set[i] << endl;
	}
}
