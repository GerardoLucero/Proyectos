#pragma once
#include "stdafx.h"
#include "Punto.h"
#include "Ventana.h"
#include "Linea.h"
#include <list>
class Puerto
{
private:
	Punto * pi;
	Punto * ps;
	Ventana * v;
	Puerto * sigPuerto;

public:
	Puerto(Punto *, Punto *);
	Puerto(float, float, float, float);

	~Puerto();

	Punto * getPi();
	Punto * getPs();

	void setVentana(Ventana *);
	bool seleccion(float, float);
	void setSiguiente(Puerto *);
	Ventana * getVentana();

	Puerto * copia();

	void desplegar(CClientDC&);

	void borrar(CClientDC&);

	Puerto *getSiguiente();

	void mover(float, float);
	void redimension(float, float);

	bool Seleccion(Puerto *, float, float);
};