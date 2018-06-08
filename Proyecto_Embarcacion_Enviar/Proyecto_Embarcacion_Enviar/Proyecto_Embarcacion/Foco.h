#pragma once
#include "stdafx.h"
#include "Punto.h"

class Foco {
private:
	Punto * Pos;
	float I;
public:
	Foco(Punto*, float);
	void setPos(Punto*);
	Punto * getPos();
	void setI(float);
	float getI();
};