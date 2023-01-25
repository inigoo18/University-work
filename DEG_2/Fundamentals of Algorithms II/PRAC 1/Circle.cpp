
#include "Circle.h"
#include <math.h>
#include <cmath>

int PAx, PAy, PBx, PBy;

Circle::Circle(int x1, int y1, int x2, int y2) {
	PAx = x1;
	PAy = y1;
	PBx = x2;
	PBy = y2;
	middleX = (PAx + PBx) / 2;
	middleY = (PAy + PBy) / 2;
	radius = radio();
}

int Circle::calculateArea(){
	return floor(pi * pow(radius,2));
}

bool Circle::inCircle(int x, int y){
	if ((pow(x - middleX, 2) + pow(y - middleY, 2)) < pow(radius, 2)){
		return true;
	}
	return false;
}

float Circle::radio(){
	float dist = sqrt(pow(PBx - PAx, 2) + pow(PBy - PAy, 2));
	return dist/2;
}

