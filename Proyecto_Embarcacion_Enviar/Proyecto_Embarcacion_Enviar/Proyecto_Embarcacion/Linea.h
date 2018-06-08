#pragma once
#include "stdafx.h"
#include "Punto.h"
#include "Matriz3D.h"
#include "Ventana.h"
#include "Puerto.h"

class Linea
{

private:
	Punto *p1;
	Punto *p2;
	COLORREF color;
	Linea * Sig;

public:
	Linea();
	Linea(Punto *, Punto *);
	Linea(float, float, float, float);
	Linea(float, float, float, float, float, float);
	~Linea();
	Punto *getP1();
	void setP1(Punto *);
	Punto *getP2();
	void setP2(Punto *);
	Linea *Copia();
	void Desplegar(CClientDC&);
	void Desplegar(byte *, int);

	void Borrar(byte *, int, int , int, int);
	Linea * getSig();
	void setSig(Linea *);
	int Seleccion(Punto *);
	void Borrar(CClientDC&, COLORREF);

	void Desplegar(CClientDC&, COLORREF);
	void Rotar(float, float, float);

	void Trasladar(float, float);

	void Escalar(float, float, float, float);

	void Trasformar(Matriz3D *);

	Linea * Recorte(Ventana *);

};