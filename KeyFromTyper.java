package main;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class KeyFromTyper implements KeyListener {
	
	private JTextField text;
	private JTextArea area;
	private JTextArea inventory;
	private WorldGen map;
	private Player player;
	private JLabel enemy, pl;
	private JTextArea inv;
	private JFrame win;
	private JPanel main, start;
	
	private int commLoc = 0;
	
	private boolean held = false;
	
	private List<String> comm = new ArrayList<String>();
	
	public KeyFromTyper(JTextField text, JTextArea area, JTextArea inventory, WorldGen map, Player player, JTextArea inv, List<String> comm, JFrame win, JPanel main, JPanel start) {
		super();
		this.text = text;
		this.area = area;
		this.inventory = inventory;
		this.map = map;
		this.player = player;
		this.pl = pl;
		this.enemy = enemy;
		this.inv = inv;
		this.comm = comm;
		this.win = win;
		this.main = main;
		this.start = start;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 38 && held == false) {
			if (comm.size() > 0 && comm.size() > commLoc) {
				if (commLoc == comm.size() - 1) {
					text.setText(comm.get(commLoc));
				}
				else {
					text.setText(comm.get(commLoc));
					commLoc++;
				}
				held = true;
			}
		}
		else if (e.getKeyCode() == 40 && held == false) {
			if (comm.size() > 0 && commLoc >= 0) {
				if (commLoc == 0) {
					text.setText("");
				}
				else {
					commLoc--;
					text.setText(comm.get(commLoc));
				}
				held = true;
			}
		}
		else {
			commLoc = 0;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 38) {
			held = false;
		}
		else if (e.getKeyCode() == 40) {
			held = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		

	}

}
