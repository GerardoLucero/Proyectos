#pragma once
#include "stdafx.h"
#include "Punto.h"
#include <math.h>

class Vector {
private:
	float x, y, z;
	float a, b, c;
public:
	Vector();
	Vector(float, float, float);
	Vector(Punto *);
	Vector(Punto*, Punto*);
	float multiplicacion(Vector*);
	float magnitud();
	Vector* unitario();
	float getX();
	void setX(float);
	float getY();
	void setY(float);
	float getZ();
	void setZ(float);
};
