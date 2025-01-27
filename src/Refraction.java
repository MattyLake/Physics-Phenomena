import java.util.Hashtable;
import java.lang.Math.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.text.DecimalFormat;
/**
 * Matthew Lakin U6 V
 */
public class Refraction extends JFrame {
    static int width = 400, height = 600;
   
    protected Color backgroundColor = Color.decode("#4D4D4D");
    
    DecimalFormat df = new DecimalFormat("#.0");
    protected RefractionPanel refractionSim;
    
    protected static double REFRACTIVE_INDEX_1 = 1.000293; // AIR DEFAULT: 1.000293
    protected static double REFRACTIVE_INDEX_2 = 1.52; // GLASS DEFAULT: 1.52
    protected static double THETA1 = 0; // DEFAULT LIGHT ANGLE TO NORMAL - DEFAULT: 0
    protected static double THETA2; // REFRACTED LIGHT ANGLE TO NORMAL
    
    float hue = 310; //hue of materials Default = 180
    float saturation = 0f; //saturation of materials Default = 0.1f
    
    protected JSlider refractiveIndex1Slider; // Instantiate Sliders
    protected JSlider refractiveIndex2Slider;
    protected JSlider theta1Slider;
    
    protected static JLabel refractiveIndex1Label; // Instantiate Labels
    protected static JLabel refractiveIndex2Label;
    protected static JLabel theta1Label;
    
    public Refraction() {
        setResizable(false);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        RefractionPanel refractionSim = new RefractionPanel();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        add(refractionSim, c);
        
        JPanel controls = new JPanel();
        controls.setBackground(backgroundColor);
        controls.setLayout(new GridBagLayout());
        
        refractiveIndex1Label = new JLabel("Refractive Index on Left = "+REFRACTIVE_INDEX_1);
        refractiveIndex1Label.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        controls.add(refractiveIndex1Label, c);
        
        refractiveIndex1Slider = new JSlider();
        refractiveIndex1Slider.setMaximum(50000000);
        refractiveIndex1Slider.setMinimum(10000000);
        refractiveIndex1Slider.setMajorTickSpacing(10000000);
        refractiveIndex1Slider.setMinorTickSpacing(2500000);
        refractiveIndex1Slider.setPaintTicks(true);
        refractiveIndex1Slider.setPaintLabels(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer( 10000000 ), new JLabel("1") );  // Use of hashing to represent decimal values with integers
        labelTable.put(new Integer( 20000000 ), new JLabel("2") );
        labelTable.put(new Integer( 30000000 ), new JLabel("3") );
        labelTable.put(new Integer( 40000000 ), new JLabel("4") );
        labelTable.put(new Integer( 50000000 ), new JLabel("5") );
        refractiveIndex1Slider.setLabelTable(labelTable);
        refractiveIndex1Slider.setUI(new ColouredNumbers());
        refractiveIndex1Slider.setForeground(Color.WHITE);
        refractiveIndex1Slider.setBackground(backgroundColor);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 0;
        c.gridwidth = 2;
        controls.add(refractiveIndex1Slider, c);
                
        refractiveIndex2Label = new JLabel("Refractive Index on Right = "+REFRACTIVE_INDEX_2);
        refractiveIndex2Label.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        controls.add(refractiveIndex2Label, c);
        
        refractiveIndex2Slider = new JSlider();
        refractiveIndex2Slider.setMaximum(50000000);
        refractiveIndex2Slider.setMinimum(10000000);
        refractiveIndex2Slider.setMajorTickSpacing(10000000);
        refractiveIndex2Slider.setMinorTickSpacing((int)2500000);
        refractiveIndex2Slider.setPaintTicks(true);
        refractiveIndex2Slider.setPaintLabels(true);
        labelTable.put(new Integer( 10000000 ), new JLabel("1") );  // Use of hashing to represent decimal values with integers
        labelTable.put(new Integer( 20000000 ), new JLabel("2") );
        labelTable.put(new Integer( 30000000 ), new JLabel("3") );
        labelTable.put(new Integer( 40000000 ), new JLabel("4") );
        labelTable.put(new Integer( 50000000 ), new JLabel("5") );
        refractiveIndex2Slider.setLabelTable(labelTable);
        refractiveIndex2Slider.setUI(new ColouredNumbers());
        refractiveIndex2Slider.setForeground(Color.WHITE);
        refractiveIndex2Slider.setBackground(backgroundColor);
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 2;
        c.weighty = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        controls.add(refractiveIndex2Slider, c);
        
        theta1Label = new JLabel("Angle of incident light = "+THETA1+"°"+"      Angle of Refracted Light = "+df.format((Math.asin(REFRACTIVE_INDEX_1*Math.sin(THETA1*(Math.PI/180)))/REFRACTIVE_INDEX_2)*(45/2*Math.PI))+"°");
        theta1Label.setForeground(Color.WHITE);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        controls.add(theta1Label, c);
        
        theta1Slider = new JSlider();
        theta1Slider.setMaximum(900);
        theta1Slider.setMinimum(-900);
        theta1Slider.setMajorTickSpacing(300);
        theta1Slider.setMinorTickSpacing(100);
        theta1Slider.setPaintTicks(true);
        theta1Slider.setPaintLabels(true);
        labelTable.put(new Integer( -900 ), new JLabel("-90") );  // Use of hashing to represent decimal values with integers
        labelTable.put(new Integer( -600 ), new JLabel("-60") );
        labelTable.put(new Integer( -300 ), new JLabel("-30") );
        labelTable.put(new Integer( 0 ), new JLabel("0") );
        labelTable.put(new Integer( 300 ), new JLabel("30") );
        labelTable.put(new Integer( 600 ), new JLabel("60") );
        labelTable.put(new Integer( 900 ), new JLabel("90") );
        theta1Slider.setLabelTable(labelTable);
        theta1Slider.setUI(new ColouredNumbers());
        theta1Slider.setForeground(Color.WHITE);
        theta1Slider.setBackground(backgroundColor);
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 2;
        c.weighty = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        controls.add(theta1Slider, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(controls, c);
        
        refractiveIndex1Slider.setValue((int)(REFRACTIVE_INDEX_1*10000000)); // Accounting for hashing
        refractiveIndex2Slider.setValue((int)(REFRACTIVE_INDEX_2*10000000));
        theta1Slider.setValue((int)(THETA1*10));
        
        event1 sliderChangeEvent = new event1();
        refractiveIndex1Slider.addChangeListener(sliderChangeEvent);
        refractiveIndex2Slider.addChangeListener(sliderChangeEvent);
        theta1Slider.addChangeListener(sliderChangeEvent);
    }
   
    class ColouredNumbers extends javax.swing.plaf.metal.MetalSliderUI {  // Used for White dashes in sliders
        protected void paintHorizontalLabel(Graphics g,int value,Component label) {
          JLabel lbl = (JLabel)label;
          lbl.setForeground(Color.WHITE);
          super.paintHorizontalLabel(g,value,lbl);
        }
    }
    
    public class event1 implements ChangeListener { // Change in any slider
        public void stateChanged(ChangeEvent sliderChangeEvent) {
            Refraction.setRefractiveIndex1((double)refractiveIndex1Slider.getValue()/10000000);
            Refraction.setRefractiveIndex2((double)refractiveIndex2Slider.getValue()/10000000);
            Refraction.setTheta1((double)theta1Slider.getValue()/10);
            
            refractiveIndex1Label.setText("Refractive Index on Left = "+Refraction.REFRACTIVE_INDEX_1);
            refractiveIndex2Label.setText("Refractive Index on Right = "+Refraction.REFRACTIVE_INDEX_2);
            if (Double.isNaN(THETA2)) {
                theta1Label.setText("Angle of incident light = "+THETA1+"°"+"      Angle of Refracted Light = "+"Invalid");
            } else {
                theta1Label.setText("Angle of incident light = "+THETA1+"°"+"      Angle of Refracted Light = "+df.format(THETA2*(180/Math.PI))+"°");
            }
            
            
                
            repaint();
        }
    }
    
    public class RefractionPanel extends JPanel {
        int width = 400, height = 400;
        public RefractionPanel() {
            setLayout(new FlowLayout());
            setPreferredSize(new Dimension(width, height));
        }
        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create(); // Clone graphics object
           
            RenderingHints rh = new RenderingHints( // Anti-aliasing
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHints(rh);
           
           
            g2d.setStroke(new BasicStroke(1));
           
            float brightness = (float)(1-(-30+REFRACTIVE_INDEX_1)/6); //brightness
            Color myRGBColor = Color.getHSBColor(hue/360, saturation, brightness);
            g2d.setColor(myRGBColor);
            g2d.fillRect(0,0,width/2,height);
           
            brightness = (float)(1-(-30+REFRACTIVE_INDEX_2)/6); //brightness
            myRGBColor = Color.getHSBColor(hue/360, saturation, brightness);
            g2d.setColor(myRGBColor);
            g2d.fillRect(width/2,0,width, height);
           
            g2d.setStroke(new BasicStroke(4));
            
            double startY;
            if(THETA1 >= 90) {
                startY = 1000000; // Big Number for Straight Line
            } else if (THETA1 <= -90) {
                startY = -1000000; // Big Number for Straight Line
            } else {
                startY = 200 + width/2 * Math.tan((THETA1*(Math.PI/180)));
            }
            THETA2 = Math.asin(REFRACTIVE_INDEX_1*Math.sin(THETA1*(Math.PI/180)))/REFRACTIVE_INDEX_2; // Using equation
            if (Double.isNaN(THETA2)) {
                double endY = 400-startY;
                g2d.setColor(Color.RED);
                g2d.drawLine(width/2, height/2, 0, (int)endY);
            } else {
                g2d.setColor(Color.WHITE);
                double endY = 200 + -200 * Math.tan(THETA2);
                g2d.drawLine(width/2, height/2, width, (int)endY);
            }
            g2d.setColor(Color.WHITE);
            g2d.drawLine(0, (int)startY, width/2, height/2);
        }
    }
    
    
    public static void setRefractiveIndex1 (double n) {
        REFRACTIVE_INDEX_1 = n;
    }
    
    public static void setRefractiveIndex2 (double n) {
        REFRACTIVE_INDEX_2 = n;
    }
    
    public static void setTheta1 (double n) {
        THETA1 = n;
    }
    
    @Override
    public Dimension preferredSize() {
        return new Dimension(width,height);
    }
}
