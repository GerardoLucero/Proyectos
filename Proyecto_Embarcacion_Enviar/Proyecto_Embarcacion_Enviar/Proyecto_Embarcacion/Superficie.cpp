#pragma once 
#include "stdafx.h"
#include "Superficie.h"
#include "Linea.h"
#include "Ventana.h"
#include "Vertice.h"
#include "ColorRGB.h"
#include "Vector.h"

Superficie::Superficie() {
	Inicio = 0;
	Final = 0;
	Sig = 0;
}

Superficie::Superficie(Vertice *ini, Vertice *fin) {
	Inicio = ini;
	Final = fin;
}

Superficie::~Superficie() {
	delete Inicio;
	delete Final;
}

Vertice * Superficie::getInicio() {
	return Inicio;
}

void Superficie::setInicio(Vertice * v){
	delete Inicio;
	Inicio = v;
}

Vertice * Superficie::getFinal() {
	return Final;
}

void Superficie::setFinal(Vertice * v) {
	delete Final;
	Final = v;
}

Superficie * Superficie::getSig() {
	return Sig;
}

void Superficie::setSig(Superficie *s) {
	Sig = s;
}

Superficie * Superficie::Copia() {
	Superficie * Copia = new Superficie();
	Vertice * s = Inicio;
	while (s != 0)
	{
		Copia->Agregar(s->Copia());
		s = s->getSig();
	}
	return Copia;
}

void Superficie::Agregar(Vertice *v) {

	if (v != 0)
	{
		if (Inicio == 0)
		{
			Inicio = v;
			Final = v;
		}
		else
		{
			Final->setSig(v);
			Final = v;
		}
		v->setSig(0);
	}
}
void Superficie::Borrar(byte * b, int ancho)
{
	BorrarRelleno(b, ancho);
	Vertice * v = Inicio;
	Linea *l = new Linea();
	while (v != 0){
		if (v != Final){
			Vertice *vc = v->Copia();
			l->setP1(vc);
			l->setP2(v->getSig()->Copia());
			l->Borrar(b, ancho, color->getR(), color->getR(), color->getR());
			delete vc;
		}
		else {
			Vertice *vc = Inicio->Copia();
			Vertice *vc2 = Final->Copia();
			l->setP1(vc);
			l->setP2(vc2);
			l->Borrar(b, ancho, color->getR(), color->getR(), color->getR());
			delete vc, vc2;
		}
		v = v->getSig();
	}

}

void Superficie::desplegar(byte * b, int ancho, int va) {
	if (va == 0)
		Relleno(b, ancho);
	Vertice * v = Inicio;
	Linea *l = new Linea();
	while (v != 0){
		if (v != Final){
			Vertice *vc = v->Copia();
			l->setP1(vc);
			l->setP2(v->getSig()->Copia());
			l->Desplegar(b, ancho);
			delete vc;
		}
		else {
			Vertice *vc = Inicio->Copia();
			Vertice *vc2 = Final->Copia();
			l->setP1(vc);
			l->setP2(vc2);
			l->Desplegar(b, ancho);
			delete vc, vc2;
		}
		v = v->getSig();
	}

	delete l;
}

void Superficie::SetNormal(Vector * v)
{
	this->Normal = v;
}

Vector * Superficie::GetNormal()
{
	return this->Normal;
}



Vector * Superficie::normal(){
	float vx1, vx2, vx3, vy1, vy2, vy3, vz1, vz2, vz3;
	Vertice * V = Inicio;
	vx1 = V->getX(); vy1 = V->getY(); vz1 = V->getZ();
	V = V->getSig();
	vx2 = V->getX(); vy2 = V->getY(); vz2 = V->getZ();
	V = V->getSig();
	vx3 = V->getX(); vy3 = V->getY(); vz3 = V->getZ();
	float A = vy1*(vz2 - vz3) + vy2*(vz3 - vz1) + vy3*(vz1 - vz2);
	float B = vz1*(vx2 - vx3) + vz2*(vx3 - vx1) + vz3*(vx1 - vx2);
	float C = vx1*(vy2 - vy3) + vx2*(vy3 - vy1) + vx3*(vy1 - vy2);
	return new Vector(A, B, C);
}
int Superficie::Adyacente(Vertice * v)
{
	Vertice * vi = Inicio;
	while (vi != 0)
	{
		if ((int)vi->getX() == (int)v->getX() && (int)vi->getY() == (int)v->getY() && (int)vi->getZ() == (int)v->getZ())
			return 1;
		vi = vi->getSig();

	}
	return 0;
}

ColorRGB * Superficie::obtenerColor(Punto* c, Foco* f, float ia){

	Vector * l = new Vector(f->getPos()->getX() - c->getX(), f->getPos()->getY() - c->getY(), f->getPos()->getZ() - c->getZ());
	
	Vector* N = normal();
	Vector* ul = l->unitario();
	Vector* uN = N->unitario();
	float cos = uN->multiplicacion(ul);
	if (cos<0)cos = 0;
	float r = (kr*ia + kr*f->getI()*cos) * 255;
	if (r>255)r = 255;
	float v = (kv*ia + kv*f->getI()*cos) * 255;
	if (v>255)v = 255;
	float z = (kz*ia + kz*f->getI()*cos) * 255;
	if (z>255)z = 255;

	return new ColorRGB((int)r , (int) v,(int)  z);
};



void Superficie::RedondearVertices()
{
	Vertice * v = Inicio;
	while (v!= 0)
	{
		v->setX(Redondear(v->getX()));
		v->setY(Redondear(v->getY()));
		v->setZ(Redondear(v->getZ()));
		v = v->getSig();
	}
}

float Superficie::Redondear(float v)
{
	if (v - (int)v>0.5)
		return ceil(v);
	else
		return floor(v);
}
void Superficie::Relleno(byte * b, int ancho)
{
	RedondearVertices();
	listaActiva = new Lista();
	for (int i = 0; i < 2000; i++)
		listas[i] = new Lista();
	GenerarListaAristas();
	for (int y = 0; y < 2000; y++)
	{
		if (listas[y]->getInicio() != 0)
			actualizaListaActiva(y);
		if (listaActiva->getInicio() != 0)
		{
			listaActiva->Ordenar();
			RellenarPixelesGouraud(b, ancho, y);
			actualizaNodos(y);
		}
	}
}
void Superficie::BorrarRelleno(byte * b, int ancho)
{
	RedondearVertices();
	listaActiva = new Lista();
	for (int i = 0; i < 2000; i++)
		listas[i] = new Lista();
	GenerarListaAristasRelleno();
	
	for (int y = 0; y < 2000; y++)
	{
		if (listas[y]->getInicio() != 0)
			actualizaListaActiva(y);
		if (listaActiva->getInicio() != 0)
		{
			listaActiva->Ordenar();
			RellenarPixeles(b, ancho, y, color->getR(), color->getG(), color->getB());
			actualizaNodosRelleno(y);	
		}
	}


}

void Superficie::RellenarPixeles(byte * b, int ancho, int y, int R, int G, int B){
	Nodo* p = listaActiva->getInicio();
	Punto* pixel = new Punto();
	Nodo* q = 0;
	while (p != 0){
		q = p->getSig();
		for (int x = p->getX(); x <= q->getX(); x++){
			pixel = new Punto(x, y);
			pixel->desplegar(b, ancho, R, G, B);
		}
		p = q->getSig();

	}
	delete p;
	delete pixel;
}

void Superficie::RellenarPixelesGouraud(byte * b, int ancho, int y)
{
	Nodo* p = listaActiva->getInicio();
	Nodo* q = 0;
	Punto* point = new Punto(0, y);
	float compRe = p->getInicial()->getR(), compGe = p->getInicial()->getG(), compBe = p->getInicial()->getB();
	ColorRGB * inicial;
	while (p != 0)
	{
		compRe += p->getIncIr();
		compGe += p->getIncIg();
		compBe += p->getIncIb();
		q = p->getSig();
		if (q != 0)
		{
			float compRi = p->getInicial()->getR(), compGi = p->getInicial()->getG(), compBi = p->getInicial()->getB();
			float compRf = q->getInicial()->getR(), compGf = q->getInicial()->getG(), compBf = q->getInicial()->getB();
			if (y == 206)
			{
				int asd =0;
			}
			for (int x = p->getX(); x <= q->getX(); x++)
			{
				point->setX(x);
				point->desplegar(b, ancho, compRe, compGe, compBe);
				if (q->getX() - p->getX() != 0)
					compRe += (compRf - compRi) / (q->getX() - p->getX());
				if (q->getX() - p->getX() != 0)
					compGe += (compGf - compGi) / (q->getX() - p->getX());
				if (q->getX() - p->getX() != 0)
					compBe += (compBf - compBi) / (q->getX() - p->getX());
			}
			p = q->getSig();
		}
		else
		{
			point->setX(p->getX());
			point->desplegar(b, ancho, compRe, compGe, compBe);
		
			p = p->getSig();
		}
	}
	delete point;


}

void Superficie::actualizaNodosRelleno(int y)
{
	Nodo * p = listaActiva->getInicio();
	Nodo * aux;
	while (p != 0)
	{
		if (y >= (int)p->getYS())
		{
			aux = p->getSig();
			listaActiva->Eliminar(p);
			p = aux;
		}
		else{
			p->setX(p->getX() + p->getINCX());
			p = p->getSig();
		}
	}
}
void Superficie::actualizaNodos(int y)
{
	Nodo * p = listaActiva->getInicio();
	Nodo * aux;
	while (p != 0)
	{
		if (y >= (int)p->getYS())
		{
			aux = p->getSig();
			listaActiva->Eliminar(p);
			p = aux;
		}
		else{
			p->setX(p->getX() + p->getINCX());
			p->setInicial(new ColorRGB(p->getInicial()->getR() + p->getIncIr(), p->getInicial()->getG() + p->getIncIg(), p->getInicial()->getB() + p->getIncIb()));

			p = p->getSig();
		}
	}
}
void Superficie::actualizaListaActiva(int y)
{
	Nodo* p = listas[y]->getInicio();
	while (p != 0){
		listaActiva->Agregar(p->Copiar());
		p = p->getSig();
	}
}
void Superficie::CrearNodoArista(Vertice * vi, Vertice * vs, float ycomp)
{
	Nodo * n = new Nodo();
	n->setINCX((vs->getX() - vi->getX() )/ (vs->getY() - vi->getY()));
	n->setX(vi->getX());
	n->setInicial(vi->getI());
	if ((vs->getY() - vi->getY()) == 0)
	{
		n->setIncIr(0);
		n->setIncIg(0);
		n->setIncIb(0);
	}
	else
	{
		n->setIncIr((vs->getI()->getR() - vi->getI()->getR()) / (vs->getY() - vi->getY()));
		n->setIncIg((vs->getI()->getG() - vi->getI()->getG()) / (vs->getY() - vi->getY()));
		n->setIncIb((vs->getI()->getB() - vi->getI()->getB()) / (vs->getY() - vi->getY()));
	}

	if ( (int)vs->getY() < ycomp)
		n->setYS(vs->getY() - 1);
	else
		n->setYS(vs->getY());
	if (listas[(int)vi->getY()]==0)
		listas[(int)vi->getY()] = new Lista();
	listas[(int)vi->getY()]->Agregar(n);
}

Vertice* Superficie::vSig(Vertice* v){
	if (v->getSig() == 0)
		return Inicio;
	else return v->getSig();
}

Vertice* Superficie::vAnt(Vertice* v){
	Vertice* f = Inicio;
	Vertice* a = 0;
	while (f != 0){
		if (v == Inicio)
			return Final;
		else
		if (v == f)
			return a;
		a = f;
		f = f->getSig();
	}
}

void Superficie::GenerarListaAristasRelleno()
{
	Vertice * v2 = Final;
	Vertice * v1 = Inicio;
	Vertice * ant = vAnt(v2);
	while (v1 != 0)
	{
		if (v1->getY() != v2->getY())
		{
			if (v1->getY()< v2->getY())
				CrearNodoAristaRelleno(v1, v2, (int)vAnt(v2)->getY());
			else{
				CrearNodoAristaRelleno(v2, v1, (int)vSig(v1)->getY());
			}
		}
		v2 = v1;
		v1 = v1->getSig();
	}
}

void Superficie::CrearNodoAristaRelleno(Vertice * vi, Vertice * vs, float ycomp)
{
	Nodo * n = new Nodo();
	n->setINCX((vs->getX() - vi->getX()) / (vs->getY() - vi->getY()));
	n->setX(vi->getX());
	if ((int)vs->getY() < ycomp)
		n->setYS(vs->getY() - 1);
	else
		n->setYS(vs->getY());
	if (listas[(int)vi->getY()] == 0)
		listas[(int)vi->getY()] = new Lista();
	listas[(int)vi->getY()]->Agregar(n);
}

void Superficie::GenerarListaAristas()
{
	Vertice * v2 = Final;
	Vertice * v1 = Inicio;
	Vertice * ant = vAnt(v2);
	while (v1 != 0)
	{
		if (v1->getY() != v2->getY())
		{
			if (v1->getY()< v2->getY())
				CrearNodoArista(v1, v2, (int)ant->getY());
			else{
				CrearNodoArista(v2, v1, (int)vSig(v1)->getY());
			}
		}
		ant = v2;
		v2 = v1;
		v1 = v1->getSig();
	}
}
void Superficie::transformar(Matriz3D * m) {
	Vertice *v = Inicio;
	while (v != 0) {
		v->Trasformar(m); 
		v = v->getSig();
	}
}

Punto * Superficie::pMin()
{
	Vertice *aux = Inicio;
	float x = Inicio->getX();
	float y = Inicio->getY();
	float z = Inicio->getZ();
	while (aux != 0)
	{
		if (x>aux->getX())
			x = aux->getX();
		if (y>aux->getY())
			y = aux->getY();
		if (z>aux->getZ())
			z = aux->getZ();
		aux = aux->getSig();
	}
	return new Punto(x, y, z);
}

Punto * Superficie::pMax()
{
	Vertice *aux = Inicio;
	float x = Inicio->getX();
	float y = Inicio->getY();
	float z = Inicio->getZ();
	while (aux != 0)
	{
		if (x<aux->getX())
			x = aux->getX();
		if (y<aux->getY())
			y = aux->getY();
		if (z<aux->getZ())
			z = aux->getZ();
		aux = aux->getSig();
	}
	return new Punto(x, y, z);
}

void Superficie::perspectiva(float zo, float zf) {
	Vertice * ini = Inicio;
	while (ini != 0) {
		ini->perspectiva(zo, zf);
		ini = ini->getSig();
	}
}

void Superficie::mapear(Ventana *v, Puerto *p, float sx, float sy) {
	Vertice * ini = Inicio;
	while (ini != 0) {
		ini->Mapear(v->getPi(), v->getPs(), p->getPi(), p->getPs(), sx, sy);
		ini = ini->getSig();
	}
}

int Superficie::Visible() {
	float x1 = Inicio->getX();
	float x2 = Inicio->getSig()->getX();
	float x3 = Inicio->getSig()->getSig()->getX();
	float y1 = Inicio->getY();
	float y2 = Inicio->getSig()->getY();
	float y3 = Inicio->getSig()->getSig()->getY();
	float c = x1*(y2 - y3) + x2*(y3 - y1) + x3*(y1 - y2); 

	if (c >= 0)
		return 1;
	else
		return 0;
}
void Superficie::SetColor(ColorRGB * c)
{
	color = c;
}
float Superficie::getKr(){
	return kr;
}

float Superficie::getKv(){
	return kv;
}

float Superficie::getKz(){
	return kz;
}

void Superficie::setKr(float k){
	kr = k;
}

void Superficie::setKv(float k){
	kv = k;
}

void Superficie::setKz(float k){
	kz = k;
}
