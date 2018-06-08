#pragma once

#include "stdafx.h"
#include "Puerto.h"
#include "Punto.h"


class ListaPuerto {

private:

	Puerto *inicio;
	Puerto *fin;
	Puerto *sigPuerto;

public:

	ListaPuerto();
	~ListaPuerto();
	Puerto * seleccion(float, float);
	void agregar(Puerto *);
};