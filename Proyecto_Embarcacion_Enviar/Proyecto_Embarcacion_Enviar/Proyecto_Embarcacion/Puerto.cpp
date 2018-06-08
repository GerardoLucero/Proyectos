#pragma once
#include "stdafx.h"
#include "Puerto.h"
#include <list>

Puerto::Puerto(Punto * Pi, Punto * Ps)
{
	this->pi = Pi;
	this->ps = Ps;
}

Puerto * Puerto::getSiguiente() {
	return this->sigPuerto;
}

void Puerto::setSiguiente(Puerto *p1) {
	this->sigPuerto = p1;
}

bool Puerto::seleccion(float x, float y)
{
	if (x >= pi->getX() && x <= ps->getX() & y >= pi->getY() & y <= ps->getY())
		return 1;
	else
		return 0;
}

Puerto::Puerto(float iX, float iY, float sX, float sY)
{
	pi = new Punto(iX, iY);
	ps = new Punto(sX, sY);
}

Puerto::~Puerto()
{
	delete pi;
	delete ps;
}

Punto * Puerto::getPi()
{
	return pi;
}

Punto * Puerto::getPs()
{
	return ps;
}
Ventana * Puerto::getVentana()
{
	return this->v;
}
Puerto * Puerto::copia()
{
	Puerto * copia = new Puerto(pi->getX(), pi->getY(), ps->getX(), ps->getY());
	return copia;
}
void Puerto::setVentana(Ventana * v)
{
	this->v = v;
}
void Puerto::desplegar(CClientDC& dc)
{
	Linea * l1 = new Linea(pi->getX(), pi->getY(), ps->getX(), pi->getY());

	Linea * l2 = new Linea(ps->getX(), pi->getY(), ps->getX(), ps->getY());

	Linea * l3 = new Linea(pi->getX(), ps->getY(), ps->getX(), ps->getY());

	Linea * l4 = new Linea(pi->getX(), pi->getY(), pi->getX(), ps->getY());

	l1->Desplegar(dc);
	l2->Desplegar(dc);
	l3->Desplegar(dc);
	l4->Desplegar(dc);

	delete l1;
	delete l2;
	delete l3;
	delete l4;
}

void Puerto::borrar(CClientDC& dc)
{
	Linea * l1 = new Linea(pi->getX(), pi->getY(), ps->getX(), pi->getY());

	Linea * l2 = new Linea(ps->getX(), pi->getY(), ps->getX(), ps->getY());

	Linea * l3 = new Linea(pi->getX(), ps->getY(), ps->getX(), ps->getY());

	Linea * l4 = new Linea(pi->getX(), pi->getY(), pi->getX(), ps->getY());

	l1->Borrar(dc, RGB(240, 240, 240));
	l2->Borrar(dc, RGB(240, 240, 240));
	l3->Borrar(dc, RGB(240, 240, 240));
	l4->Borrar(dc, RGB(240, 240, 240));

	delete l1;
	delete l2;
	delete l3;
	delete l4;
}
void Puerto::mover(float x, float y)
{
	pi->Trasladar(x, y);
	ps->Trasladar(x, y);
}

void Puerto::redimension(float ancho, float alto)
{
	pi->Trasladar(-ancho, -alto);
	ps->Trasladar(ancho, alto);
}

bool Puerto::Seleccion(Puerto * p, float x, float y)
{
	if (x > p->getPi()->getX() && x < p->getPs()->getX() && y > p->getPi()->getY() && y < p->getPs()->getY())
		return true;
	return 0;
}