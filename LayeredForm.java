package grundy;
import javax.swing.*;

public class LayeredForm extends JLayeredPane {
    Jugador container;

    LayeredForm(int sub1, int sub2, Jugador div) {
        super();
        container = div;
        
        JLabel izq = new JLabel();
        izq.setBorder(BorderFactory.createTitledBorder("Sub-Pila 1"));
        izq.setBounds(0, 0, 360, 400);
        add(izq,new Integer(0));

        JLabel der = new JLabel();
        der.setBorder(BorderFactory.createTitledBorder("Sub-Pila 2"));
        der.setBounds(370, 0, 360, 400);
        add(der,new Integer(0));

        //Sub-Pila Izquierda
        JLabel cant1 = new JLabel(sub1+" monedas");
        cant1.setBounds(138, 400, 100, 20);
        add(cant1,new Integer(0));
        if(sub1>0) {
            MonedaFlotante mon1 = new MonedaFlotante(120,290,"gold",1,container);
            add(mon1,new Integer(1));
            for(int i=2;i<=sub1;i++) {
                MonedaFlotante mon2;
                if(i%2==0)
                    mon2 = new MonedaFlotante(120,310-i*20,"silver",1,container);
                else
                    mon2 = new MonedaFlotante(120,310-i*20,"gold",1,container);
                mon1.setSup(mon2);
                add(mon2,new Integer(i));
                mon1=mon2;
            }
        }

        //Sub-Pila Derecha
        JLabel cant2 = new JLabel(sub2+" monedas");
        cant2.setBounds(510, 400, 100, 20);
        add(cant2,new Integer(0));
        if(sub2>0) {
            MonedaFlotante mon1 = new MonedaFlotante(490,290,"gold",2,container);
            add(mon1,new Integer(1));
            for(int i=2;i<=sub2;i++) {
                MonedaFlotante mon2;
                if(i%2==0)
                    mon2 = new MonedaFlotante(490,310-i*20,"silver",2,container);
                else
                    mon2 = new MonedaFlotante(490,310-i*20,"gold",2,container);
                mon1.setSup(mon2);
                add(mon2,new Integer(i));
                mon1=mon2;
            }
        }

    }
}
