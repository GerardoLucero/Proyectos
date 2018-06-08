#pragma once
#include "stdafx.h"
#include "Nodo.h"

class Lista {
private:
	Nodo* Inicio;
	Nodo* Final;
public:
	Lista();
	void Agregar(Nodo*);
	void Eliminar(Nodo*);
	void Ordenar();
	Nodo* getInicio();
	void setInicio(Nodo*);
	Nodo* getFinal();
	void setFinal(Nodo*);
	void Intercambio(Nodo*, Nodo*);
};
