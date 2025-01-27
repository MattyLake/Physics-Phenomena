import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Stack;
import java.text.DecimalFormat;
/**
 * Matthew Lakin U6 V
 */
public class DoubleSlit extends JFrame {
    
    public static double SLITSEPARATION = 190; // All input / output values are in capital
    public static double DISTANCETOSCREEN = 200;
    public static double WAVELENGTH = 400;
    public static int ORDEROFNODE = 0;
    public static float FRINGESEPARATION = 0.0f;
    
    
    final public static double slitSize = 10; // Size of each slit (Default: 10)
    
    static double maxHeight; // Used to delete waves once they get too large
    static double maxHeightTop;
    static boolean maxHeightReached;
    static int speed = 10; // Speed of simulation
    static int lineWidth = 5; // Thickness of waves
    static int waveSpacing;
    
    static List<Integer> radiiBottom = new ArrayList<>();
    
    public int width = 727; // Frame size
    public int height = 700;
    
    static public int panelWidth = 400; // Simulation Panel size
    static public int panelHeight = 600;
    
    public int resultsWidth = 720; // Results Panel size
    public int resultsHeight = 60;
    
    JSlider slitSeparationSlider = new JSlider(); // Sliders instatiation
    JSlider distanceToScreenSlider = new JSlider();
    JSlider waveLengthOfWaveSlider = new JSlider();
    JSlider orderOfNodeSlider = new JSlider();
    
    protected float hue = 0f; // Used for HSB color of waves
    protected float saturation = 60f;
    protected float brightness = 80f;
    protected int intersectionSize = 10; // Radius of intersection point
    
    public Color backgroundColor = Color.decode("#000000"); // Defining all colors
    public Color UIColor = Color.decode("#4d4d4d");
    public Color lightColor;
    static Color rectColor = Color.decode("#666666");
    public Color textColor = Color.WHITE;
    public Color intersectionColor = Color.decode("#ffffff");
    
    public JLabel slitSeparationResultsName = new JLabel("Slit Separation (m)"); // Objects used in Controls Panel
    public JLabel slitSeparationResultsLabel = new JLabel("");
    
    public JLabel distanceToScreenResultsName = new JLabel("Distance to Screen (m)");
    public JLabel distanceToScreenResultsLabel = new JLabel("");
    
    public JLabel wavelengthResultsName = new JLabel("Wavelength (nm)");
    public JLabel wavelengthResultsLabel = new JLabel("");
    
    public JLabel orderOfWaveResultsName = new JLabel("Order of Wave");
    public JLabel orderOfWaveResultsLabel = new JLabel("");
    
    public JLabel fringeSeparationResultsName = new JLabel("Fringe Separation (m)");
    public JLabel fringeSeparationResultsLabel = new JLabel("");
    
    public Font resultsFont = new Font("Bahnschrift SemiBold", Font.PLAIN, 12); // Font used in Results Panel
    
    public DoubleSlit() {
        setLayout(new FlowLayout());
        setSize(new Dimension(width,height));
        setResizable(false);
        setTitle("Double Slit Simulator");
        getContentPane().setBackground(Color.WHITE);
        
        DoubleSlitSimulation panel = new DoubleSlitSimulation();
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
        add(panel);
        
        JPanel controls = new JPanel();
        controls.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        controls.setPreferredSize(new Dimension(300, 600));
        controls.setBackground(UIColor);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel slitSeparationLabel = new JLabel("Slit Separation",JLabel.CENTER);
        slitSeparationLabel.setForeground(textColor);
        slitSeparationLabel.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        c.weighty = 1;
        controls.add(slitSeparationLabel, c);
        slitSeparationSlider = new JSlider();
        slitSeparationSlider.setBackground(UIColor);
        slitSeparationSlider.setForeground(Color.WHITE);
        slitSeparationSlider.setUI(new ColouredNumbers());
        slitSeparationSlider.setMaximum(400);
        slitSeparationSlider.setMinimum(0);
        slitSeparationSlider.setMajorTickSpacing(40);
        slitSeparationSlider.setPaintTicks(true);
        slitSeparationSlider.setPaintLabels(true);
        slitSeparationSlider.setValue((int)(SLITSEPARATION));
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        controls.add(slitSeparationSlider, c);
        
        JLabel distanceToScreenLabel = new JLabel("Distance To Screen",JLabel.CENTER);
        distanceToScreenLabel.setForeground(textColor);
        distanceToScreenLabel.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0;
        c.weighty = 1;
        controls.add(distanceToScreenLabel, c);
        distanceToScreenSlider = new JSlider();
        distanceToScreenSlider.setBackground(UIColor);
        distanceToScreenSlider.setForeground(Color.WHITE);
        distanceToScreenSlider.setUI(new ColouredNumbers());
        distanceToScreenSlider.setMaximum(200);
        distanceToScreenSlider.setMinimum(100);
        distanceToScreenSlider.setMajorTickSpacing(10);
        distanceToScreenSlider.setPaintTicks(true);
        distanceToScreenSlider.setPaintLabels(true);
        distanceToScreenSlider.setValue((int)(DISTANCETOSCREEN));
        c.gridx = 0;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        controls.add(distanceToScreenSlider, c);
        
        JLabel waveLengthOfWaveLabel = new JLabel("Wavelength of Wave",JLabel.CENTER);
        waveLengthOfWaveLabel.setForeground(textColor);
        waveLengthOfWaveLabel.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0;
        c.weighty = 1;
        controls.add(waveLengthOfWaveLabel, c);
        waveLengthOfWaveSlider = new JSlider();
        waveLengthOfWaveSlider.setBackground(UIColor);
        waveLengthOfWaveSlider.setForeground(Color.WHITE);
        waveLengthOfWaveSlider.setUI(new ColouredNumbers());
        waveLengthOfWaveSlider.setMaximum(700);
        waveLengthOfWaveSlider.setMinimum(400);
        waveLengthOfWaveSlider.setMajorTickSpacing(100);
        waveLengthOfWaveSlider.setPaintTicks(true);
        waveLengthOfWaveSlider.setPaintLabels(true);
        waveLengthOfWaveSlider.setValue((int)(WAVELENGTH));
        hue = (float)((waveLengthOfWaveSlider.getValue()-700)/-3.75);
        waveSpacing = (15*(waveLengthOfWaveSlider.getValue()+40/3)/2)/50;
        Hashtable labelTable = new Hashtable();
        labelTable.put(new Integer( 700 ), new JLabel("700nm") ); 
        labelTable.put(new Integer( 600 ), new JLabel("600nm") );
        labelTable.put(new Integer( 500 ), new JLabel("500nm") );
        labelTable.put(new Integer( 400 ), new JLabel("400nm") );
        waveLengthOfWaveSlider.setLabelTable(labelTable);
        c.gridx = 0;
        c.gridy = 5;
        c.weightx = 1;
        c.weighty = 1;
        controls.add(waveLengthOfWaveSlider, c);
        
        JLabel orderOfNodeLabel = new JLabel("Order of Node",JLabel.CENTER);
        orderOfNodeLabel.setForeground(textColor);
        orderOfNodeLabel.setFont(new Font("Bahnschrift SemiBold", Font.PLAIN, 30));
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        c.weighty = 1;
        controls.add(orderOfNodeLabel, c);
        orderOfNodeSlider = new JSlider();
        orderOfNodeSlider.setBackground(UIColor);
        orderOfNodeSlider.setForeground(Color.WHITE);
        orderOfNodeSlider.setUI(new ColouredNumbers());
        orderOfNodeSlider.setMaximum(3);
        orderOfNodeSlider.setMinimum(0);
        orderOfNodeSlider.setMajorTickSpacing(1);
        orderOfNodeSlider.setPaintTicks(true);
        orderOfNodeSlider.setPaintLabels(true);
        orderOfNodeSlider.setValue((int)(ORDEROFNODE));
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 1;
        c.weighty = 1;
        controls.add(orderOfNodeSlider, c);
        
        add(controls);

        ResultsPanel resultsPanel = new ResultsPanel();
        add(resultsPanel);
        printResults();
        
        
        event1 slitSeparationChangeEvent = new event1();
        slitSeparationSlider.addChangeListener(slitSeparationChangeEvent);
        event2 distanceToScreenChangeEvent = new event2();
        distanceToScreenSlider.addChangeListener(distanceToScreenChangeEvent);
        event3 waveLengthOfWaveChangeEvent = new event3();
        waveLengthOfWaveSlider.addChangeListener(waveLengthOfWaveChangeEvent);
        event4 orderOfNodeChangeEvent = new event4();
        orderOfNodeSlider.addChangeListener(orderOfNodeChangeEvent);
    }
    public void printResults() { // Updates results Panel
        DecimalFormat df = new DecimalFormat("#.00000000000");
        // Below is calculation of Fringe Separation
        FRINGESEPARATION = (float)(WAVELENGTH*Math.pow(10d, -9d)*((panelHeight-DISTANCETOSCREEN)*Math.pow(10d, -3d))/SLITSEPARATION*Math.pow(10d, -3d))*1000;
        slitSeparationResultsLabel.setText(Double.toString(SLITSEPARATION));
        distanceToScreenResultsLabel.setText(Double.toString(panelHeight-DISTANCETOSCREEN));
        wavelengthResultsLabel.setText(Double.toString(WAVELENGTH));
        orderOfWaveResultsLabel.setText(Double.toString(ORDEROFNODE));
        fringeSeparationResultsLabel.setText(df.format(FRINGESEPARATION));
    }
    class ResultsPanel extends JPanel {
        public ResultsPanel() {
            setLayout(new GridLayout(2,5));
            setPreferredSize(new Dimension(resultsWidth, resultsHeight));
            setBackground(Color.decode("#161616"));
            
            slitSeparationResultsName.setFont(resultsFont); // Sets title font
            distanceToScreenResultsName.setFont(resultsFont);
            wavelengthResultsName.setFont(resultsFont);
            orderOfWaveResultsName.setFont(resultsFont);
            fringeSeparationResultsName.setFont(resultsFont);
            
            slitSeparationResultsName.setForeground(Color.WHITE); // Sets title color
            distanceToScreenResultsName.setForeground(Color.WHITE);
            wavelengthResultsName.setForeground(Color.WHITE);
            orderOfWaveResultsName.setForeground(Color.WHITE);
            fringeSeparationResultsName.setForeground(Color.WHITE);
            
            add(slitSeparationResultsName); // Adds title
            add(distanceToScreenResultsName);
            add(wavelengthResultsName);
            add(orderOfWaveResultsName);
            add(fringeSeparationResultsName);
            
            slitSeparationResultsLabel.setFont(resultsFont); // Sets results font
            distanceToScreenResultsLabel.setFont(resultsFont);
            wavelengthResultsLabel.setFont(resultsFont);
            orderOfWaveResultsLabel.setFont(resultsFont);
            fringeSeparationResultsLabel.setFont(resultsFont);
            
            slitSeparationResultsLabel.setForeground(Color.WHITE); // Sets results color
            distanceToScreenResultsLabel.setForeground(Color.WHITE);
            wavelengthResultsLabel.setForeground(Color.WHITE);
            orderOfWaveResultsLabel.setForeground(Color.WHITE);
            fringeSeparationResultsLabel.setForeground(Color.WHITE);
            
            add(slitSeparationResultsLabel); // Adds result
            add(distanceToScreenResultsLabel);
            add(wavelengthResultsLabel);
            add(orderOfWaveResultsLabel);
            add(fringeSeparationResultsLabel);
            
            printResults();
        }
    }
    class ColouredNumbers extends javax.swing.plaf.metal.MetalSliderUI { // Used for White dashes in sliders
        protected void paintHorizontalLabel(Graphics g,int value,Component label) {
          JLabel lbl = (JLabel)label;
          lbl.setForeground(Color.WHITE);
          super.paintHorizontalLabel(g,value,lbl);
        }
    }
    public class event1 implements ChangeListener { // Change in SlitSeparation Slider
        public void stateChanged(ChangeEvent slitSeparationChangeEvent) {
            SLITSEPARATION = slitSeparationSlider.getValue();
            printResults();
            repaint();
        }
    }
    public class event2 implements ChangeListener { // Change in DistanceToScreen Slider
        public void stateChanged(ChangeEvent distanceToScreenChangeEvent) {
            DISTANCETOSCREEN = distanceToScreenSlider.getValue();
            printResults();
            repaint();
        }
    }
    public class event3 implements ChangeListener { // Change in WaveLength Slider
        public void stateChanged(ChangeEvent waveLengthOfWaveChangeEvent) {
            hue = (float)((waveLengthOfWaveSlider.getValue()-700)/-3.75);
            waveSpacing = (15*(waveLengthOfWaveSlider.getValue()+40/3)/2)/50;
            WAVELENGTH = waveLengthOfWaveSlider.getValue();
            printResults();
            repaint();
        }
    }
    public class event4 implements ChangeListener { // Change in OrderOfNode Slider
        public void stateChanged(ChangeEvent orderOfNodeChangeEvent) {
            ORDEROFNODE = orderOfNodeSlider.getValue();
            printResults();
            repaint();
        }
    }
    public class DoubleSlitSimulation extends JPanel { // Simulation Panel
        ArrayList<TopWaves> radiiTop = new ArrayList<>();
        //ArrayList<Intersection> intersections = new ArrayList<>();
        Point wavePoint1 = new Point((int)(panelWidth-(SLITSEPARATION/2)-slitSize/2), (int)(panelHeight-DISTANCETOSCREEN)); // Where the slits are
        Point wavePoint2 = new Point((int)(panelWidth+(SLITSEPARATION/2)+slitSize/2), (int)(panelHeight-DISTANCETOSCREEN));
        public DoubleSlitSimulation() {
            maxHeightReached = false;
            ActionListener al = new ActionListener() {
                @Override // Swing timer for bottom waves
                public void actionPerformed(ActionEvent e) {
                    updateRadiiAndRepaintBottom();
                }    
            };
            Timer timer = new Timer(speed, al);
            
            setLayout(new FlowLayout());
            setVisible(true);
            
            timer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // ----------------------- Clone Graphics Objects ----------------------- //
            
            Graphics2D bottomWavesRender = (Graphics2D) g.create();
            Graphics2D topWavesRender = (Graphics2D) g.create();
            Graphics2D intersectionRender = (Graphics2D) g.create();
            Graphics2D slitRender = (Graphics2D) g.create();
            
            // ---------------------------- Anti-Aliasing --------------------------- //
            
            RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            bottomWavesRender.setRenderingHints(rh);
            topWavesRender.setRenderingHints(rh);
            intersectionRender.setRenderingHints(rh);
            
            // ----------------------------- Bottom Waves --------------------------- //
            
            bottomWavesRender.setStroke(new BasicStroke(lineWidth));
            bottomWavesRender.setColor(Color.getHSBColor(hue/100, saturation/100, brightness/100));
            
            for (Integer r : radiiBottom) {
                bottomWavesRender.drawLine(0, (int)((200-DISTANCETOSCREEN)+panelHeight-r), panelWidth, (int)((200-DISTANCETOSCREEN)+panelHeight-r));
            }
            
            // ----------------------------- Top Waves ------------------------------ //
            
            topWavesRender.setStroke(new BasicStroke(lineWidth));
            topWavesRender.translate(panelWidth/2,panelHeight-DISTANCETOSCREEN);
            topWavesRender.setColor(Color.getHSBColor(hue/100, saturation/100, brightness/100));
            for (TopWaves r : radiiTop) {
                if (SLITSEPARATION <= slitSize) {
                    renderWave(topWavesRender, 0, 0, r.getRadii());
                } else {
                    renderWave(topWavesRender, -SLITSEPARATION/2, 0, r.getRadii());
                    renderWave(topWavesRender, SLITSEPARATION/2, 0, r.getRadii());
                }
            }
            
            // ---------------------------- Intersections --------------------------- //
            
            //intersectionRender.setStroke(new BasicStroke(lineWidth));
            //intersectionRender.translate(panelWidth/2,panelHeight-DISTANCETOSCREEN);
            //intersectionRender.setColor(intersectionColor);

            //for (Intersection i : intersections) {
            //    if (!(SLITSEPARATION <= slitSize)) {
            //        renderIntersection(intersectionRender, i.getX(), -i.getY());
            //    }
            //}
            
            // -------------------------------- Slits ------------------------------- //
            
            Slit slit1 = new Slit(0, (int)(panelWidth/2-(SLITSEPARATION/2)-(slitSize/2)), (int)((200-DISTANCETOSCREEN)+wavePoint1.getY()));
            Slit slit2 = new Slit((int)(panelWidth/2-(SLITSEPARATION/2)+(slitSize/2)), (int)(panelWidth/2+(SLITSEPARATION/2)-(slitSize/2)), (int)((200-DISTANCETOSCREEN)+wavePoint1.getY()));
            Slit slit3 = new Slit((int)(panelWidth/2+(SLITSEPARATION/2)+(slitSize/2)), panelWidth, (int)((200-DISTANCETOSCREEN)+wavePoint1.getY()));
            
            slit1.drawMe(slitRender, rectColor);
            slit2.drawMe(slitRender, rectColor);
            slit3.drawMe(slitRender, rectColor);
        }
        
        @Override
        public Color getBackground() {
            return backgroundColor;
        }
        
        public void renderWave(Graphics2D g, double centreX, double centreY, double radius) {
            g.drawArc((int)(centreX-radius/2), (int)(centreY-radius/2), (int)(radius), (int)(radius), 0, 180);
        }
        
        //public void renderIntersection(Graphics2D g, double centreX, double centreY) {
        //    g.fillOval((int)(centreX-intersectionSize/2), (int)(((centreY-intersectionSize/2)/2)-2), intersectionSize, intersectionSize);
        //}
        
        public void updateRadiiAndRepaintBottom() {
            if (radiiBottom.isEmpty()) {
                radiiBottom.add(new Integer(1));
            } else {
                Stack<Integer> bottomToBeRemoved = new Stack<>();
                Stack<Integer> bottomToBeAdded = new Stack<>();
                maxHeight = (650/4)-lineWidth+2;
                for (int i = 0; i < radiiBottom.size(); i++) {
                    int radius = radiiBottom.get(i);
                    radius += 1;
                    radiiBottom.set(i, radius);
                    if (radius > maxHeight + lineWidth + waveSpacing - 5) {
                        bottomToBeRemoved.push(radius);
                    }
                    if (i == radiiBottom.size() - 1) {
                        if (radius > 2 * lineWidth + waveSpacing - 5) {
                           bottomToBeAdded.push(1);
                        }
                    }
                }
                radiiBottom.removeAll(bottomToBeRemoved);
                radiiBottom.addAll(bottomToBeAdded);
                if (maxHeight <= Collections.max(radiiBottom)-39) { // Lakin's Number: 39
                    maxHeightReached = true;
                    radiiTop.add(new TopWaves(0)); // Creates new top wave
                    radiiBottom.remove(0); // Deletes old bottom wave
                }
            }
            this.repaint();
        }
        
        public class TopWaves {
            Integer radii;
            Timer timer;
            boolean stop = false;
            public TopWaves(Integer radii) {
                this.radii = radii;
                maxHeightTop = 2*(panelHeight-DISTANCETOSCREEN+100);
                ActionListener al = new ActionListener() {
                    @Override // Swing timer for top waves
                    public void actionPerformed(ActionEvent e) {
                        updateRadiiAndRepaintTop();
                        //createIntersections();
                        if (stop) {
                            timer.stop();
                        }
                    }   
                };
                timer = new Timer(speed, al);
                timer.start();
            }
            public void updateRadiiAndRepaintTop() {
                radii += 2;
                int radTemp = radiiTop.get(0).getRadii();
                if (radTemp > maxHeightTop) {
                    radiiTop.remove(0);
                    stop = true;
                }
                repaint();
            }
            // public void createIntersections() {
            //     ArrayList<Intersection> intersectionsTemp1 = new ArrayList<>();
            //     intersections.clear();
            //     intersectionsTemp1 = findIntersections(radiiTop, ORDEROFNODE, SLITSEPARATION);
            //     if (intersectionsTemp1.size() > 0) {
            //         intersections.add(intersectionsTemp1.get(0));
            //         intersections.add(intersectionsTemp1.get(1));
            //         intersectionsTemp1.clear();
            //     }
            // }
            // public ArrayList<Intersection> findIntersections(ArrayList<TopWaves> radiiList, int order, double ss) {
            //     ArrayList<Intersection> intersectionsTemp = new ArrayList<>();
            //     ArrayList<Integer> radii = new ArrayList<>();
            //     int offset = waveSpacing/2;
            //     for (TopWaves r : radiiList) {
            //         radii.add(r.getRadii());
            //     }
            //     for (Integer r : radii) {
            //         double x = ((-2 * order * offset) * ((-3 * order * offset) + ( 2 * r))) / (4 * ss);
            //         double y = (Math.sqrt(Math.pow(r,2)-Math.pow((x-ss),2)))-50*order;
            //         if (Double.isNaN(y) || y == 0) {
            //             continue;
            //         } else {
            //             intersectionsTemp.add(new Intersection(x, y));
            //             intersectionsTemp.add(new Intersection(-x, y));
            //         }
            //     }
            //     return intersectionsTemp;
            // }
            public Integer getRadii() {
                return radii;
            }
        }
        
        // public class Intersection { // Point class for awt basically
        //     private double x;
        //     private double y;
        //     public Intersection(double x, double y) {
        //         this.x = x;
        //         this.y = y;
        //     }
        //     public double getX() {
        //         return x;
        //     }
        //     public double getY() {
        //         return y;
        //     }
        // }
    }
}
