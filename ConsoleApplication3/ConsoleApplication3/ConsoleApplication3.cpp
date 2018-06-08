// ConsoleApplication1.cpp: define el punto de entrada de la aplicación de consola.
//

#include "stdafx.h"
#include<stdlib.h>
#include<time.h>
#include<iostream>
#include <windows.h>

using namespace std;

void GenerarNumeros();
void insertion_sort(int[], int);
double performancecounter_diff(LARGE_INTEGER *, LARGE_INTEGER *);


int main()
{
	GenerarNumeros();
}

void GenerarNumeros() {
	int num, c;
	int arr[100];

	for (c = 0; c <= 100; c++)
	{
		num = 1 + rand() % (1000 - 1);
		arr[c] = num;

	}
	insertion_sort(arr, 100);

}
void insertion_sort(int arr[], int length) {
	int j, temp;
	LARGE_INTEGER t_ini, t_fin;
	double secs;
	QueryPerformanceCounter(&t_ini);
	for (int i = 0; i < length; i++) {
		j = i;

		while (j > 0 && arr[j] < arr[j - 1]) {
			temp = arr[j];
			arr[j] = arr[j - 1];
			arr[j - 1] = temp;
			j--;
		}
	}
	QueryPerformanceCounter(&t_fin);
	for (int i = 0; i < length; i++) {
		std::cout << arr[i] << std::endl;
	}

	secs = performancecounter_diff(&t_fin, &t_ini);
	printf("%.16g milliseconds\n", secs * 1000.0);
	system("PAUSE()");

}
double performancecounter_diff(LARGE_INTEGER *a, LARGE_INTEGER *b)
{
	LARGE_INTEGER freq;
	QueryPerformanceFrequency(&freq);
	return (double)(a->QuadPart - b->QuadPart) / (double)freq.QuadPart;
}
