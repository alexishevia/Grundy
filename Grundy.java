package grundy;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Grundy {
    Grundy programa;
    private JFrame frame;
    private boolean turno;      //True = Jug1, False = Jug2
    private Jugador jug1, jug2, jugTurno;
    private ArrayList<Integer> pilasDiv;
    private ArbolGrundy arbol;
    private boolean arbolNeeded,fin; //Se necesita construir el arbol?
    int jugadas1, jugadas2, contador;

    public Grundy() {
       programa=this;
       fin=false;
       leerIniciales();
    }

    public void setVisible(boolean vis) {
      frame.setVisible(vis);
    }

    private void nuevo() {
        setVisible(false);
        programa=new Grundy();
    }

    private void leerIniciales() {
        JLabel lmonedas, ljug1, ljug2;
        JRadioButton rbper1, rbper2, rbpc1, rbpc2;
    	JButton baceptar;
    	final ButtonGroup bgjug1, bgjug2;
    	JPanel pjug1, pjug2;
    	final SpinnerNumberModel model;
    	JSpinner spinner;
        final JFrame fopciones;

        int initial=3, min=3, max=15, increment=1; contador=0;
    	model = new SpinnerNumberModel( initial, min, max, increment );
    	spinner = new JSpinner(model);

    	fopciones = new JFrame("Opciones");
    	fopciones.setSize(200,200);
    	fopciones.setLayout(new FlowLayout());
    	fopciones.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
    	fopciones.setResizable(false);
    	fopciones.setLocationRelativeTo(null);

    	lmonedas = new JLabel("Cant. de Monedas:");
    	ljug1 = new JLabel("Jugador 1:");
    	ljug2 = new JLabel("Jugador 2:");

    	bgjug1 = new ButtonGroup();
    	bgjug2 = new ButtonGroup();

    	rbper1 = new JRadioButton("Persona", true);
    	rbpc1 = new JRadioButton("PC");
    	rbper2 = new JRadioButton("Persona", true);
    	rbpc2 = new JRadioButton("PC");

    	pjug1 = new JPanel();
    	pjug2 = new JPanel();

    	pjug1.setLayout(new GridLayout(2,1));
    	pjug2.setLayout(new GridLayout(2,1));

    	bgjug1.add(rbper1);
    	bgjug1.add(rbpc1);
    	bgjug2.add(rbper2);
    	bgjug2.add(rbpc2);

    	rbper1.setActionCommand("Persona");
    	rbpc1.setActionCommand("PC");
    	rbper2.setActionCommand("Persona");
    	rbpc2.setActionCommand("PC");

    	pjug1.add(rbper1);
    	pjug1.add(rbpc1);
    	pjug2.add(rbper2);
    	pjug2.add(rbpc2);

    	fopciones.getContentPane().add(lmonedas);
    	fopciones.getContentPane().add(spinner);
    	fopciones.getContentPane().add(ljug1);
    	fopciones.add(pjug1);
      	fopciones.getContentPane().add(ljug2);
    	fopciones.add(pjug2);

		baceptar = new JButton("Aceptar");
  		fopciones.getContentPane().add(baceptar);
    	baceptar.setMnemonic(KeyEvent.VK_A);
    	fopciones.setVisible(true);

    	baceptar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
            turno=true;
            pilasDiv = new ArrayList<Integer>();
            pilasDiv.add(model.getNumber().intValue());
            String aux = bgjug1.getSelection().getActionCommand();
            if(aux.compareToIgnoreCase("persona")==0)
                jug1 = new Jugador(programa,true);
            else {
                jug1 = new Jugador(programa,false);
                arbolNeeded = true;
            }
            aux = bgjug2.getSelection().getActionCommand();
            if(aux.compareToIgnoreCase("persona")==0)
                jug2 = new Jugador(programa,true);
            else {
                jug2 = new Jugador(programa,false);
                arbolNeeded = true;
            }
            fopciones.setVisible(false);
            if(arbolNeeded) arbol = new ArbolGrundy(pilasDiv);
            iniciar();
       }});
    }

    private void iniciar() {
       frame=new JFrame("Grundy");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       /*** Construccion del Menu ***/
       //Boton Nuevo
       JMenuItem nuevo = new JMenuItem("Nuevo");
       nuevo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { nuevo(); }
       });
       //Boton Salir
       JMenuItem salir = new JMenuItem("Salir");
       salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { System.exit(0); }
       });
       //Menu Juego
       JMenu menu = new JMenu("Juego");
       menu.add(nuevo); menu.add(salir);

       //Boton Instrucciones
       JMenuItem instruct = new JMenuItem("Instrucciones");
       instruct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { instrucciones(); }
       });
       //Boton Acerca de
       JMenuItem acerca = new JMenuItem("Acerca de...");
       acerca.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) { acerca(); }
       });
       //Menu Ayuda
       JMenu ayuda = new JMenu("Ayuda");
       ayuda.add(instruct); ayuda.add(acerca);

       //MenuBar
       JMenuBar menubar = new JMenuBar();
       menubar.add(menu); menubar.add(ayuda);
       frame.setJMenuBar(menubar);
       /*** Fin Construccion del Menu ***/

       construirTablero();
    }

    private void construirTablero() {
        Collections.sort(pilasDiv);

       if(turno) jugTurno = jug1;
       else jugTurno = jug2;

       construir();

       if (!fin && !jugTurno.esPersona()) {
           Thread thread = new Pausa(this);
           thread.start();
       }
    }

    private void construir() {

       JPanel panelPrin = new JPanel();
       panelPrin.setLayout(null);

       int nmax=pilasDiv.get(pilasDiv.size()-1); //Pila con mayor cantidad de monedas
       int posx=0;
       int panelx = pilasDiv.size()*120+(pilasDiv.size()-1)*35+20; //Tamaño del Panel en X
       if(panelx<350) { posx=(350-panelx)/2; panelx=350; }
       int panely = (nmax-1)*20+200; //Tamaño del Panel en Y

       //Posicionar pilas en pantalla
       for(int i=pilasDiv.size()-1;i>=0;i--) {
            int ni = pilasDiv.get(i);
            int n=nmax-ni;
            boolean par = true;
            String color;
            int posy=25;
            
            for(int j=0;j<ni;j++) {
                if(par) color = "gold"; else color = "silver";
                panelPrin.add(new MonedaClick(10+posx,n*20+30+posy,color,jugTurno,ni));
                par = !par;
                posy+=20;
            }
            JLabel text = new JLabel("(  "+ni+"  )");
            text.setBounds(posx+50,panely-60, 50, 50);
            panelPrin.add(text);
            posx+=150;
        }

       JLabel instruct;
       String mensaje;
       fin = pilasDiv.get(pilasDiv.size()-1)<=2;

       if(fin) {
           if(turno)
                mensaje = new String("Fin del partido. El jugador 2 ha ganado. \nCantidad de Jugadas: "+contador);
           else
               mensaje = new String("Fin del partido. El jugador 1 ha ganado. \nCantidad de Jugadas: "+contador);
           instruct = new JLabel(mensaje);
           instruct.setBounds((panelx-400)/2, 0, 400, 50);
       }

       else {
           //Si es turno de la PC
           if(!jugTurno.esPersona()) {
               if(turno)
                    mensaje = new String("                                 Jugador 1: PC");
               else 
                    mensaje = new String("                                 Jugador 2: PC");
           }
           //Si es turno de Persona
           else {
                if(turno)
                    mensaje = new String("Jugador 1: Hacer click sobre la pila que desee dividir.");
                else
                    mensaje = new String("Jugador 2: Hacer click sobre la pila que desee dividir.");
           }
            instruct = new JLabel(mensaje);
            instruct.setBounds((panelx-330)/2, 0, 330, 50);
       }

       panelPrin.add(instruct);

        panelPrin.setPreferredSize(new Dimension(panelx,panely));
        JScrollPane scroll = new JScrollPane(panelPrin);
        frame.getContentPane().removeAll();
        frame.getContentPane().add(scroll);
        frame.pack();
        frame.setLocationRelativeTo(null);
        setVisible(true);
        
        if(fin) JOptionPane.showMessageDialog(null, mensaje);
    }

    public void jugarPC(){
        contador++;
        arbol.mejorHijo(turno);
        turno = !turno;
        pilasDiv = arbol.getCabeza().getPilas();
        construirTablero();
    }

    public void dividir(int pila, int subpila1, int subpila2) {
        System.out.println(contador);
        contador++;
        System.out.println(contador);
        pilasDiv.remove(pilasDiv.indexOf(pila));
        pilasDiv.add(subpila1);
        pilasDiv.add(subpila2);
        if(arbolNeeded)arbol.avanzar(pilasDiv);
        turno = !turno;
        construirTablero();
    }

    public void dividir() {
        construirTablero();
    }

     private void instrucciones() {
    	final URL url;
    	final ImageIcon icon;

    	url =  getClass().getResource("imagenes1/InstruccionesGrundy.jpg");
    	icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(url));
     	JLabel image = new JLabel(icon);
     	JScrollPane pinstrucciones = new JScrollPane(image);
     	JDialog dinstrucciones = new JDialog();
        dinstrucciones.add(pinstrucciones);
        dinstrucciones.pack();
        dinstrucciones.setLocationRelativeTo(null);
     	dinstrucciones.setVisible(true);
     	}

    private void acerca() {
    	JOptionPane.showMessageDialog(frame,
    	"Creado Por:\n"
        + "Alexis Hevia\n"
        + "Goldie Bravo\n"
    	+ "Luis Moreno\n"
    	+ "Osmany Gonzalez\n",
    	"Acerca de Grundy",JOptionPane.INFORMATION_MESSAGE);
    	}
}
