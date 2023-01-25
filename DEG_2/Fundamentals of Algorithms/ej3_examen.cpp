#include <iostream>
using namespace std;

struct Pareja {
	int x;
	int y;
	int afinidad = 0;
};

typedef Pareja tEmparejamiento[10];

bool isValid(int p1, int p2, int v1, int v2, tEmparejamiento solucion, int k) {
	for (int i = 0; i < k + 1; i++) {
		if (k == 0 || p1 != solucion[i].x && p1 != solucion[i].y && p2 != solucion[i].x && p2 != solucion[i].y) {
			if (v1 > 0 && v2 > 0) {
				return true;
			}
		}
	}
	return false;
}

bool isSolution(int k, int parejas) {
	if (k + 1 == parejas) {
		return true;
	}
	return false;
}

void treatSolution(tEmparejamiento sol, int k) {
	int afi = 0;
	for (int i = 0; i < k + 1; i++) {
		afi += sol[i].afinidad;
	}
	cout << afi << endl;
}

void ej3(int array[10][10], int parejas, tEmparejamiento solucion, int k) {
	for (int i = 0; i < parejas; i++) {
		//cout << "Comprobando: " << i << " con " << k << endl;
		if (isValid(i, k, array[i][k], array[k][i], solucion, k)) {
			cout << "Es valido i: " << i << " con j: " << k << endl;
			solucion[k].x = i;
			solucion[k].y = k;
			solucion[k].afinidad = array[i][k];
			if (isSolution(k, parejas)) {
				treatSolution(solucion, k);
			}
			else {
				ej3(array, parejas, solucion, k + 1);
			}
		}
	}
}


int main() {
	tEmparejamiento solucion;
	int casos;
	int n;
	int array[10][10];
	cin >> casos;
	for (int i = 0; i < casos; i++) {
		cin >> n;
		for (int j = 0; j < n; j++) {
			for (int m = 0; m < n; m++) {
				cin >> array[j][m];
			}
		}
		cout << "Ejecucion" << endl;
		ej3(array, n, solucion, 0);
	}

	return 0;
}