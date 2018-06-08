#include "enjambre.h"
#include "ui_enjambre.h"
#include <QPainter>
#include <QPen>
QPainter *painter;
Solucion * mejor;

int ciudades[48][2]= {{6734, 1453},
                      {2233, 10},
                      {5530, 1424},
                      {401, 841},
                      {3082, 1644},
                      {7608, 4458},
                      {7573, 3716},
                      {7265, 1268},
                      {6898, 1885},
                      {1112, 2049},
                      {5468, 2606},
                      {5989, 2873},
                      {4706, 2674},
                      {4612, 2035},
                      {6347, 2683},
                      {6107, 669},
                      {7611, 5184},
                      {7462, 3590},
                      {7732, 4723},
                      {5900, 3561},
                      {4483, 3369},
                      {6101, 1110},
                      {5199, 2182},
                      {1633, 2809},
                      {4307, 2322},
                      {675, 1006},
                      {7555, 4819},
                      {7541, 3981},
                      {3177, 756},
                      {7352, 4506},
                      {7545, 2801},
                      {3245, 3305},
                      {6426, 3173},
                      {4608, 1198},
                      {23, 2216},
                      {7248, 3779},
                      {7762, 4595},
                      {7392, 2244},
                      {3484, 2829},
                      {6271, 2135},
                      {4985, 140},
                      {1916, 1569},
                      {7280, 4899},
                      {7509, 3239},
                      {10, 2676},
                      {6807, 2993},
                      {5185, 3258},
                      {3023, 1942}};



Enjambre::Enjambre(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::Enjambre)
{
    ui->setupUi(this);
    srand(time(NULL));

    int dx,dy;
    for(int i =0;i<48;i++){
        for(int j =0;j<48;j++){
            dx=ciudades[i][0]-ciudades[j][0];
            dy=ciudades[i][1]-ciudades[j][1];
            distancias[i][j]=(float)sqrt(dx*dx + dy*dy);
        }
    }
    mejor = new Solucion(distancias);
}

Enjambre::~Enjambre()
{
    delete ui;
}

void Enjambre::Pintar(QPainter *painter){
    int x1,x2,y1,y2,temp1,temp2;
    for(int i=0;i<47;i++){
        QPen pen(Qt::green, 3, Qt::SolidLine, Qt::RoundCap, Qt::RoundJoin);
        pen.setColor(QColor(0,i*5.2,0,255));
        painter->setPen(pen);

        temp1=mejor->camino[i];
        temp2=mejor->camino[i+1];
        x1=ciudades[temp1][0];
        x2=ciudades[temp1][1];
        y1=ciudades[temp2][0];
        y2=ciudades[temp2][1];

        painter->drawLine(x1/10,x2/10,y1/10,y2/10);

        pen.setColor(QColor(255,0,0,255));
        painter->setPen(pen);
        painter->drawEllipse(x1/10,x2/10,5,5);
    }
    QPen pen(Qt::green, 5, Qt::SolidLine, Qt::RoundCap, Qt::RoundJoin);
    pen.setColor(QColor(0,255,0,255));
    painter->setPen(pen);
    temp1=mejor->camino[47];
    temp2=mejor->camino[0];
    x1=ciudades[temp1][0];
    x2=ciudades[temp1][1];
    y1=ciudades[temp2][0];
    y2=ciudades[temp2][1];

    painter->drawLine(x1/10,x2/10,y1/10,y2/10);
}
void Enjambre::paintEvent(QPaintEvent *e){
    QPainter *painter= new QPainter(this);
    Pintar(painter);
    delete painter;
}

void Enjambre::Ejecutar(){
    Solucion * actual =new Solucion(distancias);
    mejor = actual->Copia(distancias);
    Solucion * Enjambre[48];
    int cont =0;
    float comp = mejor->aptitud;
    for(int i=0;i<48;i++){
        Enjambre[i]=new Solucion(distancias);
    }
    while(cont!=100){
        for(int i=0;i<48;i++){
            Enjambre[i]->ActualizaVelocidad(mejor);
            Enjambre[i]->ActualizaSolucion();
            if(Enjambre[i]->aptitud < mejor->aptitud){
                delete mejor;
                mejor=Enjambre[i]->Copia(distancias);
            }
        }

        ////////////
        if(comp==mejor->aptitud){
            cont++;
        }
        else{
            comp =mejor->aptitud;
            cont=0;
        }
    }
    repaint();
    ui->listWidget->addItem(QString::number(mejor->aptitud));
    ui->listWidget->setCurrentRow(ui->listWidget->count()-1);
}

void Enjambre::on_pushButton_clicked()
{
    Ejecutar();
}
