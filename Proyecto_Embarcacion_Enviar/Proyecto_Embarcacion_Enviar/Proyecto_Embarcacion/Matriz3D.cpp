#pragma once

#include "stdafx.h"
#include "Matriz3D.h"
#include <math.h>

Matriz3D::Matriz3D() {
	setDato(1, 0, 0);
	setDato(0, 0, 1);
	setDato(0, 0, 2);
	setDato(0, 0, 3);

	setDato(0, 1, 0);
	setDato(1, 1, 1);
	setDato(0, 1, 2);
	setDato(0, 1, 3);

	setDato(0, 2, 0);
	setDato(0, 2, 1);
	setDato(1, 2, 2);
	setDato(0, 2, 3);

	setDato(0, 3, 0);
	setDato(0, 3, 1);
	setDato(0, 3, 2);
	setDato(1, 3, 3);
}

Matriz3D::Matriz3D(float uno, float dos, float tres, float cuatro, float cinco, float seis, float siete, float ocho, float nueve, float diez, float once, float doce) {
	setDato(uno, 0, 0);
	setDato(dos, 0, 1);
	setDato(tres, 0, 2);
	setDato(cuatro, 0, 3);

	setDato(cinco, 1, 0);
	setDato(seis, 1, 1);
	setDato(siete, 1, 2);
	setDato(ocho, 1, 3);

	setDato(nueve, 2, 0);
	setDato(diez, 2, 1);
	setDato(once, 2, 2);
	setDato(doce, 2, 3);

	setDato(0, 3, 0);
	setDato(0, 3, 1);
	setDato(0, 3, 2);
	setDato(1, 3, 3);
}

void Matriz3D::setDato(float dato, int fila, int columna) {
	this->datos[fila][columna] = dato;
}

float Matriz3D::getDato(int fila, int columna) {
	return this->datos[fila][columna];
}

Matriz3D * Matriz3D::copiar() {
	Matriz3D * Copia = new Matriz3D();
	Copia->setDato(this->getDato(0, 0), 0, 0);
	Copia->setDato(this->getDato(0, 1), 0, 1);
	Copia->setDato(this->getDato(0, 2), 0, 2);
	Copia->setDato(this->getDato(0, 3), 0, 3);

	Copia->setDato(this->getDato(1, 0), 1, 0);
	Copia->setDato(this->getDato(1, 1), 1, 1);
	Copia->setDato(this->getDato(1, 2), 1, 2);
	Copia->setDato(this->getDato(1, 3), 1, 3);

	Copia->setDato(this->getDato(2, 0), 2, 0);
	Copia->setDato(this->getDato(2, 1), 2, 1);
	Copia->setDato(this->getDato(2, 2), 2, 2);
	Copia->setDato(this->getDato(2, 3), 2, 3);

	Copia->setDato(this->getDato(3, 0), 3, 0);
	Copia->setDato(this->getDato(3, 1), 3, 1);
	Copia->setDato(this->getDato(3, 2), 3, 2);
	Copia->setDato(this->getDato(3, 3), 3, 3);

	return Copia;
}

Matriz3D * Matriz3D::multiplicar(Matriz3D *M) {
	Matriz3D * NM = new Matriz3D();
	float valor = 0;
	for (int f = 0; f < 4; f++)
	{
		//valor = 0;
		for (int c = 0; c < 4; c++)
		{
			valor = this->getDato(f, 0) * M->getDato(0, c);
			valor += this->getDato(f, 1) * M->getDato(1, c);
			valor += this->getDato(f, 2) * M->getDato(2, c);
			valor += this->getDato(f, 3) * M->getDato(3, c);
			NM->setDato(valor, f, c);
		}
	}
	return NM;
}

Matriz3D * Matriz3D::transformar(float tx, float ty, float tz)
{
	Matriz3D * Trasladar = new Matriz3D();

	Trasladar->setDato(1, 0, 0);
	Trasladar->setDato(0, 0, 1);
	Trasladar->setDato(0, 0, 2);
	Trasladar->setDato(tx, 0, 3);

	Trasladar->setDato(0, 1, 0);
	Trasladar->setDato(1, 1, 1);
	Trasladar->setDato(0, 1, 2);
	Trasladar->setDato(ty, 1, 3);

	Trasladar->setDato(0, 2, 0);
	Trasladar->setDato(0, 2, 1);
	Trasladar->setDato(1, 2, 2);
	Trasladar->setDato(tz, 2, 3);

	Trasladar->setDato(0, 3, 0);
	Trasladar->setDato(0, 3, 1);
	Trasladar->setDato(0, 3, 2);
	Trasladar->setDato(1, 3, 3);

	return Trasladar;
}
