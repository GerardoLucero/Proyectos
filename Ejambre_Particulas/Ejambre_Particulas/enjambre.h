#ifndef ENJAMBRE_H
#define ENJAMBRE_H
#include "solucion.h"
#include <QMainWindow>

namespace Ui {
class Enjambre;
}

class Enjambre : public QMainWindow
{
    Q_OBJECT

public:
    explicit Enjambre(QWidget *parent = 0);
    ~Enjambre();
    void paintEvent(QPaintEvent *e);
    void Pintar(QPainter*);
    float distancias[48][48];
    void Ejecutar();

private slots:
    void on_pushButton_clicked();

private:
    Ui::Enjambre *ui;
};

#endif // ENJAMBRE_H
