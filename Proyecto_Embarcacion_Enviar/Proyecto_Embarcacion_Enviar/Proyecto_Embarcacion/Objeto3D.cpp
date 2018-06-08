#include "stdafx.h"
#include "Objeto3D.h"

Objeto3D::Objeto3D() {
	this->Inicio = 0;
	this->Fin = 0;
}
Objeto3D::~Objeto3D()
{
	Superficie *p = Inicio;
	Superficie *a = Inicio;
	while (p != 0)
	{
		a = p->getSig();
		delete p;
		p = a;
	}
}

void Objeto3D::setInicio(Superficie * s)
{
	delete Inicio;
	Inicio = s;
}

Superficie * Objeto3D::getInicio()
{
	return this->Inicio;
}
void Objeto3D::setFinal(Superficie * s)
{
	delete Fin;
	Fin = s;
}

Superficie * Objeto3D::getFinal()
{
	return this->Fin;
}
void Objeto3D::SetColor(ColorRGB * c)
{
	this->Color = c;
}

Foco * Objeto3D::GetFoco()
{
	return this->F;

}

void Objeto3D::SetFoco(Foco * f)
{
	F = f;
}

void Objeto3D::SetIa(float ia)
{
	this->Ia = ia;
}

float Objeto3D::GetIa()
{
	return this->Ia;
}


void Objeto3D::agregar(Superficie * s)
{
	if (s != 0)
	{
		if (Inicio == 0)
		{
			Inicio = s;
			Fin = s;
		}
		else
		{
			Fin->setSig(s);
			Fin = s;
		}
		s->setSig(0);
	}
}

Objeto3D * Objeto3D::Copia()
{
	Objeto3D * Copia3D = new Objeto3D();

		Superficie* L = Inicio;

		while (L != 0)
		{
			Copia3D->agregar(L->Copia());
			L = L->getSig();
		}
	Copia3D->SetColor(Color);
	Copia3D->SetFoco(F);
	Copia3D->SetIa(Ia);
	return Copia3D;

}

void Objeto3D::transformar(Matriz3D * M)
{
	Superficie * L = Inicio;
	while (L != 0)
	{
		L->transformar(M);
		L = L->getSig();
	}
}

Punto * Objeto3D::pMin() {
	Superficie * a = Inicio;
	Punto * pm;
	float xm = NULL, ym = NULL, zm = NULL;
	while (a != 0) {
		pm = a->pMin();
		if (a == Inicio) {
			xm = pm->getX();
			ym = pm->getY();
			zm = pm->getZ();
		}
		else {
			if (xm > pm->getX())
				xm = pm->getX();
			if (ym > pm->getY())
				ym = pm->getY();
			if (zm > pm->getZ())
				zm = pm->getZ();
		}
		a = a->getSig();
		delete pm;
	}
	return new Punto(xm, ym, zm);
}
void Objeto3D::SetAllNormal()
{
	Superficie * s = Inicio;
	while (s != 0)
	{
		s->SetNormal(s->normal());
		s = s->getSig();
	}
	 s = Inicio;
	while (s != 0)
	{	
		SetVProm(s);
		s = s->getSig();
	}
	
}
void Objeto3D::SetVProm(Superficie * sp)
{
	Vector * v = new Vector(0, 0, 0);
	Vertice * ve = sp->getInicio();

	Superficie * sa = 0;
	while (ve != 0)
	{
	   sa = Inicio;
		v = new Vector(0, 0, 0);
		int c = 0;
		while (sa != 0)
		{
			
			if (sa->Adyacente(ve) )
			{
				v->setX(v->getX() + sa->GetNormal()->getX());
				v->setY(v->getY() + sa->GetNormal()->getY());
				v->setZ(v->getZ() + sa->GetNormal()->getZ());
				c++;
			}
			sa = sa->getSig();
		}


		ve->setVpromedio(v);
		obtenerColorGouraud(ve);
		delete v;
		ve = ve->getSig();
	}
}


Punto * Objeto3D::pMax() {
	Superficie * a = Inicio;
	Punto * pm;
	float xm = NULL, ym = NULL, zm = NULL;
	while (a != 0) {
		pm = a->pMax();
		if (a == Inicio) {
			xm = pm->getX();
			ym = pm->getY();
			zm = pm->getZ();
		}
		else {
			if (xm < pm->getX())
				xm = pm->getX();
			if (ym < pm->getY())
				ym = pm->getY();
			if (zm < pm->getZ())
				zm = pm->getZ();
		}
		a = a->getSig();
		delete pm;
	}
	return new Punto(xm, ym, zm);
}

Punto * Objeto3D::Centro() {
	Punto * pmin = pMin();
	Punto * pmax = pMax();
	float xmed = (pmax->getX() + pmin->getX()) / 2;
	float ymed = (pmax->getY() + pmin->getY()) / 2;
	float zmed = (pmax->getZ() + pmin->getZ()) / 2;
	Punto * p = new Punto(xmed, ymed, zmed);
	delete pmin;
	delete pmax;
	return p;
}

void Objeto3D::supVisiles() {
	Superficie *anterior = 0;
	Superficie * actual = Inicio;
	while (actual != 0) {
		if (!actual->Visible()) {
			if (actual == Inicio){
				Fin = 0;
				anterior = actual;
				actual = actual->getSig();
				Inicio = actual;
				delete anterior;
			}
			else if (actual == Fin){
				Fin = anterior;
				anterior->setSig(0);
				delete actual;
				actual = anterior->getSig();
			}
			else {
				anterior->setSig(actual->getSig());
				delete actual;
				actual = anterior->getSig();
			}
		}
		else {
			anterior = actual;
			actual = anterior->getSig();
		}
	}
}

void Objeto3D::perspectiva(float zo, float zf)
{
	Superficie * s = Inicio;
	while (s != 0)
	{
		s->perspectiva(zo, zf);
		s = s->getSig();
	}
}

void Objeto3D::Mapear(Ventana * v, Puerto * p)
{
	Superficie * s = Inicio;
	float sx = (p->getPs()->getX() - p->getPi()->getX()) / (v->getPs()->getX() - v->getPi()->getX());
	float sy = (p->getPi()->getY() - p->getPs()->getY()) / (v->getPs()->getY() - v->getPi()->getY());
	while (s != 0)
	{
		s->mapear(v, p, sx, sy);
		s = s->getSig();
	}
}
void Objeto3D::obtenerColorGouraud(Vertice * c)
{

	Vector * l = new Vector(F->getPos()->getX() - c->getX(), F->getPos()->getY() - c->getY(), F->getPos()->getZ() - c->getZ());

	Vector* N = c->getVpromedio();
	Vector* ul = l->unitario();
	Vector* uN = N->unitario();
	float cos = uN->multiplicacion(ul);

	if (cos<0)cos = 0;
	float r = ((float)Color->getR() / 255*Ia + (float)Color->getR() / 255*F->getI()*cos) * 255;
	if (r>255)r = 255;
	float v = ((float)Color->getG() / 255 * Ia + (float)Color->getG() / 255 * F->getI()*cos) * 255;
	if (v>255)v = 255;
	float z = ((float)Color->getB() / 255 * Ia + (float)Color->getB() / 255 * F->getI()*cos) * 255;
	if (z>255)z = 255;

	c->setI(new ColorRGB((int)r, (int)v, (int)z));

}
void Objeto3D::vistaLatDer(){
	Punto * c = Centro();
	float ang = -90;
	Matriz3D * t1 = new Matriz3D(1, 0, 0, c->getX(), 0, 1, 0, c->getY(),  0, 0, 1, c->getZ());
	Matriz3D * t2 = new Matriz3D(1, 0, 0, -c->getX(), 0, 1, 0, -c->getY(), 0, 0, 1, -c->getZ());
	Matriz3D * ry = new Matriz3D(cos(ang*(Pi / 180)), 0, sin(ang*(Pi / 180)), 0, 0, 1, 0, 0, -sin(ang*(Pi / 180)), 0, 0, cos(ang*(Pi / 180)));

	Matriz3D * m = t1->multiplicar(ry->multiplicar(t2));
	this->transformar(m);
	delete c;
	delete t1;
	delete t2;
	delete ry;
	delete m;
}

void Objeto3D::desplegarVistaLatDer(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p) {
	Objeto3D* copia = Copia();
	copia->SetAllNormal();
	copia->vistaLatDer();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, 0);
	delete copia;
}

void Objeto3D::BorrarVistaLatDer(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)
{
	Objeto3D* copia = Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->vistaLatDer();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}

void Objeto3D::vistaLatSup(){
	Punto * c = Centro();
	Matriz3D *t1 = new Matriz3D(1, 0, 0, c->getX(), 0, 1, 0, c->getY(), 0, 0, 1, c->getZ());
	Matriz3D *t2 = new Matriz3D(1, 0, 0, -c->getX(), 0, 1, 0, -c->getY(), 0, 0, 1, -c->getZ());
	Matriz3D* rx = new Matriz3D(1, 0, 0, 0, 0, cos(-(Pi/2)), -sin(-(Pi/2)), 0, 0, sin(-(Pi/2)), cos(-(Pi/2)), 0);
	Matriz3D* m = t1->multiplicar(rx->multiplicar(t2));
	this->transformar(m);
	delete c;
	delete t1;
	delete t2;
	delete rx;
	delete m;

}

void Objeto3D::desplegarVistaLatSup(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p) {
	Objeto3D* copia = this->Copia();
	copia->SetAllNormal();
	copia->vistaLatSup();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, 0);
	delete copia;
}

void Objeto3D::BorrarVistaLatSup(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)

{
	Objeto3D* copia = this->Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->vistaLatSup();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}

void Objeto3D::vistaLatIzq(){
	Punto * c = Centro();
	Matriz3D *t1 = new Matriz3D(1, 0, 0, c->getX(), 0, 1, 0, c->getY(), 0, 0, 1, c->getZ());
	Matriz3D *t2 = new Matriz3D(1, 0, 0, -c->getX(), 0, 1, 0, -c->getY(), 0, 0, 1, -c->getZ());
	Matriz3D* ry = new Matriz3D(cos(Pi/2), 0, sin(Pi/2), 0, 0, 1, 0, 0, -sin(Pi/2), 0,0, cos(Pi/2));
	Matriz3D* m = t1->multiplicar(ry->multiplicar(t2));
	this->transformar(m);
	delete c;
	delete t1;
	delete t2;
	delete ry;
	delete m;

}

void Objeto3D::desplegarVistaLatIzq(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p) {
	Objeto3D* copia = this->Copia();
	copia->SetAllNormal();	
	copia->vistaLatIzq();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, 0);
	delete copia;
}

void Objeto3D::desplegar(byte * d, int ancho, float zo, float zf)
{
	Objeto3D* copia = this->Copia();
	copia->SetAllNormal();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->desplegar(d, ancho, 0);
	delete copia;
}

void Objeto3D::desplegar(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int va)
{
	Objeto3D* copia = this->Copia();
	copia->SetAllNormal();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, va);
	delete copia;
}



void Objeto3D::BorrarVistaLatIzq(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)
{
	Objeto3D* copia = this->Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->vistaLatIzq();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}

void Objeto3D::vistaLatInf(){	
	Punto * c = Centro();
	Matriz3D *t1 = new Matriz3D(1, 0, 0, c->getX(), 0, 1, 0, c->getY(), 0, 0, 1, c->getZ());
	Matriz3D *t2 = new Matriz3D(1, 0, 0, -c->getX(), 0, 1, 0, -c->getY(), 0, 0, 1, -c->getZ());
	Matriz3D* rx = new Matriz3D(1, 0, 0, 0, 0, cos(Pi/2), -sin(Pi/2), 0, 0, sin(Pi/2), cos(Pi/2), 0);;
	
	Matriz3D* m = t1->multiplicar(rx->multiplicar(t2));
	this->transformar(m);
	delete c;
	delete t1;
	delete t2;
	delete rx;
	delete m;
}

void Objeto3D::desplegarVistaLatInf(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p) {
	Objeto3D* copia = this->Copia();

	copia->SetAllNormal();
	copia->vistaLatInf();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, 0);
	delete copia;
}
void Objeto3D::BorrarVistaLatInf(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)
{
	Objeto3D* copia = this->Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->vistaLatInf();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}

void Objeto3D::vistaLatPos(){
	Punto * c = Centro();

	Matriz3D *t1 = new Matriz3D(1, 0, 0, c->getX(), 0, 1, 0, c->getY(), 0, 0, 1, c->getZ());
	Matriz3D *t2 = new Matriz3D(1, 0, 0, -c->getX(), 0, 1, 0, -c->getY(), 0, 0, 1, -c->getZ());
	Matriz3D* ry = new Matriz3D(cos(90), 0, sin(90), 0, 0, 1, 0, 0, -sin(90), 0,0, cos(90));
	Matriz3D* m = t1->multiplicar(ry->multiplicar(t2));
	this->transformar(m);
	delete c;
	delete t1;
	delete t2;
	delete ry;
	delete m;

}

void Objeto3D::desplegarVistaLatPos(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p) {
	Objeto3D* copia = this->Copia();
	copia->vistaLatPos();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->desplegar(d, ancho, 0);
	delete copia;
}

void Objeto3D::BorrarVistaLatPos(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)
{
	Objeto3D* copia = this->Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->vistaLatPos();
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}

void Objeto3D::Borrar(byte * d, int ancho, float zo, float zf, Ventana * v, Puerto * p, int R, int G, int B)
{
	Objeto3D* copia = this->Copia();
	copia->SetColor(new ColorRGB(R, G, B));
	copia->perspectiva(zo, zf);
	copia->supVisiles();
	copia->Mapear(v, p);
	copia->Borrar(d, ancho);
	delete copia;
}


void Objeto3D::desplegar(byte * d, int ancho, int R, int G, int B)
{
	Superficie * s = Inicio;
	ColorRGB * c = new ColorRGB(R, G, B);
	while (s != 0)
	{
		s->SetColor(c);
		s->desplegar(d, ancho, 0);
		s = s->getSig();
	}
	delete c;

}

void Objeto3D::desplegar(byte * d, int ancho, int va)
{
	Superficie * s = Inicio;
	ColorRGB * c = new ColorRGB(Color->getR(), Color->getG(), Color->getB());
	while (s != 0)
	{
		s->SetColor(c);
		s->desplegar(d, ancho,va);
		s = s->getSig();
	}
	delete c;
}
void Objeto3D::Borrar(byte * d, int ancho)
{

	ColorRGB * c = new ColorRGB(Color->getR(), Color->getG(), Color->getB());
	Superficie * s = Inicio;
	while (s != 0)
	{
		s->SetColor(c);
		s->Borrar(d, ancho);
		s = s->getSig();
	}
}


