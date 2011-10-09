package grundy;
import java.util.ArrayList;

public class ArbolGrundy {
    private NodoGrundy cabeza;
    
    public ArbolGrundy(ArrayList<Integer> estadoIni) {
        cabeza = new NodoGrundy(estadoIni,true);
    }

    public void avanzar(ArrayList<Integer> estadoSig) {
        cabeza = cabeza.avanzar(estadoSig);
    }

    public void mejorHijo(boolean jug1) {
        cabeza = cabeza.mejorHijo(jug1);
    }

    public NodoGrundy getCabeza() {
        return cabeza;
    }
}
