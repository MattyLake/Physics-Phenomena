import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * Matthew Lakin U6 V
 */
public class Menu extends JFrame {
    private JLabel top;
    private JButton doubleSlit;
    
    private static final int SS_MIN = 0;
    private static final int SS_MAX = 30;
    private static final int SS_INIT = 15;
    private JSlider slitSeparationSlider;
    
    protected Refraction refractionSim;
    protected DoubleSlit doubleSlitSim;
    public static Mechanics mechanicsCalc;
    
    private JButton refraction;
    
    private JButton mechanics;
    
    private JButton quit;
    
    protected Color backgroundColor = Color.decode("#4D4D4D");
    public Menu() {
        setTitle("Menu");
        setSize(500,900);
        setVisible(true);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backgroundColor);
        
        Border border = new LineBorder(Color.WHITE, 4);
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.weightx = 3;
        c.weighty = 3;
        
        top = new JLabel("<html><div text-align:center>Physics Phenomena Calculator" + "<br/>Main Menu</div></html>");
        top.setFont(new Font("Bahnschrift SemiBold", Font.BOLD, 30));
        top.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 0;
        add(top, c);
        
        doubleSlit = new JButton("<html><div text-align:center>Double Slit Interference" + "<br/>Simulator</div></html>");
        doubleSlit.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 24));
        doubleSlit.setPreferredSize(new Dimension(400,120));
        doubleSlit.setBackground(Color.decode("#ffb3b3"));
        doubleSlit.setBorder(border);
        c.gridx = 0;
        c.gridy = 1;
        add(doubleSlit, c);
        
        refraction = new JButton("Refraction Simulator");
        refraction.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 24));
        refraction.setPreferredSize(new Dimension(400,120));
        refraction.setBackground(Color.decode("#c6ffb3"));
        refraction.setBorder(border);
        c.gridx = 0;
        c.gridy = 2;
        add(refraction, c);
        
        mechanics = new JButton("Mechanics Calculator");
        mechanics.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 24));
        mechanics.setPreferredSize(new Dimension(400,120));
        mechanics.setBackground(Color.decode("#b3d9ff"));
        mechanics.setBorder(border);
        c.gridx = 0;
        c.gridy = 3;
        add(mechanics, c);
        
        quit = new JButton("Quit");
        quit.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 24));
        quit.setPreferredSize(new Dimension(400,120));
        quit.setBackground(Color.decode("#d9d9d9"));
        quit.setBorder(border);
        c.gridx = 0;
        c.gridy = 4;
        add(quit, c);
        
        event1 clickDoubleSlitEvent = new event1();
        doubleSlit.addActionListener(clickDoubleSlitEvent);
        
        event2 clickRefractionEvent = new event2();
        refraction.addActionListener(clickRefractionEvent);
        
        event3 clickMechanicsEvent = new event3();
        mechanics.addActionListener(clickMechanicsEvent);
        
        event4 clickQuitEvent = new event4();
        quit.addActionListener(clickQuitEvent);
    }
    
    public static void main(String[] args) throws Exception {
        Runnable runMe = new Runnable() {
            @Override
            public void run() {
                new Menu();
            }
        };
        SwingUtilities.invokeLater(runMe);
    }
    
    public class event1 implements ActionListener {
        public void actionPerformed(ActionEvent clickDoubleSlitEvent) {
            DoubleSlit doubleSlitSim = new DoubleSlit();
            doubleSlitSim.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            doubleSlitSim.setTitle("Double Slit Simulator");
            doubleSlitSim.setVisible(true);
        }
    }
    public class event2 implements ActionListener {
        public void actionPerformed(ActionEvent clickRefractionEvent) {
            refractionSim = new Refraction();
            refractionSim.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            refractionSim.setSize(Refraction.width, Refraction.height);
            refractionSim.setTitle("Refraction Simulator");
            refractionSim.setVisible(true);
        }
    }
    public class event3 implements ActionListener {
        public void actionPerformed(ActionEvent clickMechanicsEvent) {
            mechanicsCalc = new Mechanics();
            mechanicsCalc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            mechanicsCalc.setSize(Mechanics.width, Mechanics.height);
            mechanicsCalc.setTitle("Mechanics Calculator");
            mechanicsCalc.pack();
            mechanicsCalc.setVisible(true);
        }
    }
    public class event4 implements ActionListener {
        public void actionPerformed(ActionEvent clickQuitEvent) {
            System.exit(0);
        }
    }
}