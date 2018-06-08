
#include "stdafx.h"
#include "ListaPuerto.h"
ListaPuerto::ListaPuerto() {

	inicio = 0;

	fin = 0;

}

ListaPuerto::~ListaPuerto() {

	Puerto *in = inicio;
	while (inicio != 0) {
		inicio = in->getSiguiente();
		delete in;
		in = inicio;
	}
}

Puerto *ListaPuerto::seleccion(float x, float y) {
	Puerto *copia = inicio;
	while (copia != 0) {
		if (copia->seleccion(x, y))
			return copia;

		copia = copia->getSiguiente();
	}
	return 0;
}

void ListaPuerto::agregar(Puerto *l) {
	if (inicio == 0) {
		inicio = l;
		fin = l;
	}
	else {
		fin->setSiguiente(l);
		fin = l;
	}
	l->setSiguiente(0);
}
