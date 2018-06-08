#pragma once
#include "stdafx.h"
#include "Punto.h"
#include "Matriz3D.h"


Punto::Punto()
{
	this->x = -1;
	this->y = -1;
	this->z = -1;

}

Punto::Punto(float x, float y)
{
	this->x = x;
	this->y = y;
}

Punto::Punto(float x, float y, float z)
{
	this->x = x;
	this->y = y;
	this->z = z;
}


Punto::Punto(Punto * p)
{
	this->x = p->getX();
	this->y = p->getY();
	this->z = p->getZ();
}

void Punto::setX(float x)
{
	this->x = x;
}
float Punto::getX()
{
	return x;
}

void Punto::setY(float y)
{
	this->y = y;
}

float Punto::getY()
{
	return y;
}

void Punto::setZ(float z)
{
	this->z = z;
}


float Punto::getZ()
{
	return z;
}


void Punto::desplegar(CClientDC& dc)
{
	dc.SetPixel(x, y, color);

}

void Punto::Borrar(byte* b, int ancho, int R, int G, int B)
{
	int dir = (y * ancho * 4) + (x * 4);
	b[dir] = R;
	b[dir + 1] = G;
	b[dir + 2] = B;
	b[dir + 3] = 255;
}


void Punto::desplegar(byte* b, int ancho, int R, int G, int B)
{
	int dir = (y * ancho * 4) + (x * 4);
	b[dir] = R;
	b[dir + 1] = G;
	b[dir + 2] = B;
	b[dir + 3] = 255;
}
void Punto::desplegar(byte * b, int ancho)
{
	int dir = (y * ancho * 4) + (x * 4);
	b[dir] = 0;
	b[dir + 1] = 0;
	b[dir + 2] = 0;
	b[dir+3] = 255;
}

Punto* Punto::Copia()
{
	Punto * Pun1 = new Punto(x, y, z);
	return Pun1;
}

bool Punto::Seleccion(float x, float y)
{
	if (this->x - 5 <= x && this->x + 5 >= x && this->y - 5 <= y && this->y + 5 >= y)
		return true;
	else
		return false;
}

void Punto::Trasladar(float tx, float ty)
{
	this->x = x + tx;
	this->y = y + ty;
}


void Punto::Trasformar(Matriz3D * M)
{

	int x1 = 0, y1 = 0, z1 = 0;
	x1 = x*(M->getDato(0, 0)) + y *(M->getDato(0, 1)) + z*(M->getDato(0, 2)) + M->getDato(0, 3);
	y1 = x*(M->getDato(1, 0)) + y *(M->getDato(1, 1)) + z*(M->getDato(1, 2)) + M->getDato(1, 3);
	z1 = x*(M->getDato(2, 0)) + y *(M->getDato(2, 1)) + z*(M->getDato(2, 2)) + M->getDato(2, 3);
	x = x1;
	y = y1;
	z = z1;

}

int Punto::getCodigo(Punto * pto, Punto * pi, Punto * ps)
{
	int c = 0;
	float x = pto->getX(), y = pto->getY(), xi = pi->getX(), yi = pi->getY(), xs = ps->getX(), ys = ps->getY();
	if (x < xi)
		c = 8;
	if (x > xs)
		c = 4;
	if (y < ys)
		c = c | 1;
	if (y > yi)
		c = c | 2;
	return c;
}

void Punto::Mapear(Punto *vpi, Punto *vps, Punto *ppi, Punto *pps, float sx, float sy)
{
	this->x = (this->x - vpi->getX())* sx + ppi->getX();
	this->y = (this->y - vpi->getY())* sy + pps->getY();
}
