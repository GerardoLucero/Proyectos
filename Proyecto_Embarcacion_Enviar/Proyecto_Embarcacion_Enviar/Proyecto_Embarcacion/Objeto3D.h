#pragma once
#include "stdafx.h"
#include "Punto.h"
#include "Superficie.h"
#include "Ventana.h"
#include "Puerto.h"
#include "Foco.h"

class Objeto3D {
private:
	Superficie * Inicio;
	Superficie * Fin;
	ColorRGB * Color;

	Foco * F;
	float Ia;

	const double Pi = 2 * acos(0.0);

public:
	Objeto3D();
	~Objeto3D();
	Superficie * getInicio();
	void setInicio(Superficie *);
	Superficie * getFinal();
	void setFinal(Superficie *);
	Objeto3D * Copia();
	void agregar(Superficie *);
	void SetIa(float);
	float GetIa();
	void SetAllNormal();
	void SetVProm(Superficie *);
	void SetColor(ColorRGB *);

	void transformar(Matriz3D *);
	void vistaLatDer();
	void desplegarVistaLatDer(byte *, int, float, float, Ventana *, Puerto *);

	void BorrarVistaLatDer(byte *, int, float, float, Ventana *, Puerto *, int, int, int);

	void vistaLatSup();
	void desplegarVistaLatSup(byte *, int, float, float, Ventana *, Puerto *);

	void BorrarVistaLatSup(byte *, int, float, float, Ventana *, Puerto *, int, int, int);

	void vistaLatIzq();
	void desplegarVistaLatIzq(byte *, int, float, float, Ventana *, Puerto *);

	void BorrarVistaLatIzq(byte *, int, float, float, Ventana *, Puerto *, int, int, int);

	void vistaLatInf();
	void desplegarVistaLatInf(byte *, int, float, float, Ventana *, Puerto *);

	void BorrarVistaLatInf(byte *, int, float, float, Ventana *, Puerto *, int, int, int);


	void vistaLatPos();
	void desplegarVistaLatPos(byte *, int, float, float, Ventana *, Puerto *);

	void BorrarVistaLatPos(byte *, int, float, float, Ventana *, Puerto *, int, int, int);

	void perspectiva(float, float);
	void supVisiles();

	void obtenerColorGouraud(Vertice *);
	void SetFoco(Foco *);
	Foco * GetFoco();

	void Mapear(Ventana *, Puerto*);
	Punto * pMin();
	Punto * pMax();
	Punto * Centro();


	void Borrar(byte *, int, float, float, Ventana *, Puerto *, int, int, int);
	void Borrar(byte *, int);

	void desplegar(byte *, int, float, float, int, int , int);
	void desplegar(byte *, int, float, float);
	void desplegar(byte *, int, float, float,Ventana*, Puerto*, int );
	void desplegar(byte *, int, int, int, int);
	void desplegar(byte *, int, int);
	
};