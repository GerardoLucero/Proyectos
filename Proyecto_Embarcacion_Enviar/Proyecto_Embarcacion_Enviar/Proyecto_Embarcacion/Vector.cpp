#include "stdafx.h"	
#include "Vector.h"
#include "Math.h"
#include "Punto.h"


Vector::Vector() {
	x = y = z = 0;
}
Vector::Vector(float x, float y, float z) {
	this->x = x;
	this->y = y;
	this->z = z;
}

Vector::Vector(Punto*Pf, Punto*Pc){
	this->x = (float)Pf->getX() - Pc->getX();
	this->y = (float)Pf->getY() - Pc->getY();
	this->z = (float)Pf->getZ() - Pc->getZ();
}

float Vector::multiplicacion(Vector* v) {
	return ((x*v->getX()) + (y*v->getY()) + (z*v->getZ()));
}

float Vector::magnitud() {
	return sqrt(x*x + y*y + z*z);
}

Vector*Vector::unitario() {
	float mag = magnitud();
	return new Vector((float)x / mag, (float)y / mag, (float)z / mag);
}

void Vector::setX(float x) {
	this->x = x;
}
void Vector::setY(float y) {
	this->y = y;
}
void Vector::setZ(float z) {
	this->z = z;
}
float Vector::getX() {
	return x;
}
float Vector::getY() {
	return y;
}
float Vector::getZ() {
	return z;


}

