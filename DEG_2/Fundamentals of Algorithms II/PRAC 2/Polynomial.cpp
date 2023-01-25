#include "Polynomial.h"
#include <math.h>
#include <cmath>
#include <algorithm>

Polynomial::Polynomial(int nums[], int grade) {
	degree = grade;
	for (int i = 0; i <= grade; i++) {
		numbers[i] = nums[i];
	}
}

int Polynomial::getDegree() {
	return degree;
}

int Polynomial::coef(int exponent) {
	if (exponent <= degree) {
		return numbers[exponent];
	}
	else {
		return 0;
	}
}

Polynomial Polynomial::operator +(Polynomial polynomial) {
	int maxDegree = std::max(degree, polynomial.getDegree());
	int polyNums[10];
	for (int i = 0; i <= maxDegree; i++) {
		polyNums[i] = coef(i) + polynomial.coef(i);
	}
	Polynomial polyResult = Polynomial(polyNums, maxDegree);
	return polyResult;
}

int Polynomial::evaluate(int x) {
	int result = 0;
	for (int i = 0; i <= degree; i++) {
		result += coef(i) * pow(x, i);
	}
	return result;
}
