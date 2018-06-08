#pragma once
#include "stdafx.h"
#include "Punto.h"

class Ventana
{
private:
	Punto * pi;
	Punto * ps;

public:
	Ventana(Punto *, Punto *);
	Ventana(float, float, float, float);

	~Ventana();

	Punto * getPi();
	Punto * getPs();

	Ventana * copia();
	void mover(float, float);
	void redimension(float, float);
};

