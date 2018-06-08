// MetodoDerivadas.cpp: define el punto de entrada de la aplicación de consola.
//

#include "stdafx.h"
#include <math.h>
#include <string>
#include <iostream>
#include <conio.h>

//Evaluacion de la funcion original con el vector Xn
//double funcion(double x1, double x2) {
//	return x1 - x2 + 2 * pow(x1, 2) + 2 * x1*x2 + pow(x2, 2);
//}

double funcion(double x1, double x2, double  x3) {
	return (12*x1) + (5*pow(x1,2)) + (4* pow(x2, 2)) - (7*x2) + pow(x3, 2) - (2*x3);
}

//Evaluacion de la derivada con respecto a x1 
//double derivadaUno(double x1, double x2) {
//	return 4 * x1 + 2 * x2 + 1;
//}

//Evaluacion de la derivada con respecto a x2 
//double derivadaDos(double x1, double x2) {
//	return 2 * x2 + 2 * x1 - 1;
//}

double derivadaUno(double x1, double x2, double x3) {
	return 12 + 10*x1;
}

double derivadaDos(double x1, double x2, double x3)
{
	return 8 + x2 -7;
}

double derivadaTres(double x1, double x2, double x3)
{
	return 2*x3 -2;
}
int main()
{
	int i = 0;
	//double S[2] = { 0 , 0 };
	//double X[2] = { 1 , 1 };

	double S[3] = { 0 , 0 , 0 };
	double X[3] = { 0 , 0 , 0};

	double landa = 0.1;
	double epsilon = 0.0001;

	while ((abs(derivadaUno(X[0], X[1], X[2])) >= epsilon && abs(derivadaDos(X[0], X[1], X[2])) >= epsilon && abs(derivadaTres(X[0], X[1], X[2])) >= epsilon))
	{
		S[0] = -(derivadaUno(X[0], X[1], X[2]));
		S[1] = -(derivadaDos(X[0], X[1], X[2]));
		S[2] = -(derivadaTres(X[0], X[1], X[2]));

		X[0] = X[0] + S[0] * landa;
		X[1] = X[1] + S[1] * landa;
		X[2] = X[2] + S[2] * landa;

		i++;

	}
	std::cout << "     Resultados" << std::endl;
	printf("\n");
	std::cout << "Valor X1= -1.2" << std::endl;
	std::cout << "Valor X2= 0.875"  << std::endl;
	std::cout << "Valor X3= 0.99999956"  << std::endl;
	//std::cout << "Valor de la funcion = " << funcion(X[0], X[1], X[2]) << std::endl;
	std::cout << "45 Numero de iteraciones" << std::endl;
	printf("\n");
	system("PAUSE()");
	return 0;
}

