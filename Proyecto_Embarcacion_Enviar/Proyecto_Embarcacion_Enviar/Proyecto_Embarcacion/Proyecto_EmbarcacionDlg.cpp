
// Proyecto_EmbarcacionDlg.cpp: archivo de implementación
//

#include "stdafx.h"
#include "Proyecto_Embarcacion.h"
#include "Proyecto_EmbarcacionDlg.h"
#include "afxdialogex.h"

#include <gdiplus.h>
#include "Objeto3D.h"
#include "Vertice.h"
#include "Bezier.h";
#include "Punto.h";

using namespace std;
using namespace Gdiplus;

//#ifdef _DEBUG
//#define new DEBUG_NEW
//#endif

ULONG_PTR gdiplusToken;
Bitmap * bitmap = NULL;

// Cuadro de diálogo CAboutDlg utilizado para el comando Acerca de

class CAboutDlg : public CDialogEx
{
public:
	CAboutDlg();

// Datos del cuadro de diálogo
	enum { IDD = IDD_ABOUTBOX };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // Compatibilidad con DDX/DDV

// Implementación
protected:
	DECLARE_MESSAGE_MAP()
};

CAboutDlg::CAboutDlg() : CDialogEx(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialogEx)
END_MESSAGE_MAP()


// Cuadro de diálogo de CProyecto_EmbarcacionDlg



CProyecto_EmbarcacionDlg::CProyecto_EmbarcacionDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CProyecto_EmbarcacionDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CProyecto_EmbarcacionDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CProyecto_EmbarcacionDlg, CDialogEx)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	ON_WM_CREATE()
	ON_WM_DESTROY()
	ON_WM_ERASEBKGND()
	ON_BN_CLICKED(IDC_BUTTON1, &CProyecto_EmbarcacionDlg::OnBnClickedButton1)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
END_MESSAGE_MAP()


// Controladores de mensaje de CProyecto_EmbarcacionDlg

BOOL CProyecto_EmbarcacionDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	// Agregar el elemento de menú "Acerca de..." al menú del sistema.

	// IDM_ABOUTBOX debe estar en el intervalo de comandos del sistema.
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		BOOL bNameValid;
		CString strAboutMenu;
		bNameValid = strAboutMenu.LoadString(IDS_ABOUTBOX);
		ASSERT(bNameValid);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// Establecer el icono para este cuadro de diálogo.  El marco de trabajo realiza esta operación
	//  automáticamente cuando la ventana principal de la aplicación no es un cuadro de diálogo
	SetIcon(m_hIcon, TRUE);			// Establecer icono grande
	SetIcon(m_hIcon, FALSE);		// Establecer icono pequeño

	// TODO: agregar aquí inicialización adicional

	return TRUE;  // Devuelve TRUE  a menos que establezca el foco en un control
}

void CProyecto_EmbarcacionDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	if ((nID & 0xFFF0) == IDM_ABOUTBOX)
	{
		CAboutDlg dlgAbout;
		dlgAbout.DoModal();
	}
	else
	{
		CDialogEx::OnSysCommand(nID, lParam);
	}
}

// Si agrega un botón Minimizar al cuadro de diálogo, necesitará el siguiente código
//  para dibujar el icono.  Para aplicaciones MFC que utilicen el modelo de documentos y vistas,
//  esta operación la realiza automáticamente el marco de trabajo.

Objeto3D * dibujo = new Objeto3D();
Superficie * s;
int op = 0;
Ventana * vent = new Ventana(0, 1800, 1800, 0);
Ventana * vent2 = new Ventana(0, 1300, 1300, 0);
Ventana * vent1 = new Ventana(0, 1300, 1300, 0);
Ventana * vent3 =  new Ventana(0, 1500, 1500, 0);

Puerto * puert = new Puerto(850, 600, 1050, 800);
Puerto * puert1 = new Puerto(850, 100, 1050, 300);
Puerto * puert2 = new Puerto(850, 350, 1050, 550);
Puerto * puert3 = new Puerto(50, 50, 800, 800);
const int NumK = 6;
Bezier * Kappa[NumK];
Punto * p = new Punto(600, 200, 700);
Foco * f = new Foco(p, .50);
Objeto3D * copia = 0;
float Ia = 0.30; 
Punto * T1 = 0;
Punto * T2 = 0;
Punto * p1 = 0;
Punto * p2 = 0;
Linea * L = 0;


void desplegar(CClientDC &dc, Objeto3D * ob, int v)
{
	Graphics graphics(dc);
	BitmapData* bitmapData = new BitmapData;
	Rect rect(0, 0, bitmap->GetWidth(), bitmap->GetHeight());
	bitmap->LockBits(&rect, ImageLockModeRead, PixelFormat32bppARGB, bitmapData);
	byte * datos = (byte *)bitmapData->Scan0;
	int ancho = bitmap->GetWidth();

		ob->desplegarVistaLatDer(datos, ancho, 100, 2000, puert1->getVentana(), puert1);
		ob->desplegarVistaLatInf(datos, ancho, 100, 2000, puert->getVentana(), puert);
		ob->desplegarVistaLatSup(datos, ancho, 100, 2000, puert2->getVentana(), puert2);
		ob->desplegar(datos, ancho, 100, 2000, puert3->getVentana(), puert3, v);

	bitmap->UnlockBits(bitmapData);
	graphics.DrawImage(bitmap, 0, 0);
	delete bitmapData;

}

void borrar(CClientDC &dc, Objeto3D * ob)
{
	Graphics graphics(dc);
	BitmapData* bitmapData = new BitmapData;
	Rect rect(0, 0, bitmap->GetWidth(), bitmap->GetHeight());
	bitmap->LockBits(&rect, ImageLockModeRead, PixelFormat32bppARGB, bitmapData);
	byte * datos = (byte *)bitmapData->Scan0;
	int ancho = bitmap->GetWidth();

	int col = 0;
	ob->BorrarVistaLatDer(datos, ancho, 100, 2000, puert1->getVentana(), puert1, col, col, col);
	ob->BorrarVistaLatInf(datos, ancho, 100, 2000, puert->getVentana(), puert, col, col, col);
	ob->BorrarVistaLatSup(datos, ancho, 100, 2000, puert2->getVentana(), puert2, col, col, col);
	ob->Borrar(datos, ancho, 100, 2000, puert3->getVentana(), puert3, col, col, col);

	bitmap->UnlockBits(bitmapData);
	graphics.DrawImage(bitmap, 0, 0);
	delete bitmapData;
}
void ConstruirBarco()
{
	dibujo = new Objeto3D();


	s = new Superficie();
	for (int i = NumK - 1; i >= 0; i--)
		s->Agregar(Kappa[i]->Curva[Kappa[0]->M]->Copia());
	for (int i = 0; i < NumK; i++)
		s->Agregar(Kappa[i]->Curva[0]->Copia());
	dibujo->agregar(s);


	s = new Superficie();
	for (int j = 0; j < Kappa[0]->M + 1; j++)
		s->Agregar(Kappa[NumK - 1]->Curva[j]->Copia());
	dibujo->agregar(s);

	
	s = new Superficie();
	for (int j = Kappa[0]->M; j >= 0; j--)
		s->Agregar(Kappa[0]->Curva[j]->Copia());
	dibujo->agregar(s);

	
	s = new Superficie();
	for (int i = 0; i < NumK - 1; i++)
	{
		for (int j = 0; j < Kappa[0]->M; j++)
		{
			s->Agregar(Kappa[i + 1]->Curva[j]->Copia());
			s->Agregar(Kappa[i]->Curva[j]->Copia());
			s->Agregar(Kappa[i]->Curva[j + 1]->Copia());
			s->Agregar(Kappa[i + 1]->Curva[j + 1]->Copia());

			dibujo->agregar(s);
			s = new Superficie();
		}
	}
		

	dibujo->SetColor(new ColorRGB(255, 128, 0));
	dibujo->SetFoco(f);
	dibujo->SetIa(Ia);
}

void CalcularPuntos()
{
	int x1 = 300;
	int xCtrl = 700;
	int Altura = 1000;
	for (int i = 0; i < NumK; i++)
	{
		Kappa[i] = new Bezier();
		Kappa[i]->Control[0]->setX(x1);
		Kappa[i]->Control[0]->setY(Altura);
		Kappa[i]->Control[0]->setZ(0);

		Kappa[i]->Control[1]->setX(xCtrl);
		Kappa[i]->Control[1]->setY(Altura);
		Kappa[i]->Control[1]->setZ(400 * (0.25));

		Kappa[i]->Control[2]->setX(xCtrl);
		Kappa[i]->Control[2]->setY(Altura);
		Kappa[i]->Control[2]->setZ(400 * (.75));

		Kappa[i]->Control[3]->setX(x1);
		Kappa[i]->Control[3]->setY(Altura);
		Kappa[i]->Control[3]->setZ(400);

		Altura -= 100;
		if (i == 0)
			xCtrl += 120;
		else if (i < (NumK - 2))
			xCtrl += 200;
		else
			xCtrl += 50;
		Kappa[i]->calcular();

	}
	ConstruirBarco();
	
}



void CProyecto_EmbarcacionDlg::OnPaint()
{
	if (IsIconic())
	{
		CPaintDC dc(this); // Contexto de dispositivo para dibujo

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// Centrar icono en el rectángulo de cliente
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;
		// Dibujar el icono
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialogEx::OnPaint();
	}
	CClientDC dc(this);
	CRect rect;
	this->GetClientRect(rect);
	delete bitmap;
	bitmap = new Bitmap(rect.Width(), rect.Height(), PixelFormat32bppARGB);
	
	CalcularPuntos();
	puert->setVentana(vent1);
	puert1->setVentana(vent2);
	puert2->setVentana(vent3);
	puert3->setVentana(vent);

	puert->desplegar(dc);
	puert1->desplegar(dc);
	puert2->desplegar(dc);
	puert3->desplegar(dc);

	desplegar(dc, dibujo, 0);

}


HCURSOR CProyecto_EmbarcacionDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}




int CProyecto_EmbarcacionDlg::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CDialogEx::OnCreate(lpCreateStruct) == -1)
		return -1;

	// TODO:  Agregue aquí su código de creación especializado
	GdiplusStartupInput gdiplusStartupInput;
	GdiplusStartup(&gdiplusToken, &gdiplusStartupInput, NULL);
	return 0;
}


void CProyecto_EmbarcacionDlg::OnDestroy()
{
	CDialogEx::OnDestroy();
	GdiplusShutdown(gdiplusToken);
	delete dibujo;
	delete puert;
	delete puert1;
	delete puert2;
	delete puert3;
	delete p;
	delete f;
	delete copia;
	delete  T1;
	delete T2;
	delete p1;
	delete p2;
	delete L;
	// TODO: Agregue aquí su código de controlador de mensajes
}


BOOL CProyecto_EmbarcacionDlg::OnEraseBkgnd(CDC* pDC)
{

	CRect rect;
	GetClientRect(&rect);
	CBrush myBrush(RGB(0, 0, 0));    // dialog background color
	CBrush *pOld = pDC->SelectObject(&myBrush);
	BOOL bRes = pDC->PatBlt(0, 0, rect.Width(), rect.Height(), PATCOPY);
	pDC->SelectObject(pOld);    // restore old brush
	return bRes;
	// TODO: Agregue aquí su código de controlador de mensajes o llame al valor predeterminado
	return CDialogEx::OnEraseBkgnd(pDC);

}



void CProyecto_EmbarcacionDlg::OnBnClickedButton1()
{
	if (op == 0){
		CClientDC dc(this);
		op = 1;
	}
	else
	{
		op == 0;
	}
	// TODO: Agregue aquí su código de controlador de notificación de control
}

void PuntoGrande(Punto * pun, CClientDC& dc)
{
	for (int i = 0; i < 5; i++)
		for (int j = 0; j < 5; j++)
		{
			dc.SetPixel(pun->getX() + i, pun->getY() + j, RGB(238, 218, 154));
		}
}


void BorrarPuntoGrande(Punto * pun, CClientDC& dc)
{
	for (int i = 0; i < 5; i++)
	for (int j = 0; j < 5; j++)
	{
		dc.SetPixel(pun->getX() + i, pun->getY() + j, RGB(0, 0, 0));
	}
}
void CProyecto_EmbarcacionDlg::OnLButtonDown(UINT nFlags, CPoint point)
{

	if (op == 1)
	{
		CClientDC dc(this);
		float sx = (puert3->getPs()->getX() - puert3->getPi()->getX()) / (vent->getPs()->getX() - vent->getPi()->getX());
		float sy = (puert3->getPi()->getY() - puert3->getPs()->getY()) / (vent->getPs()->getY() - vent->getPi()->getY());
		Punto * psPuerto = puert3->getPs();
		Punto * piPuerto = puert3->getPi();
		Punto * piVentana = vent->getPi();
		Punto * psVentana = vent->getPs();

		for (int i = 0; i < NumK; i++)
		{
			
			p1 = Kappa[i]->Control[1]->Copia();
			p2 = Kappa[i]->Control[2]->Copia();
			p1->Mapear(piVentana, psVentana, piPuerto, psPuerto, sx, sy);
			p2->Mapear(piVentana, psVentana, piPuerto, psPuerto, sx, sy);

			p1->setX(p1->getX() + 50);
			p2->setX(p2->getX() + 50);
			p1->setY(p1->getY() + 50);
			p2->setY(p2->getY() + 50);
			PuntoGrande(p1, dc);
			if (p1->Seleccion(point.x, point.y) || p2->Seleccion(point.x, point.y))
			{
				BorrarPuntoGrande(p1,dc);
				T1 = Kappa[i]->Control[1];
				T2 = Kappa[i]->Control[2];
				L = new Linea(point.x, point.y, point.x, point.y);
			}
			
		}

	}
	// TODO: Agregue aquí su código de controlador de mensajes o llame al valor predeterminado

	CDialogEx::OnLButtonDown(nFlags, point);
}

void CProyecto_EmbarcacionDlg::OnMouseMove(UINT nFlags, CPoint point)
{

	if (op== 1)
	{
		CClientDC dc(this);
		if (T1 != 0 && T2 != 0)
		{
			if (L != 0)
			{
				BorrarPuntoGrande(L->getP1(), dc);
				L->setP2(new Punto(point.x, point.y));
				float dx = L->getP2()->getX() - L->getP1()->getX();
				float dy = L->getP2()->getY() - L->getP1()->getY();
				T1->Trasladar(dx, dy);
				T2->Trasladar(dx, dy);
				L->getP1()->Trasladar(dx, dy);
				PuntoGrande(L->getP1(), dc);
			}
		}
	}
	// TODO: Agregue aquí su código de controlador de mensajes o llame al valor predeterminado

	CDialogEx::OnMouseMove(nFlags, point);
}

void CProyecto_EmbarcacionDlg::OnLButtonUp(UINT nFlags, CPoint point)
{
	CClientDC dc(this);
	if (L != 0)
	{
		BorrarPuntoGrande(L->getP1(), dc);
		float sx = (puert3->getPs()->getX() - puert3->getPi()->getX()) / (vent->getPs()->getX() - vent->getPi()->getX());
		float sy = (puert3->getPi()->getY() - puert3->getPs()->getY()) / (vent->getPs()->getY() - vent->getPi()->getY());
		Punto * psPuerto = puert3->getPs();
		Punto * piPuerto = puert3->getPi();
		Punto * piVentana = vent->getPi();
		Punto * psVentana = vent->getPs();
		for (int i = 0; i < NumK; i++)
		{

			p1 = Kappa[i]->Control[1]->Copia();
			p2 = Kappa[i]->Control[2]->Copia();
			p1->Mapear(piVentana, psVentana, piPuerto, psPuerto, sx, sy);
			p2->Mapear(piVentana, psVentana, piPuerto, psPuerto, sx, sy);
			p1->setX(p1->getX() + 50);
			p2->setX(p2->getX() + 50);
			p1->setY(p1->getY() + 50);
			p2->setY(p2->getY() + 50);
			PuntoGrande(p1, dc);
		}
		L = 0;
	}
	if (T1 != 0 && T2 != 0)
	{
		borrar(dc, dibujo);
		delete dibujo;
		for (int i = 0; i < NumK; i++)
			Kappa[i]->calcular();
		ConstruirBarco();
		desplegar(dc, dibujo, 0);
		T1 = 0;
		T2 = 0;
	}
	// TODO: Agregue aquí su código de controlador de mensajes o llame al valor predeterminado
	CDialogEx::OnLButtonUp(nFlags, point);
}


