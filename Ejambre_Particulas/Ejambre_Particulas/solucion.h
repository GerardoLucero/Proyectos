#ifndef SOLUCION_H
#define SOLUCION_H
#include<stdlib.h>
#include<time.h>
#include <math.h>
#include <QtGlobal>


class Solucion
{
public:
    Solucion(float[48][48]);
    float aptitud;
    float aptitudMejor;
    int camino[48];
    int mejorCamino[48];
    float probabilidades[48][48];
    float probCiudad[48];
    float distancias[48][48];

    float const W= 0.5;
    int const po1=4;
    int const po2=2;

    float getAptitud();
    void ActualizaSolucion();
    void ActualizaVelocidad(Solucion *);
    Solucion * Copia(float[48][48]);
    bool EstaEnCamino(int,int);
    bool EstaArista(int,int);
    int Ruleta();
    void OptimizacionLocal();
};

#endif // SOLUCION_H
