
// Proyecto_Embarcacion.h: archivo de encabezado principal para la aplicaci�n PROJECT_NAME
//

#pragma once

#ifndef __AFXWIN_H__
	#error "incluir 'stdafx.h' antes de incluir este archivo para PCH"
#endif

#include "resource.h"		// S�mbolos principales


// CProyecto_EmbarcacionApp:
// Consulte la secci�n Proyecto_Embarcacion.cpp para obtener informaci�n sobre la implementaci�n de esta clase
//

class CProyecto_EmbarcacionApp : public CWinApp
{
public:
	CProyecto_EmbarcacionApp();

// Reemplazos
public:
	virtual BOOL InitInstance();

// Implementaci�n

	DECLARE_MESSAGE_MAP()
};

extern CProyecto_EmbarcacionApp theApp;