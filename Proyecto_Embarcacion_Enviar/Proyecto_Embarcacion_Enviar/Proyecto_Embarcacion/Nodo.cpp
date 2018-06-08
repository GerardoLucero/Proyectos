#pragma once
#include "stdafx.h"
#include "Nodo.h"
#include "ColorRGB.h"

Nodo::Nodo(){
	IncX = 0;
	x = 0;
	Ys = 0;
	sig = 0;
}
Nodo::Nodo(float xi, float X2, float Y2){
	IncX = xi;
	x = X2;
	Ys = Y2;
	sig = 0;
}
Nodo::Nodo(float incx, float x, float ysup, Nodo* sig){
	this->IncX = incx;
	this->x = x;
	this->Ys = ysup;
	this->sig = sig;
}

Nodo* Nodo::getSig(){
		return sig;
}

Nodo* Nodo::Copiar(){
	Nodo  * p = new Nodo(IncX, x, Ys,0);
	p->setInicial(getInicial());
	p->setIncIr(getIncIr());
	p->setIncIg(getIncIg());
	p->setIncIb(getIncIb());
	p->setSig(getSig());
	return p;
}
float Nodo::getX(){
	return x;
}
float Nodo::getYS(){
	return Ys;
}
float Nodo::getINCX(){
	return IncX;
}
void Nodo::setINCX(float IX){
	IncX = IX;
}
void Nodo::setX(float Xx){
	x = Xx;
}
void Nodo::setYS(float YY){
	Ys = YY;
}
void Nodo::setSig(Nodo *n){
	sig = n;
}

int Nodo::getIncIr()
{
	return incIr;
}

void Nodo::setIncIr(int IncIr)
{
	this->incIr = IncIr;
}

int Nodo::getIncIg()
{
	return incIg;
}

void Nodo::setIncIg(int IncIg)
{
	this->incIg = IncIg;
}

int Nodo::getIncIb()
{
	return incIb;
}

void Nodo::setIncIb(int IncIb)
{
	this->incIb = IncIb;
}

ColorRGB * Nodo::getInicial()
{
	return inicial;
}

void Nodo::setInicial(ColorRGB * r)
{
	this->inicial = r;
}