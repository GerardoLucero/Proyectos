#pragma once 
#include "stdafx.h"
#include "Linea.h"
#include <algorithm>
#include "Ventana.h"
#include "Puerto.h"
#include "Matriz3D.h"

Linea::Linea()
{
	this->p1 = new Punto();
	this->p2 = new Punto();
	this->color = RGB(0, 0, 0);
}

Linea::Linea(Punto *P1, Punto *P2)
{
	this->p1 = P1;
	this->p2 = P2;
}

Linea::Linea(float x1, float y1, float x2, float y2)
{
	this->p1 = new Punto(x1, y1);
	this->p2 = new Punto(x2, y2);
}

Linea::Linea(float x1, float y1, float z1, float x2, float y2, float z2)
{
	this->p1 = new Punto(x1, y1, z1);
	this->p2 = new Punto(x2, y2, z2);
}

Linea::~Linea()
{
	delete p1;
	delete p2;
}

Punto *Linea::getP1()
{
	return this->p1;
}

void Linea::Borrar(byte * b, int ancho, int R, int G, int B)
{
	int x1 = this->p1->getX();
	int y1 = this->p1->getY();
	int x2 = this->p2->getX();
	int y2 = this->p2->getY();
	int x, y, p, inc1, inc2, avanceX, avanceY;
	int dx = (x2 - x1);
	int dy = (y2 - y1);
	Punto *np = new Punto(0, 0);

	if (dy < 0) {
		dy = -dy;
		avanceY = -1;
	}
	else {
		avanceY = 1;
	}

	if (dx < 0) {
		dx = -dx;
		avanceX = -1;
	}
	else {
		avanceX = 1;
	}
	x = x1;
	y = y1;

	if (dx>dy){
		p = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);
		while (x != x2){
			x += avanceX;
			if (p < 0){
				p += inc1;
			}
			else {
				y += avanceY;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->Borrar(b, ancho, R, G, B);
		}
	}
	else{
		p = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);
		while (y != y2){
			y = y + avanceY;
			if (p < 0){
				p += inc1;
			}
			else {
				x += avanceX;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->Borrar(b, ancho, R, G, B);
		}
	}
	delete np;
}
void Linea::Desplegar(byte * b, int ancho) {
	int x1 = this->p1->getX();
	int y1 = this->p1->getY();
	int x2 = this->p2->getX();
	int y2 = this->p2->getY();
	int x, y, p, inc1, inc2, avanceX, avanceY;
	int dx = (x2 - x1);
	int dy = (y2 - y1);
	Punto *np = new Punto(0, 0);

	if (dy < 0) {
		dy = -dy;
		avanceY = -1;
	}
	else {
		avanceY = 1;
	}

	if (dx < 0) {
		dx = -dx;
		avanceX = -1;
	}
	else {
		avanceX = 1;
	}
	x = x1;
	y = y1;

	if (dx>dy){
		p = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);
		while (x != x2){
			x += avanceX;
			if (p < 0){
				p += inc1;
			}
			else {
				y += avanceY;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(b, ancho);
		}
	}
	else{
		p = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);
		while (y != y2){
			y = y + avanceY;
			if (p < 0){
				p += inc1;
			}
			else {
				x += avanceX;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(b, ancho);
		}
	}
	delete np;
}

void Linea::setP1(Punto *p)
{
	p1->setX(p->getX());
	p1->setY(p->getY());
}

Punto *Linea::getP2()
{
	return this->p2;
}

void Linea::setP2(Punto *p)
{
	p2->setX(p->getX());
	p2->setY(p->getY());
}

Linea * Linea::Copia()
{
	Punto *Pn1 = new Punto(p1->Copia());
	Punto *Pn2 = new Punto(p2->Copia());
	Linea * L = new Linea(Pn1, Pn2);
	return L;
}

Linea * Linea::getSig()
{
	return this->Sig;
}

void Linea::setSig(Linea * L)
{
	this->Sig = L;
}

int Linea::Seleccion(Punto * p)
{
	if (p1->Seleccion(p->getX(), p->getY()) || p2->Seleccion(p->getX(), p->getY()))
		return 1;
	else
		return 0;
}

void Linea::Desplegar(CClientDC& dc)
{
	int x1 = this->p1->getX();
	int y1 = this->p1->getY();
	int x2 = this->p2->getX();
	int y2 = this->p2->getY();
	int x, y, p, inc1, inc2, avanceX, avanceY;
	int dx = (x2 - x1);
	int dy = (y2 - y1);
	Punto *np = new Punto(0, 0, RGB(0,0,0));
	if (dy < 0) {
		dy = -dy;
		avanceY = -1;
	}
	else {
		avanceY = 1;
	}

	if (dx < 0) {
		dx = -dx;
		avanceX = -1;
	}
	else {
		avanceX = 1;
	}
	x = x1;
	y = y1;

	if (dx>dy){
		p = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);
		while (x != x2){
			x += avanceX;
			if (p < 0){
				p += inc1;
			}
			else {
				y += avanceY;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	else{
		p = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);
		while (y != y2){
			y = y + avanceY;
			if (p < 0){
				p += inc1;
			}
			else {
				x += avanceX;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	delete np;

}
void Linea::Desplegar(CClientDC& dc, COLORREF C)
{
	int x1 = this->p1->getX();
	int y1 = this->p1->getY();
	int x2 = this->p2->getX();
	int y2 = this->p2->getY();
	int x, y, p, inc1, inc2, avanceX, avanceY;
	int dx = (x2 - x1);
	int dy = (y2 - y1);
	Punto *np = new Punto(0, 0, C);

	if (dy < 0) {
		dy = -dy;
		avanceY = -1;
	}
	else {
		avanceY = 1;
	}

	if (dx < 0) {
		dx = -dx;
		avanceX = -1;
	}
	else {
		avanceX = 1;
	}

	x = x1;
	y = y1;

	if (dx>dy){
		p = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);
		while (x != x2){
			x += avanceX;
			if (p < 0){
				p += inc1;
			}
			else {
				y += avanceY;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	else{
		p = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);
		while (y != y2){
			y = y + avanceY;
			if (p < 0){
				p += inc1;
			}
			else {
				x += avanceX;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	delete np;

}
void Linea::Borrar(CClientDC& dc, COLORREF C)
{
	int x1 = this->p1->getX();
	int y1 = this->p1->getY();
	int x2 = this->p2->getX();
	int y2 = this->p2->getY();
	int x, y, p, inc1, inc2, avanceX, avanceY;
	int dx = (x2 - x1);
	int dy = (y2 - y1);
	Punto *np = new Punto(0, 0, C);

	if (dy < 0) {
		dy = -dy;
		avanceY = -1;
	}
	else {
		avanceY = 1;
	}

	if (dx < 0) {
		dx = -dx;
		avanceX = -1;
	}
	else {
		avanceX = 1;
	}

	x = x1;
	y = y1;

	if (dx>dy){
		p = 2 * dy - dx;
		inc1 = 2 * dy;
		inc2 = 2 * (dy - dx);
		while (x != x2){
			x += avanceX;
			if (p < 0){
				p += inc1;
			}
			else {
				y += avanceY;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	else{
		p = 2 * dx - dy;
		inc1 = 2 * dx;
		inc2 = 2 * (dx - dy);
		while (y != y2){
			y = y + avanceY;
			if (p < 0){
				p += inc1;
			}
			else {
				x += avanceX;
				p += inc2;
			}
			np->setX(x);
			np->setY(y);
			np->desplegar(dc);
		}
	}
	delete np;

}


void Linea::Trasladar(float tx, float ty)
{
	p1->Trasladar(tx, ty);
	p2->Trasladar(tx, ty);
}


void Linea::Trasformar(Matriz3D * M)
{
	p1->Trasformar(M);
	p2->Trasformar(M);
}

Linea * Linea::Recorte(Ventana * v)
{
	Linea * l = this->Copia();

	int c1, c2, c1a = 0, c2a = 0, c1b = 0, c2b = 0;

	float val;
	while (1)
	{
		c1 = l->getP1()->getCodigo(l->getP1(), v->getPi(), v->getPs());
		c2 = l->getP2()->getCodigo(l->getP2(), v->getPi(), v->getPs());
		if (c1 + c2 == 0)
			return l;
		if (c1b == c1 && c2b == c2 || c1 == c2 || c1 == 8 && (c2 == 9 || c2 == 10) || c1 == 4 && (c2 == 9 || c2 == 10) || c2 == 8 && (c1 == 9 || c1 == 10) || c2 == 4 && (c1 == 9 || c1 == 10))
			return 0;
		c1b = c1a;
		c2b = c2a;
		float xi = v->getPi()->getX(), yi = v->getPi()->getY(), xs = v->getPs()->getX(), ys = v->getPs()->getY();
		float x1 = this->getP1()->getX(), y1 = this->getP1()->getY(), x2 = this->getP2()->getX(), y2 = this->getP2()->getY();
		float dx = x2 - x1, dy = y2 - y1, m = 0;
		if (dx != 0)
			m = dy / dx;
		if (c1 >= 8)
		{
			l->getP1()->setX(xi);
			l->getP1()->setY(m*(xi - x2) + y2);
		}
		else
		if (c1 >= 4)
		{
			l->getP1()->setX(xs);
			l->getP1()->setY(m*(xs - x2) + y2);
		}
		if (c1 == 1)
		{
			l->getP1()->setY(ys);
			if (dx != 0)
				l->getP1()->setX((ys - y2) / m + x2);
		}
		if (c1 == 2)
		{
			l->getP1()->setY(yi);
			if (dx != 0)
				l->getP1()->setX((yi - y2) / m + x2);
		}
		//------
		if (c2 >= 8)
		{
			l->getP2()->setX(xi);
			l->getP2()->setY(m*(xi - x1) + y1);
		}
		else
		if (c2 >= 4)
		{
			l->getP2()->setX(xs);
			l->getP2()->setY(m*(xs - x1) + y1);
		}
		if (c2 == 1)
		{
			l->getP2()->setY(ys);
			if (dx != 0)
				l->getP2()->setX((ys - y1) / m + x1);
		}
		if (c2 == 2)
		{
			l->getP2()->setY(yi);
			if (dx != 0)
				l->getP2()->setX((yi - y1) / m + x1);
		}
		c1a = c1;
		c2a = c2;
	}
	return l;
}

//void Linea::mapear(Ventana * v, Puerto * p, float sx, float sy)
//{
//	p1->Mapear(v->getPi(), v->getPs(), p->getPi(), p->getPs(), sx, sy);
//	p2->Mapear(v->getPi(), v->getPs(), p->getPi(), p->getPs(), sx, sy);
//}
