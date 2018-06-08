
import java.util.Random; 
import java.util.concurrent.ThreadLocalRandom;
 
 
public class KOPT { 
    Ciudad[] temp = new Ciudad[48];
    public KOPT(){ 
    } 
    private void inserta(Ciudad[] copy, int a, int b){ 
        if(a == 47)
            temp[0] = copy[b];
        else
            temp[a+1] = copy[b];
        int i=0;
        int j=0;
        while(i<48)
        {
            if(j != a+1){
                if(i != b)
                {
                    temp[i] = copy[j];
                    j++;
                }
                i++; 
            }
            else
               j++;
            
        }
        copy = temp;
    }  
    public Solucion optimizar(Solucion copy){ 
        Random rnd = ThreadLocalRandom.current();
        //Could go wrong 
        int index = rnd.nextInt(48); 
        int index2 = rnd.nextInt(48);
        int bestScore = 0;
        int bestScore2 = 0;
        if(index == 47)
            bestScore = copy.getTour()[index].Distancia(0);
        else
            bestScore = copy.getTour()[index].Distancia(index+1);
        if(index2 == 47)
            bestScore2 = copy.getTour()[index2].Distancia(0);
        else
            bestScore2 = copy.getTour()[index2].Distancia(index2+1);
        int curScore = 0;  
        int curScore2 = 0;
        int contador = 0;
        int ind = 0;
        int ind2 =0;
        while(contador < 48)
        {
            if(index + contador < 47)
            {
                ind = index + contador;
                curScore = copy.getTour()[index].Distancia(ind);
            }
            else
            {
                ind = index + contador -47;
                curScore = copy.getTour()[index].Distancia(ind);
            }
            if(curScore > bestScore)
            {
                bestScore = curScore;
                inserta(copy.getTour(),index,ind);
            }
            if(index2 + contador < 47)
            {
                ind2 = index2 + contador;
                curScore2 = copy.getTour()[index2].Distancia(ind2);
            }
            else
            {
                ind2 = index2 + contador -47;
                curScore2 = copy.getTour()[index2].Distancia(ind2);
            }
            if(curScore2 > bestScore2)
            {
                bestScore2 = curScore2;
                inserta(copy.getTour(),index2,ind2);
            }
            contador ++;
        }
        return copy;
    }
    public Solucion optimizarLineal(Solucion copy){ 
        int bestScore = 0;
        int curScore = 0;  
        copy.EvaluarAptitud();
        for(int i = 0; i < 47; i ++)
        {
            bestScore = copy.getAptitud();
            for(int j=i;j<47;j++)
            {
                insrta(copy.getTour(),i,j);
                curScore = copy.getAptitud();
                if(curScore > bestScore)
                {
                    bestScore = curScore;
                }
                else 
                {
                    insrta(copy.getTour(),j,i);
                    copy.EvaluarAptitud();
                }
            }
        }
        return copy;
    }
    public Solucion linear2OPT(Solucion copy){ 
    copy.EvaluarAptitud(); 
    int bestScore = copy.getAptitud();
    //  System.out.println("INITIAL SCORE: " + bestScore); 
    int curScore = 0; 
    for(int i=0;i<48;i++){ 
        for(int j=0;j<48;j++){ 
                insrta(copy.getTour(),i,j); 
                copy.EvaluarAptitud();
                curScore = copy.getAptitud(); 
                if(curScore<bestScore){ 
                    bestScore=curScore; 
                    j = 0;
                } 
                else{ 
                    //Reverse changes 
                    insrta(copy.getTour(),j,i);   
                    copy.EvaluarAptitud();
                } 
            
        } 
    } 
    return copy; 
 } 

    private void insrta(Ciudad[] copy, int a, int b){ 
    temp[a] = copy[a];
    copy[a] = copy[b];
    copy[b] = temp[a];
 }  
}