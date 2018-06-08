// ComparacionOrdenacion.cpp: define el punto de entrada de la aplicación de consola.
//

#include "stdafx.h"
#include <iostream>
#include "windows.h"
#include "time.h"
#include "dos.h"
#include <stdlib.h>
#include <math.h>


using namespace::std;
void metodo2();
void rellenar(int [], int );
int alea(int , int);
bool repetido(int[], int, int);
void calculo(int [], int, double [], int);
void Merge_Sort(int [], int , int);
void Merge(int[], int, int, int);

const int v1 = 100;
const int v2 = 100;



int _tmain(int argc, _TCHAR* argv[])
{
	//metodo2();
	int arr1[100];
	rellenar(arr1, 99);
	Merge_Sort(arr1, 0, 100);
	for (int i = 0; i < 100; i++)
	{
		printf("Elmento /n", arr1[i]);
	}
	system("pause");
	return 0;
}

void metodo2() {
	double tiempo[4];
	int arr1[100];
	int arr2[1000];
	int arr3[10000];
	int arr4[100000];
	rellenar(arr1, 100);
	rellenar(arr2, 1000);
	rellenar(arr3, 10000);
	rellenar(arr4, 100000);
	calculo(arr1, 100, tiempo, 0);
	calculo(arr2, 1000, tiempo, 1);
	calculo(arr3, 10000, tiempo, 2);
	calculo(arr4, 100000, tiempo, 3);
	printf("Tiempo de 100 elementos: %f\n", tiempo[0]);
	printf("Tiempo de 1000 elementos: %f\n", tiempo[1]);
	printf("Tiempo de 10000 elementos: %f\n", tiempo[2]);
	printf("Tiempo de 100000 elementos: %f\n", tiempo[3]);
}

void rellenar(int arr[], int elems) {
	int num = 0;
	arr[0] = 0;
	for (int i = 1; i < elems; i++) {
		num = elems - i;
		arr[i] = num;
	}
	printf("Se termino de rellenar el arreglo de %d...\n", elems);
}

int alea(int desde, int hasta) {
	return rand() % (hasta - desde + 1) + desde;
}

bool repetido(int arr[], int elems, int val) {
	for (int i = 0; i < elems; i++)
		if (arr[i] == val)
			return true;
	return false;
}

void calculo(int arr[], int elems, double tiempo[], int indice) {
	clock_t start, end;
	int key = 0, i = 0;
	start = clock();
	for (int j = 2; j < elems; j++) {
		key = arr[j];
		i = j - 1;
		while (i > 0 && arr[i] > key) {
			arr[i + 1] = arr[i];
			i = i - 1;
		}
		arr[i + 1] = key;
	}
	end = clock();
	tiempo[indice] = (end - start) / CLK_TCK;
}

void Merge_Sort(int A[], int p, int r) {
	if (p < r) {
		int q = (p + r) / 2;
		Merge_Sort(A, p, q);
		Merge_Sort(A, q + 1, r);
		Merge(A, p, q, r);
	}
}

void Merge(int A[], int p, int q, int r) {
	int n1 = q + p + 1;
	int n2 = r - q;
	int *L = new int[n1 + 1];
	int *R = new int[n2 + 1];

	for (int i = 0; i < n1; i++) {
		L[i] = A[p + i];
	}

	for (int j = 0; j < n2; j++) {
		R[j] = A[q + j +1];
	}

	L[n1] = 9999999999;
	L[n2] = 9999999999;
	int i = 0;
	int j = 0;
	for (int k = p; k < r; k++) {
		if (L[i] <= R[j]) {
			A[k] = L[i];
			i++;
		}
		else {
			A[k] = R[j];
			j = j + 1;
		}
	}

}
