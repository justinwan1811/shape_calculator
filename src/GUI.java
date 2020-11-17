//CMPT 275 Assignment 2
// Author: Chin Ho Wan 301308171
package A2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class GUI implements ActionListener {
    private static JLabel h_label, thick_label, r_label, res_label, fres_label;
    private static JTextField h_text, thick_text, r_text;
    private static JButton cal_button;
    private static JLabel h_error, thick_error, r_error, error;
    private static JFrame frame;
    private static JPanel panel;
    private static  MyGraphics g;

    public static void window(){

        frame = new JFrame("Part Calculator");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Create a 2*1 grid panel
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(1, 2));

        // Panel for user input
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel.add(panel1);

        // Create label, text field, unit label for height, thickness, hole radius,
        // result and error message
        h_label = new JLabel("Enter height (m):");
        h_label.setBounds(20,20,120,25);
        panel1.add(h_label);
        h_text = new JTextField(30);
        h_text.setBounds(160, 20, 165, 25);
        panel1.add(h_text);

        h_error = new JLabel("");
        h_error.setBounds(160, 40, 250, 25);
        h_error.setForeground(Color.RED);
        panel1.add(h_error);

        thick_label = new JLabel("Enter thickness (m):");
        thick_label.setBounds(20,100,120,25);
        panel1.add(thick_label);
        thick_text = new JTextField(30);
        thick_text.setBounds(160, 100, 165, 25);
        panel1.add(thick_text);

        thick_error = new JLabel("");
        thick_error.setBounds(160, 120, 250, 25);
        thick_error.setForeground(Color.RED);
        panel1.add(thick_error);


        r_label = new JLabel("Enter hole radius (m):");
        r_label.setBounds(20,180,160,25);
        panel1.add(r_label);
        r_text = new JTextField(30);
        r_text.setBounds(160, 180, 165, 25);
        panel1.add(r_text);

        r_error = new JLabel("");
        r_error.setBounds(160, 200, 250, 25);
        r_error.setForeground(Color.RED);
        panel1.add(r_error);

        cal_button = new JButton("Calculate");
        cal_button.setBounds(20, 260, 100, 25);
        cal_button.addActionListener(new GUI());
        panel1.add(cal_button);

        res_label = new JLabel("Needed Liquid (m^3):");
        res_label.setBounds(20,320,160,25);
        panel1.add(res_label);
        Border border = LineBorder.createBlackLineBorder();
        fres_label = new JLabel("");
        fres_label.setBounds(20,345,160,25);
        fres_label.setOpaque(true);
        fres_label.setBackground(Color.white);
        fres_label.setBorder(border);
        panel1.add(fres_label);

        error = new JLabel("");
        error.setBounds(130,260,160,25);
        error.setForeground(Color.RED);
        panel1.add(error);

        g = new MyGraphics();
        panel.add(g);

        frame.setVisible(true);
    }

    @Override
    //Function for actionPerformed (Calculate button)
    public void actionPerformed(ActionEvent e) {
        //Reset all the error message
        h_error.setText("");
        thick_error.setText("");
        r_error.setText("");
        error.setText("");

        // the flag to see if all input pass the validation
        boolean pass_flag = check_value();

        if (pass_flag) {

            // Getting inputs from the textField
            String h = h_text.getText();
            double height = Double.parseDouble(h);
            String thick = thick_text.getText();
            double thickness = Double.parseDouble(thick);
            String r = r_text.getText();
            double hole_r = Double.parseDouble(r);

            // Using function calculate_volume()
            float result = calculate_volume(height, thickness, hole_r);

            // Display the result to the fres_label
            String final_result = Float.toString(result);
            fres_label.setText(final_result);

            int x = 100;
            g.x = x;
            int y = 100;
            g.y = y;
            height = height * 300;
            thickness = thickness * 300;
            hole_r = hole_r * 300;
            // Draw the cylinder with 2 ovals (upper and lower), rect as body, 1 oval as hole
            // Using ratio 1:3 for the oval and calculate the corresponding pixel

            // Resizing all input in case the cylinder is too larger
            // Divide every input by 2, until it's within 300 * 300 pixels size
            double tem = (thickness + hole_r) * 2;
            if(tem > 300 || height > 300 ){
                while (tem > 300 || height > 300) {
                    tem = tem / 2 ;
                    height = height / 2;
                    thickness = thickness / 2;
                    hole_r = hole_r / 2;
                }
            }
            // Resizing all input in case the cylinder is too small
            if(tem < 100 || height < 100 ){
                while (tem < 100 && height < 100) {
                    tem = tem * 2;
                    height = height * 2;
                    thickness = thickness * 2;
                    hole_r = hole_r * 2;
                }
            }

            // Everything after this point is within 300 * 300 pixels size
            double temp1 = Math.round((thickness + hole_r) * 2);

            int oval_width = (int) temp1;
            int oval_height = (int) temp1 / 3;

            // Using ratio 1:3 for the hole and calculate the corresponding pixel
            double temp2 = Math.round(hole_r * 2);
            int hole_width = (int) temp2;
            int hole_height = (int) temp2 / 3;

            int rect_width = oval_width;
            int rect_height = (int) Math.round(height);
            int rect_x = x;
            int rect_y = y + (oval_height / 2);

            g.oval_height = oval_height;
            g.oval_width = oval_width;
            g.rect_height = rect_height;
            g.rect_width = rect_width;
            g.hole_height = hole_height;
            g.hole_width = hole_width;
            g.rect_x = rect_x;
            g.rect_y = rect_y;
            g.l_x = x;
            g.l_y = rect_y + rect_height - (oval_height / 2);
            g.hole_x = x + (oval_width / 2) - (hole_width / 2);
            g.hole_y = y + (oval_height / 2) - (hole_height / 2);

            // Display the resized-cylinder to the UI (update)
            panel.repaint();

            error.setForeground(Color.BLACK);
            error.setText("Submit Successfully!");
        }
        else
        {
            error.setForeground(Color.RED);
            error.setText("Submit failed!");
        }
    }

    // Function for input validations (all inputs)
    public static boolean check_value() {
        boolean pass_flag = true;

        //Validation for h_value
        String h = h_text.getText();
        double height;
        try {
            height = Double.parseDouble(h);
            //check if the input is out of range
            if (height <= 0 ) {
                h_error.setText("Error: Input need to be larger than 0!");
                pass_flag = false;
            }
        } catch (Exception error) {
            if (h.length() == 0) {
                h_error.setText("Empty input!");
            } else {
                h_error.setText("Error: Invalid Input!");
            }
            pass_flag = false;
        }

        //Validation for thick_value
        String thick = thick_text.getText();
        double thickness;
        try {
            thickness = Double.parseDouble(thick);
            //check if the input is out of range
            if (thickness <= 0) {
                thick_error.setText("Error: Input need to be larger than 0!");
                pass_flag = false;
            }
        } catch (Exception error) {
            if (thick.length() == 0) {
                thick_error.setText("Empty input!");
            } else {
                thick_error.setText("Error: Invalid Input!");
            }
            pass_flag = false;
        }

        //Validation for hole_r
        String r = r_text.getText();
        double hole_r;
        try {
            hole_r = Double.parseDouble(r);
            //check if the input is out of range
            if (hole_r <= 0) {
                r_error.setText("Error: Input need to be larger than 0!");
                pass_flag = false;
            }
        } catch (Exception error) {
            if (r.length() == 0) {
                r_error.setText("Empty input!");
            } else {
                r_error.setText("Error: Invalid Input!");
            }
            pass_flag = false;
        }
        return pass_flag;
    }

    // Function for calculating the needed liquid to fill the part (the blue part)
    public static float calculate_volume(double height, double thickness, double hole_r){
        double out_cyl = Math.PI * Math.pow((hole_r+thickness), 2) * height;
        double in_cyl = Math.PI * Math.pow(hole_r, 2) * height;
        double result = out_cyl - in_cyl;
        return (float)result;
    }

}

