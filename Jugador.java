package grundy;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Jugador {
    protected Grundy programa;
    private boolean esPersona;
    private JFrame frame;
    private LayeredForm layeredPane;
    private int monedas; //Cantidad de monedas en la pila original
    private int subIzq; //Cantidad de monedas en pila izq
    private int subDer; //Cantidad de monedas en pila der

    public Jugador(Grundy prog, boolean esPersona) {
        programa = prog;
        this.esPersona = esPersona;
    }

    public boolean esPersona() {
        return esPersona;
    }

    public void jugar(int n) {
        programa.setVisible(false);
        subIzq = monedas = n;
        subDer=0;
        frame = new JFrame("Dividir Pila");
        frame.setLayout(null);
        frame.setSize(800, 620);
        frame.setResizable(false);
        frame.addWindowListener(
            new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    cancelar();
         }});

        JPanel instruct = new JPanel();
        instruct.setBorder(BorderFactory.createTitledBorder("Instrucciones"));
        instruct.add(new JLabel("Para dividir la pila arrastre las monedas de una subpila a otra."));
        instruct.setBounds(0, 5, 790, 75);
        frame.add(instruct);

        JPanel botones = new JPanel();
        JButton aceptar = new JButton("Aceptar");
        aceptar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                aceptar();
            }
        });

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        botones.add(aceptar); botones.add(cancelar);
        botones.setBounds(0, 520, 800, 45);
        frame.add(botones,BorderLayout.SOUTH);

        layeredPane = new LayeredForm(subIzq,subDer,this);
        layeredPane.setBounds(20, 100, 800, 440);
        frame.add(layeredPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update(int n) {     //n indica cuantas monedas hay que agregar a la pila izquierda
        subIzq += n;
        subDer -= n;
        update();
    }

    private void update() {
       frame.remove(layeredPane);
       layeredPane = new LayeredForm(subIzq,subDer,this);
       frame.add(layeredPane,BorderLayout.CENTER);
       layeredPane.setBounds(20, 100, 800, 440);
       frame.repaint();
    }

    private void aceptar() {
        if(subIzq==0 || subDer==0) JOptionPane.showMessageDialog(null, "No ha realizado ninguna división.");
        else if(subIzq==subDer) JOptionPane.showMessageDialog(null, "No se puede dividir una pila en dos sub-pilas iguales");
        else {
            frame.setVisible(false);
            programa.dividir(monedas, subIzq, subDer);
        }
    }

    private void cancelar() {
        frame.setVisible(false);
        programa.dividir();
    }

}
