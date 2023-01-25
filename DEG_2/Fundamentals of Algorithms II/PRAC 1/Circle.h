/*
 * Circle.h
 *
 *  Created on: 5 feb. 2020
 *      Author: usuario_local
 */

#ifndef CIRCLE_H_
#define CIRCLE_H_

class Circle {
public:
	Circle(int x1, int y1, int x2, int y2);

	int calculateArea();
	bool inCircle(int x, int y);
private:
	const float pi = 3.1416;
	float middleX;
	float middleY;
	float radius;
	float radio();

	/* Discussion:
	 *
	 * To specify our private part, we're going to consider our representing types the middle coordinates along with the radius
	 * of the circle. We thought of having the two given points as the representing types at first, but we think that the former
	 * representation is much more efficient since it gives more information.
	 *
	 * Representing types: the middle coordinates and the radius
	 *
	 * Abstraction function: ¿?
	 *
	 * Equivalence function: when radius equals to 0, both middleX and middleY will be the same.
	 *
	 * Representation invariant: for the values to be considered valid, the radius has to be positive or equal to 0.
	 */
};

#endif /* CIRCLE_H_ */
