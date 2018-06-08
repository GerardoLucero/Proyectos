

#pragma once
#include "stdafx.h"
#include "ColorRGB.h"
class Nodo
{
private:
	float IncX;
	float x;
	float Ys;
	Nodo *sig;
	ColorRGB * inicial;
	int incIr;
	int incIg;
	int incIb;

public:
	Nodo();
	Nodo(float, float, float);
	void setSig(Nodo*);
	Nodo * getSig();
	float getX();
	void setX(float);
	float getYS();
	void setYS(float);
	float getINCX();
	void setINCX(float);
	Nodo * Copiar();
	Nodo(float, float, float, Nodo*);

	int getIncIr();
	void setIncIr(int);
	int getIncIg();
	void setIncIg(int);
	int getIncIb();
	void setIncIb(int);

	ColorRGB * getInicial();
	void setInicial(ColorRGB *);
};