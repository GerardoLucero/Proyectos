#include "solucion.h"

Solucion::Solucion()
{
    int j,temp;
    int p[48] = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47};
    //shuffle p
    for (int i=47; i>0; --i){
        //get swap index
        j = rand()%i;
        //swap p[i] with p[j]
        temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }
    //copy first n elements from p to arr

    for (int i=0; i<48; ++i){
        camino[i] = p[i];
    }
    alfa=2;
    beta=2;
}
float Solucion::aptitud(float distancias[48][48]){
    float acumulado=0;
    int x,y;
    for(int i=0;i<48;i++){
        if(i==47){
            x=camino[47];
            y=camino[0];
            acumulado+=distancias[x][y];
        }
        else{
            x=camino[i];
            y=camino[i+1];
            acumulado+=distancias[x][y];
        }
    }
    return acumulado;
}

Solucion * Solucion::Construye(float feromonas[48][48],float distancias[48][48]){
    alfa=2;
    beta=2;
    int i =rand()% 48;
    camino[0]=i;
    for(int n=1;n<48;n++){
        float sumaProb=0;
        for(int j=0;j<48;j++){
            if(EstaEnCamino(j,n)==false){
                sumaProb+=(pow(feromonas[i][j],alfa)*pow(1/distancias[i][j],beta));
            }
        }
        for(int j=0;j<48;j++){
            if(EstaEnCamino(j,n)==false){
                probabilidades[j]=(pow(feromonas[i][j],alfa)*(pow(1/distancias[i][j],beta)))/sumaProb;
            }
            else{
                probabilidades[j]=0;
            }
        }
        i=Ruleta();
        camino[n]=i;
    }
    return this;
}

void Solucion::OptimizacionLocal(float distancias[48][48]){
    for(int i =0; i<46; i++ ){
        for(int j=i+1;j<47;j++){
            if((distancias[camino[i]][camino[i+1]]
                + distancias[camino[j]][camino[j+1]])
                > (distancias[camino[i]][camino[j]]
                + distancias[camino[i+1]][camino[j+1]])){
                int aux = camino[j];
                camino[j]=camino[i+1];
                camino[i+1]=aux;
            }
        }
    }
}

Solucion * Solucion::Copia(){
    Solucion * nueva = new Solucion();
    for(int i=0;i<48;i++){
        nueva->camino[i]=this->camino[i];
    }
    return nueva;
}
bool Solucion::EstaEnCamino(int valor,int rango){
    for(int i=0;i<rango;i++){
        if(camino[i]==valor){
            return true;
        }
    }
    return false;
}
int Solucion::Ruleta(){
     float prob=rand()% 10000;
     prob/=10000.0;
     float sumaProba=0;
     int j=-1;
     do{
         j++;
         sumaProba+=probabilidades[j];
     }while(sumaProba<=prob && j<47);
     return j;
}
