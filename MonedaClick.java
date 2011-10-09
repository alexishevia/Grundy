package grundy;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MonedaClick extends MonedaLabel {
    Jugador jug; //Almacena el jugador en turno
    int pila; //Almacena la cantidad de monedas en la pila

   MonedaClick(int x, int y, String color, Jugador jugador, int npila) {
       super(x,y,color,false);
       jug = jugador;
       pila = npila;

       addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) { if(jug.esPersona()) jug.jugar(pila); }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
            });
   }

}