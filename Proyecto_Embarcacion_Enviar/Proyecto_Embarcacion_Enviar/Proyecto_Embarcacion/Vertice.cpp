#pragma once
#include "stdafx.h"
#include "Vertice.h"

Vertice::Vertice():Punto(){
	siguiente = 0;
}

Vertice::Vertice(float x, float y, float z):Punto(x,y,z) {
	siguiente = 0;
}


Vertice * Vertice::Copia() {
	Vertice * v = new Vertice(getX(), getY(), getZ());
	return v;
}

void Vertice::setSig(Vertice *v) {
	siguiente = v;
}

Vertice * Vertice::getSig() {
	return siguiente;
}

void Vertice::perspectiva(float zo, float zf) {

	this->setX(((zf - zo) / (zf - this->getZ()))*this->getX());
	this->setY(((zf - zo) / (zf - this->getZ()))*this->getY());
}

void Vertice::calcularIr(float Ia, ColorRGB * K, Foco* f, Punto* cent, Vector* prom)
{
	float If = f->getI();
	float Ir, Ig, Ib;
	float Kr = (float)K->getR() / (float)255;
	float Kg = (float)K->getG() / (float)255;
	float Kb = (float)K->getB() / (float)255;
	Vector* N = prom;
	Vector* nu = N->unitario();
	Vector* L = new Vector(f->getPos(), cent);
	Vector* lu = L->unitario();
	float vectorCos = nu->multiplicacion(lu);
	if (vectorCos < 0)
		vectorCos = -1 * vectorCos;
	Ir = (Ia*Kr + f->getI()*Kr*vectorCos) * 255;
	if (Ir > 255)
		Ir = 255;
	Ig = (Ia*Kg + f->getI()*Kg*vectorCos) * 255;
	if (Ig > 255)
		Ig = 255;
	Ib = (Ia*Kb + f->getI()*Kb*vectorCos) * 255;
	if (Ib > 255)
		Ib = 255;
	I = new ColorRGB((int)Ir, (int)Ig, (int)Ib);
}

void Vertice::setI(ColorRGB * I)
{
	this->I = I;
}

ColorRGB * Vertice::getI()
{
	return I;
}

void Vertice::setVpromedio(Vector* prom) 
{
	this->vProm = prom;
}

Vector * Vertice::getVpromedio()
{
	return vProm;
}