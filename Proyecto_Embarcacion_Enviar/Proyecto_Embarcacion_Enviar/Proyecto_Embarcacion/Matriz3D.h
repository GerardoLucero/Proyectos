#pragma once

#include "stdafx.h"



class Matriz3D{
private:
	float datos[4][4];
public:
	Matriz3D();
	Matriz3D(float, float, float, float, float, float, float, float, float, float, float, float);
	void setDato(float, int, int);
	float getDato(int, int);
	Matriz3D * copiar();
	Matriz3D * multiplicar(Matriz3D *);
	Matriz3D * transformar(float, float, float);
};