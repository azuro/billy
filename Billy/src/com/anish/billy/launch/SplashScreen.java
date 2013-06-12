package com.anish.billy.launch;

import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.File;

public class SplashScreen {

	    static JFrame f = new JFrame("Billy - Starting Up");
	    static void Show(){
	       f.setUndecorated(true);
	        JLabel ImageLabel = new JLabel(new ImageIcon("Library"+File.separator+"SplashScreen.png"));
	       f.getContentPane().add(ImageLabel);
	       f.pack();
	       f.setResizable(false);
	       f.setLocationRelativeTo(null);
	       f.setVisible(true);
	    }
	    public static void terminate(){
	        f.dispose();
	    }

}
