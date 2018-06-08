#pragma once 
#include "stdafx.h"
#include "Punto.h"
#include "Ventana.h"

Ventana::Ventana(Punto *pi, Punto *ps)
{
	this->pi = pi;
	this->ps = ps;
}

Ventana::Ventana(float iX, float iY, float sX, float sY)
{
	this->pi = new Punto(iX, iY);
	this->ps = new Punto(sX, sY);
}

Ventana::~Ventana()
{
	delete pi;
	delete ps;
}

Punto * Ventana::getPi()
{
	return this->pi;
}

Punto * Ventana::getPs()
{
	return this->ps;
}

Ventana * Ventana::copia()
{
	Ventana *copia = new Ventana(pi->getX(), pi->getY(), ps->getX(), ps->getY());
	return copia;
}

void Ventana::mover(float x, float y)
{
	pi->Trasladar(x, y);
	ps->Trasladar(x, y);
}
void Ventana::redimension(float ancho, float alto)
{
	pi->Trasladar(-ancho, alto);
	ps->Trasladar(ancho, -ancho);
}
