#pragma once
#include "stdafx.h"
#include "Punto.h"
#include "Foco.h"

Foco::Foco(Punto* pos, float I) {
	this->Pos = pos;
	this->I = I;
}

void Foco::setPos(Punto* Pos) {
	this->Pos = Pos;
}

Punto * Foco::getPos() {
	return Pos;
}

void Foco::setI(float I) {
	this->I = I;
}

float Foco::getI() {
	return I;
}

