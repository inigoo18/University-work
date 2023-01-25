#include <iostream>
using namespace std;
#include "Circle.h"

int main(){
	int numCases = 0;
	cin >> numCases;
	for (int i = 0; i < numCases; i++){
		int x1, y1, x2, y2, x3, y3;
		cin >> x1 >> y1 >> x2 >> y2 >> x3 >> y3;
		Circle circle(x1, y1, x2, y2);
		cout << circle.calculateArea() << " ";
		bool result = circle.inCircle(x3, y3);
		if (result){
			cout << "yes";
		}
		else{
			cout << "no";
		}
		cout << endl;
	}
}
