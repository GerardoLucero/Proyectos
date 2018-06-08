#pragma once
#include "stdafx.h"
#include "Vertice.h"

class Bezier
{
public:
	const int N = 3;
	int M = 10;
	Bezier();
	~Bezier();
	Vertice* Control[3 + 1];//N=3
	float C[10 + 1];//N=3 
	Vertice* Curva[10 + 1];//M=4

	void calcular();
	void desplegar(byte*, int);
	float bez(int, float);
	void coeficiente();
	int fact(int);
	void trasladar(int, int);
};