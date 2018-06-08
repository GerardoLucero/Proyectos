#pragma once
#include "stdafx.h"
#include "Lista.h"
#include "Nodo.h"

Lista::Lista()
{
	Inicio = 0;
	Final = 0;
}
void Lista::Agregar(Nodo*p)
{
	if (Inicio == 0)
	{
		Inicio = p;
		Final = p;
	}
	else
	{
		Final->setSig(p);
		Final = p;
	}
	p->setSig(0);
}
void Lista::Eliminar(Nodo* n) {
	if (Inicio)
	{
		Nodo* aux = 0;
		if (n != Inicio)
		{
			aux = Inicio;
			while (aux->getSig() != n)
				aux = aux->getSig();
			aux->setSig(n->getSig());
		}
		else
		if (n == Inicio)
			Inicio = Inicio->getSig();
		if (n == Final)
			Final = aux;
		delete n;
	}
}
void Lista::Ordenar()
{
	Nodo* p = Inicio;
	while (p != 0){
		Nodo* min = p->Copiar();
		Nodo* q = p->getSig();
		while (q != 0){
			if (p->getX()>q->getX()){

				Nodo* min = q->Copiar();
				Nodo* aux = p->Copiar();

				p->setX(min->getX());

				p->setINCX(min->getINCX());

				p->setIncIr(min->getIncIr());
				p->setIncIg(min->getIncIg());
				p->setIncIb(min->getIncIb());
				p->setInicial(min->getInicial());

				p->setYS(min->getYS());

				q->setINCX(aux->getINCX());

				q->setIncIr(aux->getIncIr());
				q->setIncIg(aux->getIncIg());
				q->setIncIb(aux->getIncIb());
				q->setInicial(aux->getInicial());

				q->setX(aux->getX());
				q->setYS(aux->getYS());


			}
			q = q->getSig();
		}
		p = p->getSig();
	}
}
void Lista::Intercambio(Nodo* p, Nodo* q) {
	float aux = p->getX();
	p->setX(q->getX());
	q->setX(aux);

	aux = p->getYS();
	p->setYS(q->getYS());
	q->setYS(aux);

	aux = p->getINCX();
	p->setINCX(q->getINCX());
	q->setINCX(aux);
}

Nodo* Lista::getInicio() {
	return Inicio;

}

void Lista::setInicio(Nodo* Inicio) {
	this->Inicio = Inicio;
}

Nodo* Lista::getFinal() {
	return Final;

}

void Lista::setFinal(Nodo* Final) {
	this->Final = Final;
}