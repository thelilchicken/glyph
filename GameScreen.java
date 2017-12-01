package main;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultCaret;

public class GameScreen extends JFrame {
	
	private static final long serialVersionUID = -9046105757503558839L;
	
	/*
	 * Components
	 */
	private JPanel mainFrame, readTextPanel, invPanel, startFrame, startHolder, startFrameHolder, inputPanel;
	private JTextField textField;
	private JButton startGame, quitGame, inGameQuit;
	private JTextArea readText, inv;
	
	/*
	 * Game Related Variables
	 */
	private Player player;
    private WorldGen world;
    private List<String> prevCommands = new ArrayList<String>();
    private boolean inGame = false;
	
	public GameScreen() {
		super ("Glyph");
				
		//Initializations
		player = new Player(1,100,"Solomon", 0, 0, 0, 0, 0, 0);
        world = new WorldGen(200, 200);
        
		mainFrame = new JPanel();
		textField = new JTextField();
		readText = new JTextArea(5, 110);
		inv = new JTextArea("", 5, 50);
		readTextPanel = new JPanel(new BorderLayout());
		invPanel = new JPanel();
		startFrameHolder = new JPanel();
		inputPanel = new JPanel();
		startFrame = new JPanel();
		startHolder = new JPanel();
		startGame = new JButton("New Game");
		quitGame = new JButton("Quit ...");
		inGameQuit = new JButton("Quit");
		
		//InputPanel Details
		readTextPanel.add(inputPanel, BorderLayout.SOUTH);
		inputPanel.setBackground(new Color(0, 0, 0, 0));
		inputPanel.setLayout(new BorderLayout());
		inputPanel.setVisible(true);
		
		//StartFrameHolder details
		startFrameHolder.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		startFrameHolder.setBackground(new Color(52, 179, 69));
		startFrameHolder.setVisible(true);
		
		//StartHolder details
		startHolder.setLayout(new BoxLayout(startHolder, BoxLayout.Y_AXIS));
		startHolder.setSize(100, 1);
		startHolder.setBorder(new EmptyBorder(0, 0, 0, 0));
		startHolder.setBackground(new Color(52, 179, 69));
		
		//StartFrame details
		startFrameHolder.add(startFrame);
		startFrame.setBorder(new EmptyBorder(100, 100, 800, 1500));
		startFrame.setLayout(new BorderLayout());
		startFrame.setBackground(new Color(52, 179, 69));
		startFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		startFrame.setVisible(true);
		
		startFrame.add(startHolder, BorderLayout.WEST);
		
		//MainFrame Details
		mainFrame.setBorder(new EmptyBorder(20, 20, 20, 20));
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setBackground(new Color(52, 179, 69));
		mainFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		mainFrame.setVisible(false);
		
		//JFrame Details		
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setUndecorated(true);
        setVisible(true);
        setBackground(new Color(52, 179, 69));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //ReadText Details
        mainFrame.add(readTextPanel, BorderLayout.WEST);
        
        readTextPanel.setPreferredSize(new Dimension(900, 800));
        readTextPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        readTextPanel.add(readText, BorderLayout.NORTH);
        readTextPanel.setBackground(new Color(215, 158, 85, 75));
        
        JScrollPane scrollPane = new JScrollPane(readText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        readText.setBorder(BorderFactory.createEmptyBorder());
        readTextPanel.add(scrollPane);
        
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        
        DefaultCaret caret = (DefaultCaret)readText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        readText.setEditable(false);
        readText.setLineWrap(true);
        readText.setFont(new Font("MS Gothic", Font.BOLD, 20));
        readText.setForeground(new Color(0, 0, 0));
        readText.setBackground(new Color(215, 158, 85, 75));
		
        //Inventory Details
        mainFrame.add(invPanel, BorderLayout.EAST);
        
        invPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        invPanel.add(inv, BorderLayout.CENTER);
        invPanel.setBackground(new Color(215, 158, 85));
        
        inv.setEditable(false);
        inv.setFont(new Font("MS Gothic", Font.BOLD, 20));
        inv.setForeground(new Color(0, 0, 0));
        inv.setBackground(new Color(215, 158, 85));
        
        //Text Box Details
        inputPanel.add(textField, BorderLayout.WEST);
        
        textField.setFocusable(true);
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setBorder(new LineBorder(new Color(0, 0, 0)));
        textField.setBackground(new Color(200, 158, 85));
        textField.setForeground(new Color(0, 0, 0));
        textField.setFont(new Font("MS Gothic", Font.BOLD, 20));
        textField.setPreferredSize(new Dimension(750, 20));
        
        //Start Button
        startHolder.add(startGame);
        startGame.setBackground(new Color(0, 0, 0));
        startGame.setFont(new Font("MS Gothic", Font.BOLD, 20));
        startGame.setForeground(new Color(255, 255, 255));
        startGame.setActionCommand("quit"); 
        startGame.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        //Quit Button
        startHolder.add(quitGame);
        quitGame.setPreferredSize(new Dimension(100, 100));
        quitGame.setBackground(new Color(0, 0, 0));
        quitGame.setFont(new Font("MS Gothic", Font.BOLD, 20));
        quitGame.setForeground(new Color(255, 255, 255));
        quitGame.setActionCommand("start");
        quitGame.setBorder(new EmptyBorder(10, 50, 10, 50));
        
        //inGameQuit Details
      	inputPanel.add(inGameQuit, BorderLayout.EAST);
      	
      	inGameQuit.setPreferredSize(new Dimension(100, 30));
      	inGameQuit.setBackground(new Color(0, 0, 0));
      	inGameQuit.setFont(new Font("MS Gothic", Font.BOLD, 20));
      	inGameQuit.setForeground(new Color(255, 255, 255));
      	inGameQuit.setActionCommand("main");
      	inGameQuit.setVisible(true);        
        
		//Adding Components
		add(mainFrame);
		add(startFrameHolder);
		
		//Listeners
		textField.addActionListener(new InputFromTyper(textField, readText, inv, world, player, inv, prevCommands, this, mainFrame, startFrame, inGame, scrollPane));
        quitGame.addActionListener(new InputFromTyper(textField, readText, inv, world, player, inv, prevCommands, this, mainFrame, startFrame, inGame, scrollPane));
        startGame.addActionListener(new InputFromTyper(textField, readText, inv, world, player, inv, prevCommands, this, mainFrame, startFrame, inGame, scrollPane));
		inGameQuit.addActionListener(new InputFromTyper(textField, readText, inv, world, player, inv, prevCommands, this, mainFrame, startFrame, inGame, scrollPane));
        textField.addKeyListener(new KeyFromTyper(textField, readText, inv, world, player, inv, prevCommands, this, mainFrame, startFrame));
	}
	
	public static void main(String args[]) {
		//Start Loading Screen
		JFrame load = new JFrame("Loading Glyph...");
		JPanel loadBack = new JPanel();
		JLabel loadLabel = new JLabel("Glyph");
		loadBack.setLayout(new GridBagLayout());
		loadBack.add(loadLabel);
		load.add(loadBack);
		load.setSize(400, 200);
		load.setUndecorated(true);
		loadBack.setBackground(new Color(130, 98, 20));
		loadLabel.setForeground(new Color(255, 255, 255));
		loadLabel.setFont(new Font("MS Gothic", Font.BOLD, 75));
		loadLabel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(10.0f)));
		load.setLocationRelativeTo(null);
		load.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		load.setVisible(true);
		
		new GameScreen();
		load.dispose();
	}
}
