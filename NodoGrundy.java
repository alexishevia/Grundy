package grundy;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;

public class NodoGrundy {
    private boolean nivelPar;
    private ArrayList <Integer> pilas;
    private ArrayList <NodoGrundy> hijos;
    private Dimension costo;   // (x,y)  x=oportunidad que gane jug 1, y=oportunidad que gane jug2

    public NodoGrundy(ArrayList<Integer> pilas, boolean nivelPar) {
        this.pilas=pilas;
        this.nivelPar=nivelPar;
        hijos = new ArrayList<NodoGrundy>();
        generarHijos();
        calcularCosto();
    }


    private void calcularCosto() {
        if(hijos.size()==0) {  //Si no hay hijos
            if(nivelPar)
                costo = new Dimension(0,1); //Gana Jug2
            else
                costo = new Dimension(1,0); //Gana Jug1
        }
        else {
            int jug1, jug2;
            jug1 = jug2 = 0;
            for(int i=0; i<hijos.size(); i++) {
                jug1 += hijos.get(i).getCosto().getWidth();
                jug2 += hijos.get(i).getCosto().getHeight();
            }
            costo = new Dimension(jug1,jug2);
        }
    }

    private void generarHijos() {
        ArrayList<Integer> analizados = new ArrayList<Integer>();
        for(int i=0; i<pilas.size();i++) {
            if(!analizados.contains(pilas.get(i))) {
                int n=pilas.get(i);
                analizados.add(n);
                for(int a=n-1, b=1; a>b; a--, b++) {
                    ArrayList aux = new ArrayList();
                    aux.add(a);
                    aux.add(b);
                    for(int j=0;j<pilas.size();j++)
                        if(j!=i)
                        aux.add(pilas.get(j));
                    hijos.add(new NodoGrundy(aux,!nivelPar));
                }
            }
        }
    }

    @Override
    public String toString() {
        String str = new String("");
        //Impresion de Resultados
        str = str.concat("Impresión de nodo:");
        for(int i=0; i<pilas.size(); i++) {
            str = str.concat("\n\tpila["+i+"] = "+pilas.get(i));
        }
        str = str.concat("\n\tcosto= "+costo);
        return str;
    }

    public Dimension getCosto() {
        return costo;
    }

    public NodoGrundy avanzar(ArrayList<Integer> estadoSig) {
        int indice = 0;
        for(int i=0; i<hijos.size(); i++) {
            if(hijos.get(i).pilaIgual(estadoSig))
                indice = i;
        }
        return hijos.get(indice);
    }

    public NodoGrundy mejorHijo(boolean jug1) {
        NodoGrundy mejor = hijos.get(0);
        int chanceJug1 = (int) (mejor.getCosto().getWidth() - mejor.getCosto().getHeight()); //Encuentra que chances tiene el jug1 de ganar
        for(int i=1; i<hijos.size(); i++) {
            int c = (int) (hijos.get(i).getCosto().getWidth() - hijos.get(i).getCosto().getHeight());
            if((jug1 && c>chanceJug1) || (!jug1 && c<chanceJug1))
                mejor = hijos.get(i);
        }
        return mejor;
    }

    public ArrayList <Integer> getPilas() { return pilas; }

    private boolean pilaIgual(ArrayList<Integer> comp) {
        Collections.sort(comp);
        Collections.sort(pilas);
        if(comp.size()!=pilas.size())
            return false;
        boolean iguales = true;
        for(int i=0; i<comp.size();i++) {
            if(pilas.get(i)!=comp.get(i))
                iguales = false;
        }
        return iguales;
    }
}
