
public class Ciudad {
    int lat;
    int lon;
    int id;
    int[] distancia;
    public Ciudad(int lon, int lat, int id, int[] distancia){
        this.lat = lat;
        this.lon = lon;
        this.id = id;
        this.distancia = distancia;
    }
    public Ciudad Copia(){
        return new Ciudad(lon, lat, id, distancia);
    }
    public int Distancia(int index){
        return distancia[index];
    }
    public static int Distancia(Ciudad c1,Ciudad c2){
        float distanciaX = Math.abs(c1.getLon() - c2.getLon());
        float distanciaY = Math.abs(c1.getLat() - c2.getLat());
        int distancia = (int)Math.sqrt( (distanciaX*distanciaX) + (distanciaY*distanciaY) );
        return distancia;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
