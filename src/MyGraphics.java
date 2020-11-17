//CMPT 275 Assignment 2
// Author: Chin Ho Wan 301308171
package A2;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class MyGraphics extends JPanel {
    // x,y is the coordinate for the cylinder
    // rect_x and rect_y is the coordinate for the rect(body) based on the size of the oval
    // l_x and l_y is the coordinate for the lower oval
    int x, y, rect_x, rect_y, l_x, l_y, hole_x, hole_y;
    // variables belows are the height and width for each shape
    int rect_height, rect_width, oval_width, oval_height, hole_width, hole_height;


    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        this.setBackground(Color.white);


        // Draw the rect
        g2D.setColor(new Color(51,105,255));
        g2D.fillRect(rect_x, rect_y, rect_width, rect_height);

        // Draw the lower oval
        g2D.setColor(new Color(51,105,255));
        g2D.fillOval(l_x, l_y, oval_width, oval_height);

        // Draw the upper oval
        g2D.setColor(new Color(51,153,255));
        g2D.fillOval(x, y, oval_width, oval_height);

        // Draw the hole
        g2D.setColor(Color.white);
        g2D.fillOval(hole_x,hole_y, hole_width, hole_height);

    }
}
