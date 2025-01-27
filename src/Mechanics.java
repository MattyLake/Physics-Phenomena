import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
/**
 * Matthew Lakin U6 V
 */
public class Mechanics extends JFrame {
    public static int width = 600, height = 250;
    
    
    // ArrayList indexes -> 0 = displacement, 1 = initial velocity, 2 = final velocity, 3 = acceleration, 4 = time
    // This is true for all arraylists
    public static ArrayList<JLabel> textLabels = new ArrayList<>();
    public static ArrayList<JTextField> textFields = new ArrayList<>();
    public static ArrayList<CheckBox> calculatingCheckBoxes = new ArrayList<>();
    public static ArrayList<CheckBox> missingCheckBoxes = new ArrayList<>();
    
    protected JButton calculate;
    
    public Color backgroundColor = Color.decode("#666666");
    public Color inputColor = Color.decode("#b3b3b3");
    public Color foregroundColor;
    public Font universalFont = new Font("Bahnschrift SemiBold", Font.PLAIN, 15);
    
    float saturation = 0.6f;
    float brightness = 1f;
    
    public Mechanics() {
        setResizable(false);
        UIManager.put("TabbedPane.selected", Color.decode("#808080"));
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        
        getContentPane().setBackground(Color.decode("#1a1a1a"));
        
        SUVAT suvatWindow = new SUVAT();
        ProjectileMotion projMotionWindow = new ProjectileMotion();
        
        tabbedPane.addTab("SUVAT", null, suvatWindow, "Calculate SUVAT");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        tabbedPane.addTab("Proj. Motion", null, projMotionWindow, "Simulate Projectile Motion");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        tabbedPane.setBackground(Color.decode("#333333"));
        tabbedPane.setForeground(Color.decode("#ffffff"));
        
        Container contentPane = getContentPane();
        contentPane.setBackground(backgroundColor);
        contentPane.setForeground(backgroundColor);
        
        contentPane.add(tabbedPane);
    }
    
    public static void calculate(ArrayList<SUVATValue> list) {
        double s = list.get(0).getValue();
        double u = list.get(1).getValue();
        double v = list.get(2).getValue();
        double a = list.get(3).getValue();
        double t = list.get(4).getValue();
        
        
        String sType = list.get(0).getType();
        String uType = list.get(1).getType();
        String vType = list.get(2).getType();
        String aType = list.get(3).getType();
        String tType = list.get(4).getType();
        
        
        if (sType.equals("C")) {
            double sResult = 0;
            if (uType.equals("M")) {
                sResult = (v * t) - (0.5 * a * t * t);
            } else if (vType.equals("M")) {
                sResult = (u * t) + (0.5 * a * t * t);
            } else if (aType.equals("M")) {
                sResult = (t / 2) * (u + v) ;
            } else if (tType.equals("M")) {
                sResult = ((u * u) - (v * v)) / (2 * a);
            }
            textFields.get(0).setText(String.valueOf(Math.abs(sResult)));
        } else if (uType.equals("C")) {
            double uResult = 0;
            if (sType.equals("M")) {
                uResult = v - (a * t);
            } else if (vType.equals("M")) {
                uResult = ((2 * s) - (a * Math.pow(t, 2))) / (2 * t);
            } else if (aType.equals("M")) {
                uResult = ((2 * s) / t) - v;
            } else if (tType.equals("M")) {
                uResult = Math.sqrt((2 * a * s) - (v * v));
            }
            textFields.get(1).setText(String.valueOf(uResult));
        } else if (vType.equals("C")) {
            double vResult = 0;
            if (sType.equals("M")) {
                vResult = u + (a * t);
            } else if (uType.equals("M")) {
                vResult = ((2 * s) + (a * t * t)) / (2 * t);
            } else if (aType.equals("M")) {
                vResult = ((2 * s) / t) - u;
            } else if (tType.equals("M")) {
                vResult = Math.sqrt((u * u) + (2 * a * s));
            }
            textFields.get(2).setText(String.valueOf(vResult));
        } else if (aType.equals("C")) {
            double aResult = 0;
            if (sType.equals("M")) {
                aResult = (v - u) / t;
            } else if (uType.equals("M")) {
                aResult = (2 * ((v * t) - s)) / (t * t);
            } else if (vType.equals("M")) {
                aResult = (2 * (s - (u * t))) / (t * t);
            } else if (tType.equals("M")) {
                aResult = ((v * v) - (u * u)) / (2 * s);
            }
            textFields.get(3).setText(String.valueOf(aResult));
        } else if (tType.equals("C")) {
            double tResult = 0;
            if (sType.equals("M")) {
                tResult = (v - u) / a;
            } else if (uType.equals("M")) {
                tResult = (v - Math.sqrt((v * v) - 2 * a * s)) / a;
            } else if (vType.equals("M")) {
                tResult = (Math.sqrt((2 * a * s) - u * u) - u) / a;
            } else if (aType.equals("M")) {
                tResult = (2 * s) / (u + v);
            }
            textFields.get(4).setText(String.valueOf(Math.abs(tResult)));
        }
    }
    
    
    public class SUVATValue {
        private String type;
        private double value;
        public SUVATValue(String type, double value) {
            this.type = type;
            this.value = value;
        }
        public String getType() {
            return type;
        }
        public double getValue() {
            return value;
        }
    }
    public class CheckBox extends JCheckBox { // JCheckBox with polymorphism
        private String type;
        private String attribute;
        public CheckBox(String type, String attribute) {
            // Use of ternary operator to pass first input to parent class
            super(type.equals("M") ? "Missing this Value?" : "Calculating this Value?");
            this.attribute = attribute;
        }
    }
    public class SUVAT extends JPanel {
        public SUVAT() {
            setLayout(new GridBagLayout());
            setBackground(backgroundColor);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            createObjects(); // Store objects in ArrayLists for later iteration
            c.weighty = 0;
            c.weightx = 0;
            c.ipadx = 10;
            for (int i = 0 ; i < 5 ; i++) { // Add Text Labels (x = 0)
                c.gridx = 0;
                c.gridy = i;
                foregroundColor = getForegroundColor(i);
                textLabels.get(i).setForeground(foregroundColor);
                textLabels.get(i).setBackground(backgroundColor);
                textLabels.get(i).setFont(universalFont);
                add(textLabels.get(i), c);
            }
            c.weighty = 1;
            c.weightx = 1;
            c.ipadx = 0;
            for (int i = 0 ; i < 5 ; i++) { // Add Text Fields (x = 1)
                c.gridx = 1;
                c.gridy = i;
                foregroundColor = getForegroundColor(i);
                textFields.get(i).setForeground(foregroundColor);
                textFields.get(i).setBackground(inputColor);
                textFields.get(i).setFont(universalFont);
                add(textFields.get(i), c);
            }
            c.weighty = 0;
            c.weightx = 0;
            for (int i = 0 ; i < 5 ; i++) { // Add Calculating Check Boxes (x = 2)
                c.gridx = 2;
                c.gridy = i;
                foregroundColor = getForegroundColor(i);
                calculatingCheckBoxes.get(i).setForeground(foregroundColor);
                calculatingCheckBoxes.get(i).setBackground(backgroundColor);
                calculatingCheckBoxes.get(i).setFont(universalFont);
                add(calculatingCheckBoxes.get(i), c);
            }
            for (int i = 0 ; i < 5 ; i++) { // Add Missing Check Boxes (x = 3)
                c.gridx = 3;
                c.gridy = i;
                foregroundColor = getForegroundColor(i);
                missingCheckBoxes.get(i).setForeground(foregroundColor);
                missingCheckBoxes.get(i).setBackground(backgroundColor);
                missingCheckBoxes.get(i).setFont(universalFont);
                add(missingCheckBoxes.get(i), c);
            }
            calculate = new JButton("Calculate");
            c.gridx = 0;
            c.gridy = 5;
            c.gridwidth = 4;
            calculate.setBackground(inputColor);
            calculate.setForeground(Color.WHITE);
            calculate.setFont(universalFont);
            add(calculate, c);
            
            // Calculating Check Box Item Listeners //
            
            eventCS checkCSBox = new eventCS();
            calculatingCheckBoxes.get(0).addItemListener(checkCSBox);
            
            eventCU checkCUBox = new eventCU();
            calculatingCheckBoxes.get(1).addItemListener(checkCUBox);
            
            eventCV checkCVBox = new eventCV();
            calculatingCheckBoxes.get(2).addItemListener(checkCVBox);
            
            eventCA checkCABox = new eventCA();
            calculatingCheckBoxes.get(3).addItemListener(checkCABox);
            
            eventCT checkCTBox = new eventCT();
            calculatingCheckBoxes.get(4).addItemListener(checkCTBox);

            // Missing Check Box Item Listeners //
            
            eventMS checkMSBox = new eventMS();
            missingCheckBoxes.get(0).addItemListener(checkMSBox);
            
            eventMU checkMUBox = new eventMU();
            missingCheckBoxes.get(1).addItemListener(checkMUBox);
            
            eventMV checkMVBox = new eventMV();
            missingCheckBoxes.get(2).addItemListener(checkMVBox);
            
            eventMA checkMABox = new eventMA();
            missingCheckBoxes.get(3).addItemListener(checkMABox);
            
            eventMT checkMTBox = new eventMT();
            missingCheckBoxes.get(4).addItemListener(checkMTBox);

            // Calculate Button Action Listener //
            
            eventB buttonClickEvent = new eventB();
            calculate.addActionListener(buttonClickEvent);
        }
        @Override
        public Dimension preferredSize() {
            return new Dimension(width, height);
        }
        
        public Color getForegroundColor(float i) { // Creates the rainbow text color
            return Color.getHSBColor(i/5f, saturation, brightness);
        }
        
        public void createObjects() {
            textLabels.add(new JLabel("Displacement (m)"));
            textLabels.add(new JLabel("Initial Velocity (m/s)"));
            textLabels.add(new JLabel("Final Velocity (m/s)"));
            textLabels.add(new JLabel("Acceleration (m/s²)"));
            textLabels.add(new JLabel("Time (s)"));
            
            for (int i = 0 ; i < 5 ; i++) {
                textFields.add(new JTextField());
            }
            
            calculatingCheckBoxes.add(new CheckBox("C", "S")); // Use of the same class with different results
            calculatingCheckBoxes.add(new CheckBox("C", "U"));
            calculatingCheckBoxes.add(new CheckBox("C", "V"));
            calculatingCheckBoxes.add(new CheckBox("C", "A"));
            calculatingCheckBoxes.add(new CheckBox("C", "T"));
            
            missingCheckBoxes.add(new CheckBox("M", "S"));
            missingCheckBoxes.add(new CheckBox("M", "U"));
            missingCheckBoxes.add(new CheckBox("M", "V"));
            missingCheckBoxes.add(new CheckBox("M", "A"));
            missingCheckBoxes.add(new CheckBox("M", "T"));
        }
        
    
       
        
        public class eventCS implements ItemListener { // WORK OUT FOR DISPLACEMENT
            public void itemStateChanged(ItemEvent checkCSBox){
                boolean status = !(checkCSBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(0).setEnabled(status);
                textFields.get(0).setText(null);
                calculatingCheckBoxes.get(1).setEnabled(status);
                calculatingCheckBoxes.get(2).setEnabled(status);
                calculatingCheckBoxes.get(3).setEnabled(status);
                calculatingCheckBoxes.get(4).setEnabled(status);
                missingCheckBoxes.get(0).setEnabled(status);
            }
        }
    
        public class eventCU implements ItemListener { // WORK OUT FOR INITIAL VELOCITY
            public void itemStateChanged(ItemEvent checkCUBox){
                boolean status = !(checkCUBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(1).setEnabled(status);
                textFields.get(1).setText(null);
                calculatingCheckBoxes.get(0).setEnabled(status);
                calculatingCheckBoxes.get(2).setEnabled(status);
                calculatingCheckBoxes.get(3).setEnabled(status);
                calculatingCheckBoxes.get(4).setEnabled(status);
                missingCheckBoxes.get(1).setEnabled(status);
            }
        }
        
        public class eventCV implements ItemListener { // WORK OUT FOR FINAL VELOCITY
            public void itemStateChanged(ItemEvent checkCVBox){
                boolean status = !(checkCVBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(2).setEnabled(status);
                textFields.get(2).setText(null);
                calculatingCheckBoxes.get(0).setEnabled(status);
                calculatingCheckBoxes.get(1).setEnabled(status);
                calculatingCheckBoxes.get(3).setEnabled(status);
                calculatingCheckBoxes.get(4).setEnabled(status);
                missingCheckBoxes.get(2).setEnabled(status);
            }
        }
        
        public class eventCA implements ItemListener { // WORK OUT FOR ACCELERATION
            public void itemStateChanged(ItemEvent checkCABox){
                boolean status = !(checkCABox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(3).setEnabled(status);
                textFields.get(3).setText(null);
                calculatingCheckBoxes.get(0).setEnabled(status);
                calculatingCheckBoxes.get(1).setEnabled(status);
                calculatingCheckBoxes.get(2).setEnabled(status);
                calculatingCheckBoxes.get(4).setEnabled(status);
                missingCheckBoxes.get(3).setEnabled(status);
            }
        }
        
        public class eventCT implements ItemListener { // WORK OUT FOR TIME
            public void itemStateChanged(ItemEvent checkCTBox){
                boolean status = !(checkCTBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(4).setEnabled(status);
                textFields.get(4).setText(null);
                calculatingCheckBoxes.get(0).setEnabled(status);
                calculatingCheckBoxes.get(1).setEnabled(status);
                calculatingCheckBoxes.get(2).setEnabled(status);
                calculatingCheckBoxes.get(3).setEnabled(status);
                missingCheckBoxes.get(4).setEnabled(status);
            }
        }
        
        public class eventMS implements ItemListener { // MISSING DISPLACEMENT
            public void itemStateChanged(ItemEvent checkMSBox){
                boolean status = !(checkMSBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(0).setEnabled(status);
                textFields.get(0).setText(null);
                missingCheckBoxes.get(1).setEnabled(status);
                missingCheckBoxes.get(2).setEnabled(status);
                missingCheckBoxes.get(3).setEnabled(status);
                missingCheckBoxes.get(4).setEnabled(status);
                calculatingCheckBoxes.get(0).setEnabled(status);
            }
        }
        
        public class eventMU implements ItemListener { // MISSING INITIAL DISPLACEMENT
            public void itemStateChanged(ItemEvent checkMUBox){
                boolean status = !(checkMUBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(1).setEnabled(status);
                textFields.get(1).setText(null);
                missingCheckBoxes.get(0).setEnabled(status);
                missingCheckBoxes.get(2).setEnabled(status);
                missingCheckBoxes.get(3).setEnabled(status);
                missingCheckBoxes.get(4).setEnabled(status);
                calculatingCheckBoxes.get(1).setEnabled(status);
            }
        }
        
        public class eventMV implements ItemListener { // MISSING FINAL DISPLACEMENT
            public void itemStateChanged(ItemEvent checkMVBox){
                boolean status = !(checkMVBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(2).setEnabled(status);
                textFields.get(2).setText(null);
                missingCheckBoxes.get(0).setEnabled(status);
                missingCheckBoxes.get(1).setEnabled(status);
                missingCheckBoxes.get(3).setEnabled(status);
                missingCheckBoxes.get(4).setEnabled(status);
                calculatingCheckBoxes.get(2).setEnabled(status);
            }
        }
        
        public class eventMA implements ItemListener { // MISSING ACCELERATION
            public void itemStateChanged(ItemEvent checkMABox){
                boolean status = !(checkMABox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(3).setEnabled(status);
                textFields.get(3).setText(null);
                missingCheckBoxes.get(0).setEnabled(status);
                missingCheckBoxes.get(1).setEnabled(status);
                missingCheckBoxes.get(2).setEnabled(status);
                missingCheckBoxes.get(4).setEnabled(status);
                calculatingCheckBoxes.get(3).setEnabled(status);
            }
        }
        
        public class eventMT implements ItemListener { // MISSING TIME
            public void itemStateChanged(ItemEvent checkMTBox){
                boolean status = !(checkMTBox.getStateChange() == ItemEvent.SELECTED);
                textFields.get(4).setEnabled(status);
                textFields.get(4).setText(null);
                missingCheckBoxes.get(0).setEnabled(status);
                missingCheckBoxes.get(1).setEnabled(status);
                missingCheckBoxes.get(2).setEnabled(status);
                missingCheckBoxes.get(3).setEnabled(status);
                calculatingCheckBoxes.get(4).setEnabled(status);
            }
        }
        
        public class eventB implements ActionListener {
            public void actionPerformed(ActionEvent buttonClickEvent) {
                boolean isTimePositive = true; // All conditions must stay true for valid calculation
                boolean bothCheckBoxesUsed = true;
                boolean threeTextFieldsUsed = true;
                boolean noStringsUsed = true;
                boolean noBlankField = true;
                boolean valid = true;
                
                ArrayList<SUVATValue> choices = new ArrayList<>();
                
                // Time Positive Validation //
                try {
                    if (!(calculatingCheckBoxes.get(4).isSelected() || missingCheckBoxes.get(4).isSelected())) {
                        if (Double.valueOf(textFields.get(4).getText()) < 0) {
                            isTimePositive = false;
                        } else {
                            isTimePositive = true;
                        }
                    }
                } catch (Exception e) {
                    if (!(missingCheckBoxes.get(4).isSelected() || calculatingCheckBoxes.get(4).isSelected())) {
                        noBlankField = false;
                    }
                }
                
                // Both Checkboxes Used Validation //
                int checkBoxCount = 0;
                for (int i = 0 ; i < 5 ; i++) {
                    if (calculatingCheckBoxes.get(i).isSelected()) {
                        checkBoxCount++;
                    }
                }
                for (int i = 0 ; i < 5 ; i++) {
                    if (missingCheckBoxes.get(i).isSelected()) {
                        checkBoxCount++;
                    }
                }
                if (checkBoxCount == 2) {
                    bothCheckBoxesUsed = true;
                } else {
                    bothCheckBoxesUsed = false;
                }
                
                // Three Text Fields Used Validation //
                int usedTextFieldCount = 0;
                for (int i = 0 ; i < 5 ; i++) {
                    if (!textFields.get(i).getText().equals("")) {
                        usedTextFieldCount++;
                    }
                }
                if(usedTextFieldCount == 3) {
                    threeTextFieldsUsed = true;
                } else {
                    threeTextFieldsUsed = false;
                }
                
                // No Strings Used Validation //
                for (int i = 0 ; i < 5 ; i++) {
                    if (!isNumeric(textFields.get(i).getText())) {
                        if (!(missingCheckBoxes.get(i).isSelected() || calculatingCheckBoxes.get(i).isSelected())) {
                            noStringsUsed = false;
                        }
                    }
                }
                
                String result = "";
                if (!isTimePositive) {
                    result += "Time Negative\n";
                    valid = false;
                }
                if (!bothCheckBoxesUsed) {
                    result += "Check box used incorrectly\n";
                    valid = false;
                }
                if (!threeTextFieldsUsed) {
                    result += "Incorrect values entered\n";
                    valid = false;
                }
                if (!noStringsUsed) {
                    result += "Only decimal values allowed\n";
                    valid = false;
                }
                if (!noBlankField) {
                    result += "Enter all values\n";
                    valid = false;
                }
                
                if (valid) { // Passed all Validation
                    for (int i = 0 ; i < 5 ; i++) {
                        if (missingCheckBoxes.get(i).isSelected()) {
                            choices.add(new SUVATValue("M", 0)); // Missing Text Field
                        } else if (calculatingCheckBoxes.get(i).isSelected()) {
                            choices.add(new SUVATValue("C", 0)); // Calculating Text Field (Where answer will be)
                        } else {
                            choices.add(new SUVATValue("B", Double.valueOf(textFields.get(i).getText()))); // Data text Field
                        }
                    }
                    calculate(choices);
                } else {
                    JOptionPane.showMessageDialog(Menu.mechanicsCalc, result, "ERROR Message", JOptionPane.ERROR_MESSAGE); // Error message pop-up
                }
            }
            
            public boolean isNumeric(String strNum) {
                if (strNum == "") {
                    return false;
                }
                try {
                    double d = Double.parseDouble(strNum);
                } catch (NumberFormatException nfe) {
                    return false; // If error, must be non-numeric
                }
                return true;
            }
        }
    }
    
    public class ProjectileMotion extends JPanel {
        public ProjectileMotion() {
            try {
               BufferedImage myPicture = ImageIO.read(this.getClass().getResource("projmot.jpg"));
               JLabel picLabel = new JLabel(new ImageIcon(myPicture));
               add(picLabel);
            }  catch (Exception e) {}
        }
    }
}

