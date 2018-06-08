#pragma once

#include "stdafx.h"
#include "Matriz3D.h"
#include "Vertice.h"
#include "Ventana.h"
#include "Puerto.h"
#include "Lista.h"
#include "Nodo.h"
#include "Foco.h"
#include "ColorRGB.h"
#include "Vector.h"

class Superficie{
private:
	Vertice * Inicio;
	Vertice * Final;
	Superficie * Sig;

	Lista* listaActiva;
	Lista* listas[2000];

	float kr, kv, kz;
	ColorRGB * color;

	Vector * Normal;
public:
	Superficie();
	Superficie(Vertice *, Vertice *);

	~Superficie();
	Vertice * getInicio();
	void setInicio(Vertice *);
	Vertice * getFinal();
	void setFinal(Vertice *);
	Superficie * Copia();
	void Agregar(Vertice *);
	void desplegar(byte *, int, int );
	Superficie * getSig();
	void setSig(Superficie *);
	void transformar(Matriz3D *);
	Punto *pMin();
	Punto *pMax();
	void perspectiva(float, float);
	void mapear(Ventana *, Puerto *, float, float);
	int Visible();
	Vertice* vSig(Vertice*);
	Vertice* vAnt(Vertice*);

	Vector * GetNormal();
	void SetNormal(Vector *);

	int Adyacente(Vertice * );

	ColorRGB * obtenerColor(Punto*, Foco*, float);
	

	void RedondearVertices();
	float Redondear(float);

	void Relleno(byte *, int);

	void GenerarListaAristas();
	void CrearNodoArista(Vertice *, Vertice *, float);
	void Borrar(byte *, int);
	void BorrarRelleno(byte *, int);

	void actualizaListaActiva(int);

	void actualizaNodos(int);

	void RellenarPixeles(byte *, int, int, int, int, int);

	void RellenarPixelesGouraud(byte *, int, int);

	float getKr();
	float getKv();
	float getKz();
	void setKr(float);
	void setKv(float);
	void setKz(float);



	void SetColor(ColorRGB *);

	Vector * normal();

	void GenerarListaAristasRelleno();

	void CrearNodoAristaRelleno(Vertice * , Vertice * , float );
	
	void actualizaNodosRelleno(int);

	void actualizaListaActivaRelleno(int );

};