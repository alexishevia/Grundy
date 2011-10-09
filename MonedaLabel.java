package grundy;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import javax.swing.*;

public class MonedaLabel extends JLabel {
    private final MonedaLabel moneda;
    protected MonedaLabel superior;
    private final int dist = 20; //Distancia entre una moneda y otra
    private final URL url;
    private final ImageIcon icon;
    private final int width;
    private final int height;
    private int origenX;
    private int origenY;

    MonedaLabel(int x, int y, String color, boolean draggable) {
        super();
        superior=null;
        if(color.compareToIgnoreCase("gold")==0)
            url =  getClass().getResource("imagenes1/coin_gold.png");
        else
            url =  getClass().getResource("imagenes1/coin_silver.png");
        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
        width = icon.getIconWidth();
        height = icon.getIconHeight();
        setIcon(icon);
        this.setSize(width, height);
        moneda = this;
        origenX = x; origenY = y;
        colocar(x,y);
        if(draggable) {
            addMouseMotionListener(new MouseMotionListener() {
                public void mouseDragged(MouseEvent e) {
                    moneda.desplazar(e.getX(),e.getY());
                }
            public void mouseMoved(MouseEvent e) { }
            });
            addMouseListener(new MouseListener() {
                public void mouseClicked(MouseEvent e) {}
                public void mousePressed(MouseEvent e) { }
                public void mouseReleased(MouseEvent e) {
                    moneda.colocar(origenX, origenY);
                }
                public void mouseEntered(MouseEvent e) { }
                public void mouseExited(MouseEvent e) { }
            });
        }
    }

    MonedaLabel(int x, int y, String color) {
        this(x,y,color,true);
    }

    public void colocar() {
        colocar(origenX, origenY);
    }

    private void desplazar(int deltaX, int deltaY) {
        setLocation(getX()+deltaX-width/2,getY()+deltaY-height+5);
        if(superior!=null)
            superior.desplazar(deltaX, deltaY);
    }

    protected void colocar(int x, int y) {
        setLocation(origenX,origenY);
        if(superior!=null)
            superior.colocar(x, y);
    }

   public void setSup(MonedaLabel sup) {
        superior = sup;
    }

    public int contar() {
        if(superior==null)
             return 1;
        else
            return 1+superior.contar();
    }

}
