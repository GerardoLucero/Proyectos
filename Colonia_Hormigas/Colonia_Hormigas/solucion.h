#ifndef SOLUCION_H
#define SOLUCION_H
#include<stdlib.h>
#include<time.h>
#include <math.h>
#include <QtGlobal>


class Solucion
{
public:
    Solucion();
    int camino[48];
    float probabilidades[48];
    int alfa;
    int beta;
    float aptitud(float[48][48]);
    Solucion * Construye(float[48][48],float[48][48]);
    Solucion * Copia();
    bool EstaEnCamino(int,int);
    int Ruleta();
    void OptimizacionLocal(float[48][48]);
};

#endif // SOLUCION_H
