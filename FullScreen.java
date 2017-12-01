package main;

import java.awt.*;
import java.awt.Window;

import javax.swing.JFrame;

public class FullScreen {
	private GraphicsDevice gd;
	
	public FullScreen() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = env.getDefaultScreenDevice();
	}
	
	public void setFullScreen(DisplayMode dm, JFrame frm) {
		frm.setUndecorated(true);
		frm.setResizable(false);
		gd.setFullScreenWindow(frm);
		
		if (dm != null && gd.isDisplayChangeSupported()) {
			try {
				gd.setDisplayMode(dm);
			}
			catch (Exception ex) {}
		}
	}
	
	public Window getFullScreenWindow() {
		return gd.getFullScreenWindow();
	}
	
	public void restoreScreen() {
		Window win = gd.getFullScreenWindow();
		
		if (win != null) {
			win.dispose();
		}
		
		gd.setFullScreenWindow(null);
	}
}
