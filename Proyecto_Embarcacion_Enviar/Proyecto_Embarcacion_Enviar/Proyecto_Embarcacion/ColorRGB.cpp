#include "stdafx.h"
#include "ColorRGB.h"

ColorRGB::ColorRGB()
{
	R = 0;
	G = 0;
	B = 0;
}


ColorRGB::ColorRGB(int r,int g,int b)
{
	R = r;
	G = g;
	B = b;
}

int ColorRGB ::getR()
{
	return this->R;
}

int ColorRGB::getG()
{
	return this->G;
}

int ColorRGB::getB()
{
	return this->B;
}


