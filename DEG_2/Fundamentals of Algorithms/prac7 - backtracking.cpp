#include <iostream>
using namespace std;

const int N = 100;
typedef int tMap[N][N];
typedef bool tUsed[N][N];

struct Cell {
	int x;
	int y;
	char dir;
};

typedef Cell tSolution[N*N];

bool isValid(tMap map, Cell cell, int size, tUsed usedMap) {
	bool valid = false;
	if (map[cell.x][cell.y] % 2 == 1 && !usedMap[cell.x][cell.y] && cell.x >= 0 && cell.x < size && cell.y >= 0 && cell.y < size) {
		valid = true;
	}
	return valid;
}

void initBool(tUsed &boolMap, int size) {
	for (int i = 0; i < size; i++) {
		for (int j = 0; j < size; j++) {
			boolMap[i][j] = false;
		}
	}
}

Cell nextDirection(int dir, Cell &pos) {
	Cell newCell = pos;
	if (dir == 0) {
		newCell.x++;
		pos.dir = 'd';
	}
	else if (dir == 1) {
		newCell.y++;
		pos.dir = 'r';
	}
	else if (dir == 2) {
		newCell.x--;
		pos.dir = 'u';
	}
	else if (dir == 3) {
		newCell.y--;
		pos.dir = 'l';
	}
	return newCell;
}

bool isSolution(Cell pos, int n) {
	return pos.y == n - 1 && pos.x == n - 1;
}

void treatSolution(Cell solution[], int n) {
	if (n == N * N) {
		cout << "IMPOSSIBLE";
	}
	else {
		for (int i = 0; i < n; i++) {
			cout << solution[i].dir << " ";
		}
	}
	cout << endl;
}

void copySolution(tSolution solution, tSolution &bestSolution) {
	for (int i = 0; i < N*N; i++) {
		bestSolution[i].dir = solution[i].dir;
		bestSolution[i].x = solution[i].x;
		bestSolution[i].y = solution[i].y;
	}

}


void shortestPathOddNums(tMap map, tUsed usedMap, int k, int size, tSolution solution, tSolution &bestSolution, int &bestCost, int cost) {
	for (int dir = 0; dir < 4; dir++) {
		solution[k] = nextDirection(dir, solution[k - 1]);
		cost++;
		if (isValid(map, solution[k], size, usedMap)) {
			if (isSolution(solution[k], size)) {
				if (cost < bestCost) {
					bestCost = cost;
					copySolution(solution, bestSolution);
				}
			}
			else {
				usedMap[solution[k].x][solution[k].y] = true;
				shortestPathOddNums(map, usedMap, k + 1, size, solution, bestSolution, bestCost, cost);
			}
		}
		cost--;
	}
}

int main() {
	int size;
	cin >> size;
	while (size != -1) {
		tMap map;
		tUsed usedMap;
		tSolution solution;
		tSolution bestSolution;
		int k = 1;
		int bestCost = N * N;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cin >> map[i][j];
			}
		}
		initBool(usedMap, size);
		solution[0].x = 0;
		solution[0].y = 0;
		usedMap[0][0] = true;
		shortestPathOddNums(map, usedMap, k, size, solution, bestSolution, bestCost, 0);
		treatSolution(bestSolution, bestCost);
		cin >> size;
	}
	return 0;
}
