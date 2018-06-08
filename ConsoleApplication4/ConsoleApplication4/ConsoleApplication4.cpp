// ConsoleApplication4.cpp: define el punto de entrada de la aplicación de consola.
//

#include "stdafx.h"
#include <iostream>
#include <time.h>
using namespace std;

static double intSort(double);


int main()
{
    return 0;
}

double intSort(double valor) {
	int val = (int)valor;

	double A[(int)val];

	for (int i = 0; i < val; i++) {
		A[i] = (rand() % val + 1);
	}

	clock_t tStart = clock();
	for (int j = 1; j < val; j++) {
		int key = A[j];
		int i = j - 1;
		while (i >= 0 && A[i] > key) {

			A[i + 1] = A[i];
			i = i - 1;
		}
		A[i + 1] = key;
	}

	return (double)(clock() - tStart) / CLOCKS_PER_SEC;
}