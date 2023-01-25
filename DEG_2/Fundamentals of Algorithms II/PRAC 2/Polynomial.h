#ifndef POLYNOMIAL_H_
#define POLYNOMIAL_H_

class Polynomial {
public:
	Polynomial(int numbers[], int grade);
	int evaluate(int x);
	int numbers[10];
	Polynomial operator +(Polynomial poly);
	int coef(int exponent);
private:
	int getDegree();
	int degree;
};


#endif