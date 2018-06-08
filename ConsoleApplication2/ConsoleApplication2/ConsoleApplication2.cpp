// ConsoleApplication1.cpp: define el punto de entrada de la aplicación de consola.
//

#include "stdafx.h"
#include<stdlib.h>
#include<time.h>
#include<iostream>

void GenerarNumeros();
void insertion_sort(int[], int);

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
	clock_t times;
	for (int i = 0; i < length; i++) {
		j = i;
		times = clock();
		while (j > 0 && arr[j] < arr[j - 1]) {
			temp = arr[j];
			arr[j] = arr[j - 1];
			arr[j - 1] = temp;
			j--;
		}
	}
	for (int i = 0; i < length; i++) {
		std::cout << arr[i] << std::endl;
	}

	times = times / CLOCKS_PER_SEC;
	std::cout << "Valor = " << times << std::endl;
	system("PAUSE()");

}
