package alggen_viajero;

import java.util.Collections;
import java.util.Arrays;

public class Capitales {
    
    Capital [] c = new Capital[48];
    int n [] = new int[48];
    int optimo [] = {1,8,38,31,44,18,7,28,6,37,19,27,17,43,30,36,46,33,20,47,21,32,39,48,5,42,24,10,45,35,4,26,2,29,34,41,16,22,3,23,14,25,13,11,12,15,40,9};
    
    public Capitales(){
        c[0] = new Capital(0,6734,1453);
        c[1] = new Capital(1,2233,10);
        c[2] = new Capital(2,5530,1424);
        c[3] = new Capital(3,401,841);
        c[4] = new Capital(4,3082,1644);
        c[5] = new Capital(5,7608,4458);
        c[6] = new Capital(6,7573,3716);
        c[7] = new Capital(7,7265,1268);
        c[8] = new Capital(8,6898,1885);
        c[9] = new Capital(9,1112,2049);
        c[10] = new Capital(10,5468,2606);
        c[11] = new Capital(11,5989,2873);
        c[12] = new Capital(12,4706,2674);
        c[13] = new Capital(13,4612,2035);
        c[14] = new Capital(14,6347,2683);
        c[15] = new Capital(15,6107,669);
        c[16] = new Capital(16,7611,5184);
        c[17] = new Capital(17,7462,3590);
        c[18] = new Capital(18,7732,4723);
        c[19] = new Capital(19,5900,3561);
        c[20] = new Capital(20,4483,3369);
        c[21] = new Capital(21,6101,1110);
        c[22] = new Capital(22,5199,2182);
        c[23] = new Capital(23,1633,2809);
        c[24] = new Capital(24,4307,2322);
        c[25] = new Capital(25,675,1006);
        c[26] = new Capital(26,7555,4819);
        c[27] = new Capital(27,7541,3981);
        c[28] = new Capital(28,3177,756);
        c[29] = new Capital(29,7352,4506);
        c[30] = new Capital(30,7545,2801);
        c[31] = new Capital(31,3245,3305);
        c[32] = new Capital(32,6426,3173);
        c[33] = new Capital(33,4608,1198);
        c[34] = new Capital(34,23,2216);
        c[35] = new Capital(35,7248,3779);
        c[36] = new Capital(36,7762,4595);
        c[37] = new Capital(37,7392,2244);
        c[38] = new Capital(38,3484,2829);
        c[39] = new Capital(39,6271,2135);
        c[40] = new Capital(40,4985,140);
        c[41] = new Capital(41,1916,1569);
        c[42] = new Capital(42,7280,4899);
        c[43] = new Capital(43,7509,3239);
        c[44] = new Capital(44,10,2676);
        c[45] = new Capital(45,6807,2993);
        c[46] = new Capital(46,5185,3258);
        c[47] = new Capital(47,3023,1942);
        
       

    }
    public int[] Optimo(){
        for (int i = 0; i < n.length ; i++) {
            optimo[i] = optimo[i] -1;
        }
        return optimo;
    }
    
    public int[] Revolver() {
        Collections.shuffle(Arrays.asList(c)); 
         for (int i = 0; i < n.length ; i++) {
            n[i] = c[i].n;
        }
        return n;
    }
    
}
