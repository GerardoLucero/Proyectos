#pragma once
#include "stdafx.h"
#include "Matriz3D.h"
#include "Punto.h"
#include "Foco.h"
#include "Vector.h"
#include "ColorRGB.h"

class Vertice : public Punto {
private:
	Vertice * siguiente;
	ColorRGB * I;
	Vector* vProm;
public:
	Vertice();
	Vertice(float, float, float);
	Vertice * Copia();
	Vertice * getSig();
	void setSig(Vertice *);
	void perspectiva(float, float);

	void calcularIr(float Ia, ColorRGB * K, Foco* f, Punto* cent, Vector* prom);
	void setI(ColorRGB *);
	ColorRGB * getI();
	Vector * getVpromedio();
	void setVpromedio(Vector*);
};
