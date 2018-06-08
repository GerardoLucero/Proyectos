#include "stdafx.h"
#include "Bezier.h"
#include <math.h>
#include "Linea.h"

Bezier::Bezier()
{
	for (int i = 0; i <= N; i++)
		Control[i] = new Vertice();
	for (int j = 0; j <= M; j++)
		Curva[j] = new Vertice();
}

Bezier::~Bezier()
{
	delete Control;
	delete Curva;
}

void Bezier::trasladar(int dx, int dy)
{
	Control[1]->Trasladar(dx, dy);
	Control[2]->Trasladar(dx, dy);
	calcular();
}

void Bezier::calcular()
{
	coeficiente();
	float u, x, y, z;
	
	for (int i = 0; i <= M; i++)
	{
		u = (float)i / (float)M;
		x = 0;
		y = 0;
		z = 0;
		for (int j = 0; j <= N; j++)
		{

				x += Control[j]->getX()*bez(j, u);
				y += Control[j]->getY()*bez(j, u);
				z += Control[j]->getZ()*bez(j, u);
		}
		Curva[i]->setX(x);
		Curva[i]->setY(y);
		Curva[i]->setZ(z);
	}
}

void Bezier::desplegar(byte* datos, int ancho)
{
	for (int j = 0; j <  M ; j++)
	{
		Linea * l = new Linea();
		l->setP1(Curva[j]);
		l->setP2(Curva[j + 1]);
		l->Desplegar(datos, ancho);
	}
}

float Bezier::bez(int j, float u)
{ 
	float v = C[j] * pow(u, j)*pow(1 - u, N - j);
	return v;
}

void Bezier::coeficiente()
{
	int f = fact(N);
	for (int j = 0; j <= N; j++)
		C[j] = f / (fact(j)*fact(N - j));
}

int Bezier::fact(int facs)
{
	int resultado = 1;
	int factorial;
	factorial = facs;
	while (factorial > 1)
	{
		resultado = resultado*factorial;
		factorial = factorial - 1;
	}
	return resultado;
}