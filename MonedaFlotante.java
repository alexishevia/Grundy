package grundy;

public class MonedaFlotante extends MonedaLabel {
    int pila; //Almacena si se encuentra en la pila izquierda (1) o derecha (2)
    Jugador container;

    MonedaFlotante(int x, int y, String color, int p, Jugador div) {
        super(x,y,color);
        pila = p;
        container = div;
    }

    @Override
    protected void colocar(int x, int y) {
        if(pila==1&&getX()>325&&getY()>-40&&getY()<280) {
            int n = contar();
            n = n*-1;
            container.update(n);
        }
        else if(pila==2&&getX()<260&&getY()>-20&&getY()<280) {
            int n = contar();
            container.update(n);
        }
        else super.colocar(x,y);
    }
 
}
