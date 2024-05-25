package com.company;

import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class mazeWindow extends JFrame {

    public mazeWindow(Cell maze[][])
    {
        setVisible(true);
        setSize(2000,1000);
        setLayout(new GridLayout(20,40));
        for(Cell[] row: maze)
        {
            for (Cell cell : row)
            {
                String picType = "";
                if(cell.getStatus())
                    picType = "temps\\";
                else
                    picType = "picture\\";
                int numOpen = cell.getNumOpen();
                int rot = 0;
                String fileName = "";
                switch(numOpen) {
                    case 0:
                        fileName = "none.png";
                        break;
                    case 1:
                        fileName = "deadEnd.png";
                        rot = rotate(new boolean[]{false,false,true,false}, cell);
                        break;
                    case 2:
                        if(cell.getNorth() && cell.getSouth() || cell.getEast() && cell.getWest()) {
                            fileName = "straight.png";
                            rot = rotate(new boolean[]{true, false, true, false}, cell);
                        }
                        else
                        {
                            fileName = "corner.png";
                            rot = rotate(new boolean[]{false,true,true,false}, cell);
                        }
                        break;
                    case 3:
                        fileName = "t.png";
                        rot = rotate(new boolean[]{false,true,true,true}, cell);
                        break;
                    case 4:
                        fileName = "all.png";
                        break;
                }
                try
                {
                    BufferedImage img = ImageIO.read(new File(""+ picType + fileName));
                    int w = img.getWidth();
                    int h = img.getHeight();
                    BufferedImage temp = new BufferedImage(w,h,img.getType());
                    Graphics2D g2 = temp.createGraphics();
                    g2.rotate( Math.toRadians(rot), w/2.0, h/2.0 );
                    g2.drawImage(img, null, 0,0);
                    ImageIcon icon = new ImageIcon(temp);
                    JLabel image = new JLabel();
                    image.setIcon(icon);
                    add(image);
                }
                catch(Exception e)
                {
                    System.out.println("a");
                }
            }
        }
        setVisible(true);
    }

    public int rotate(boolean[] num, Cell cell)
    {
        boolean[] picArr= new boolean[]{cell.getNorth(), cell.getEast(), cell.getSouth(), cell.getWest()};
        int rotate = 0;
        while(!Arrays.equals(picArr, num))
        {
            boolean temp = picArr[3];
            for(int i =3; i > 0;i--)
            {
                picArr[i] = picArr[i-1];
            }
            picArr[0] = temp;
            rotate -= 90;
        }
        return rotate;
    }
}
