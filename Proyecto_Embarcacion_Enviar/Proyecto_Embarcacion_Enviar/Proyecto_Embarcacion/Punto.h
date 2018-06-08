#pragma once
#include "stdafx.h"
#include "Matriz3D.h"


class Punto
{
private:
	float x;
	float y;
	float z;
	COLORREF color;

public:
	Punto();
	Punto(float, float);
	Punto(float, float, float);
	Punto(Punto *);

	void setX(float);
	float getX();
	void setY(float);
	float getY();
	void setZ(float);
	float getZ();

	void Borrar(byte *, int, int, int);
	

	void desplegar(byte *, int);
	void desplegar(byte *, int, int, int, int);

	void Borrar(byte *, int, int, int, int);

	void desplegar(CClientDC&);


	Punto * Copia();
	bool Seleccion(float, float);

	void Trasladar(float, float);

	void Trasformar(Matriz3D *);

	int getCodigo( Punto *, Punto *, Punto *);
	void Mapear(Punto *, Punto *, Punto *, Punto *, float, float);
};

