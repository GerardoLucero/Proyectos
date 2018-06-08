#include "solucion.h"


Solucion::Solucion(float distanciasE[48][48])
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
        mejorCamino[i] = p[i];
    }
    for (int i=0; i<48; ++i){
        for (int j=0; j<48; j++){
            distancias[i][j]=distanciasE[i][j];
            probabilidades[i][j]=0.5;
        }
    }
    aptitud=getAptitud();
    aptitudMejor=getAptitud();
}
float Solucion::getAptitud(){
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

void Solucion::ActualizaVelocidad(Solucion * mejor){
    for (int i=0; i<48; ++i){
        for (int j=0; j<48; j++){
            probabilidades[i][j]*=W;
        }
    }
    for (int i=0; i<46; ++i){
        if(!EstaArista(mejorCamino[i],mejorCamino[i+1])){
            float ra=rand()% 1000;
            ra/=1000;
            float nvaProb = po1*ra;
            if(nvaProb>1){
                nvaProb=1;
            }
            if(nvaProb>probabilidades[mejorCamino[i]][mejorCamino[i+1]]){
                probabilidades[mejorCamino[i]][mejorCamino[i+1]]=nvaProb;
                probabilidades[mejorCamino[i+1]][mejorCamino[i]]=nvaProb;
            }
        }
    }
    for (int i=0; i<46; ++i){
        if(!EstaArista(mejor->camino[i],mejor->camino[i+1])){
            float ra=rand()% 1000;
            ra/=1000;
            float nvaProb = po2*ra;
            if(nvaProb>1){
                nvaProb=1;
            }
            if(nvaProb>probabilidades[mejor->camino[i]][mejor->camino[i+1]]){
                probabilidades[mejor->camino[i]][mejor->camino[i+1]]=nvaProb;
                probabilidades[mejor->camino[i+1]][mejor->camino[i]]=nvaProb;
            }
        }
    }

}

void Solucion::ActualizaSolucion(){
    float alfa=rand()% 1000;
    alfa/=1000;
    int i =rand()% 48;
    camino[0]=i;
    for(int n=1;n<48;n++){
        float sumaProb=0;
        for(int j=0;j<48;j++){
            if(probabilidades[i][j] && !EstaEnCamino(j,n)){
                sumaProb+=probabilidades[i][j];
            }
        }
        for(int j=0;j<48;j++){
            if(probabilidades[i][j] && !EstaEnCamino(j,n)){
                probCiudad[j]=probabilidades[i][j]/sumaProb;
            }
            else{
                probCiudad[j]=0;
            }
        }
        if(sumaProb==0){
            int mejorJ=-1;
            for(int j=0;j<48;j++){
                if(!EstaEnCamino(j,n)){
                    if(mejorJ==-1 || distancias[i][j]<distancias[i][mejorJ]){
                        mejorJ=j;
                    }
                }
            }
            i=mejorJ;
        }
        else{
            i=Ruleta();
        }
        camino[n]=i;
    }
    aptitud=getAptitud();
    if(aptitud < aptitudMejor){
        aptitudMejor=aptitud;
        for (int i=0; i<48; i++){
            mejorCamino[i]=camino[i];
        }

    }
}

void Solucion::OptimizacionLocal(){
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

Solucion * Solucion::Copia(float distanciasE[48][48]){
    Solucion * nueva = new Solucion(distanciasE);
    for(int i=0;i<48;i++){
        nueva->camino[i]=this->camino[i];
        nueva->mejorCamino[i]=this->mejorCamino[i];
         nueva->probCiudad[i]=this->probCiudad[i];
        for(int j=0; j<48;j++){
            nueva->probabilidades[i][j]=this->probabilidades[i][j];
        }
    }
    nueva->aptitud=this->aptitud;
    nueva->aptitudMejor=this->aptitudMejor;
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
bool Solucion::EstaArista(int c1,int c2){
    for (int i=0; i<48; i++){
        if((camino[i]==c1 && camino[i+1]==c2) || (camino[i+1]==c1 && camino[i]==c2) ){
            return true;
        }
        if((camino[0]==c1 && camino[47]==c2) || (camino[47]==c1 && camino[0]==c2) ){
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
        sumaProba+=probCiudad[j];
    }while(sumaProba<=prob && j<47);
    return j;
}
