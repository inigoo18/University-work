#include <iostream>
using namespace std;
#include "Polynomial.h"

int main() {
	int numCases = 0;
	cin >> numCases;
	for (int i = 0; i < numCases; i++) {
		int degree, degree2;
		int nums[10];
		int nums2[10];
		int x;
		cin >> degree;
		for (int j = 0; j <= degree; j++) {
			cin >> nums[j];
		}
		cin >> degree2;
		for (int j = 0; j <= degree2; j++) {
			cin >> nums2[j];
		}
		Polynomial poly1(nums, degree), poly2(nums2, degree2);
		cin >> x;
		Polynomial polyResult = poly1 + poly2;
		cout << polyResult.evaluate(x) << endl;
	}
}