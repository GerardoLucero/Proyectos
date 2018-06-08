#ifndef ACO_H
#define ACO_H
#include "solucion.h"
#include <QMainWindow>

namespace Ui {
class Aco;
}

class Aco : public QMainWindow
{
    Q_OBJECT

public:
    explicit Aco(QWidget *parent = 0);
    void paintEvent(QPaintEvent *e);
    float distancias[48][48];
    float feromonas[48][48];
    void Ejecutar();
    void EvaporarFeromonas();
    void DepositarFeromonas(Solucion *);
    void Pintar(QPainter*);
    ~Aco();

private slots:
    void on_pushButton_clicked();

private:
    Ui::Aco *ui;
};

#endif // ACO_H
