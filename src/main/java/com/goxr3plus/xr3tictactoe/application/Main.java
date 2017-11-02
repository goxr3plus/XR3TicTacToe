package main.java.com.goxr3plus.xr3tictactoe.application;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main implements ActionListener {
	Random randomNumber = new Random();
	Font font = new Font("Verdana", 0, 11);
	final ImageIcon refresh = new ImageIcon(this.getClass().getResource("/image/refresh.png"));
	final ImageIcon winner = new ImageIcon(this.getClass().getResource("/image/winner.png"));
	JFrame mainWindow = new JFrame("XR3TicTacToe");
	JLabel mainLabel = new JLabel(new ImageIcon(this.getClass().getResource("/image/woodmap.png")));
	JFrame TimerWindow = new JFrame();
	boolean ShowTiming = false;
	JButton[] box = new JButton[]{ new JButton() , new JButton() , new JButton() , new JButton() , new JButton() , new JButton() , new JButton() , new JButton() , new JButton() ,
			new JButton() };
	JCheckBox Vs = new JCheckBox("Player(1) Vs Computer", false);
	boolean PcMode = false;
	JCheckBox Volume = new JCheckBox("Volume", true);
	boolean boardEnabled = true;
	int[] score = new int[3];
	boolean[] player1 = new boolean[10];
	boolean[] player2 = new boolean[10];
	static boolean turn = false;
	boolean[] clicked = new boolean[10];
	public static SquareButton score1 = new SquareButton("PLayer1 Score: 0", new ImageIcon(Main.class.getResource("/image/SpFlag1.png")));
	public static SquareButton score2 = new SquareButton("PLayer2 Score: 0", new ImageIcon(Main.class.getResource("/image/SpFlag2.png")));
	SquareButton clear = new SquareButton("Clear Board", new ImageIcon(this.getClass().getResource("/image/clearall.png")));
	SquareButton restart = new SquareButton("Restart", new ImageIcon(this.getClass().getResource("/image/restart.png")));
	SquareButton gameClassic = new SquareButton("Classic", new ImageIcon(this.getClass().getResource("/image/classic.png")));
	boolean Classic = true;
	Helper extra;
	
	public static void main(String[] args) {
		try {
			LookAndFeelInfo[] arg3;
			int arg2 = ( arg3 = UIManager.getInstalledLookAndFeels() ).length;
			
			for (int arg1 = 0; arg1 < arg2; ++arg1) {
				LookAndFeelInfo e = arg3[arg1];
				if ("Nimbus".equals(e.getName())) {
					UIManager.setLookAndFeel(e.getClassName());
					break;
				}
			}
		} catch (Exception arg4) {
			arg4.printStackTrace();
		}
		
		new Main();
	}
	
	public Main() {
		this.extra = new Helper(this.box, this.player1, this.player2, this.clicked, this.score);
		this.mainWindow.setSize(378, 426);
		this.mainWindow.setDefaultCloseOperation(3);
		this.mainWindow.setIconImage( ( new ImageIcon(this.getClass().getResource("/image/arrow.png")) ).getImage());
		this.mainWindow.setResizable(false);
		this.mainWindow.setLocationRelativeTo((Component) null);
		this.mainLabel.setLayout((LayoutManager) null);
		this.box[1].setBounds(51, 40, 90, 90);
		this.box[2].setBounds(141, 40, 90, 90);
		this.box[3].setBounds(231, 40, 90, 90);
		this.box[4].setBounds(51, 130, 90, 90);
		this.box[5].setBounds(141, 130, 90, 90);
		this.box[6].setBounds(231, 130, 90, 90);
		this.box[7].setBounds(51, 220, 90, 90);
		this.box[8].setBounds(141, 220, 90, 90);
		this.box[9].setBounds(231, 220, 90, 90);
		
		int v;
		for (v = 1; v < this.box.length; ++v) {
			this.box[v].setFocusable(false);
		}
		
		score1.setBounds(5, 2, 170, 27);
		score1.setFont(this.font);
		score2.setBounds(195, 2, 170, 27);
		score2.setFont(this.font);
		this.clear.setBounds(5, 330, 120, 27);
		this.clear.setBackground(Color.YELLOW);
		this.clear.setFont(this.font);
		this.restart.setBounds(135, 330, 110, 27);
		this.restart.setBackground(Color.GRAY);
		this.restart.setFont(this.font);
		this.gameClassic.setBounds(255, 330, 110, 27);
		this.gameClassic.setBackground(new Color(99, 207, 254, 120));
		this.gameClassic.setFont(this.font);
		this.Vs.setBounds(20, 360, 160, 30);
		this.Vs.setFont(this.font);
		this.Volume.setBounds(250, 360, 70, 30);
		this.Volume.setFont(this.font);
		
		for (v = 1; v < this.box.length; ++v) {
			this.mainLabel.add(this.box[v]);
		}
		
		this.mainLabel.add(this.gameClassic);
		this.mainLabel.add(score1);
		this.mainLabel.add(score2);
		this.mainLabel.add(this.clear);
		this.mainLabel.add(this.restart);
		this.mainLabel.add(this.Vs);
		this.mainLabel.add(this.Volume);
		this.mainWindow.add( ( new JPanel() ).add(this.mainLabel));
		this.mainWindow.setVisible(true);
		
		for (v = 1; v < this.box.length; ++v) {
			this.box[v].addActionListener(this);
		}
		
		this.clear.addActionListener(this);
		this.restart.addActionListener(this);
		this.gameClassic.addActionListener(this);
		this.Vs.addActionListener(this);
		this.MakeTimerWindow();
	}
	
	private void MakeTimerWindow() {
		this.TimerWindow.setSize(32, 32);
		this.TimerWindow.setLocation(this.mainWindow.getX() + 330, this.mainWindow.getY() + 64);
		this.TimerWindow.setDefaultCloseOperation(0);
		this.TimerWindow.setUndecorated(true);
		this.TimerWindow.getContentPane().setBackground(Color.WHITE);
		this.TimerWindow.setAlwaysOnTop(true);
		this.TimerWindow.add(new JLabel(new ImageIcon(this.getClass().getResource("/image/10.gif"))));
		( new Thread(() -> {
			boolean speed = true;
			
			while (speed) {
				if (this.ShowTiming) {
					this.boardEnabled = false;
					this.TimerWindow.setLocation(this.mainWindow.getX() + 330, this.mainWindow.getY() + 64);
					this.TimerWindow.setVisible(true);
					this.ShowTiming = false;
					
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException arg2) {
						arg2.printStackTrace();
					}
					
					this.TimerWindow.dispose();
					this.PcPlay();
					this.boardEnabled = true;
				}
				
				try {
					Thread.sleep(15L);
				} catch (InterruptedException arg3) {
					arg3.printStackTrace();
				}
			}
			
		}) ).start();
	}
	
	public void PcPlay() {
		int i = 1 + this.randomNumber.nextInt(9);
		if (this.clicked[1] && this.clicked[2] && this.clicked[3] && this.clicked[4] && this.clicked[5] && this.clicked[6] && this.clicked[7] && this.clicked[8]
				&& this.clicked[9]) {
			this.extra.Results(this.Volume.isSelected());
		} else {
			while (this.clicked[i]) {
				i = 1 + this.randomNumber.nextInt(9);
			}
			
			if (this.extra.PCAlgorithm() != 0) {
				i = this.extra.PCAlgorithm();
			}
			
			if (turn) {
				if (this.Classic) {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/o.png")));
				} else {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/crosser2.png")));
				}
				
				this.player2[i] = true;
			}
			
			turn = !turn;
			this.clicked[i] = true;
			this.extra.Results(this.Volume.isSelected());
		}
	}
	
	public void WhoPlay(int i) {
		if (!this.clicked[i]) {
			if (!turn) {
				if (this.Classic) {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/x.png")));
				} else {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/crosser.png")));
				}
				
				this.player1[i] = true;
			} else if (turn) {
				if (this.Classic) {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/o.png")));
				} else {
					this.box[i].setIcon(new ImageIcon(this.getClass().getResource("/image/crosser2.png")));
				}
				
				this.player2[i] = true;
			}
			
			turn = !turn;
			this.clicked[i] = true;
			if (!this.Vs.isSelected()) {
				this.ShowTiming = true;
			}
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.box[1]) {
			this.BoxesMethod(1);
		} else if (source == this.box[2]) {
			this.BoxesMethod(2);
		} else if (source == this.box[3]) {
			this.BoxesMethod(3);
		} else if (source == this.box[4]) {
			this.BoxesMethod(4);
		} else if (source == this.box[5]) {
			this.BoxesMethod(5);
		} else if (source == this.box[6]) {
			this.BoxesMethod(6);
		} else if (source == this.box[7]) {
			this.BoxesMethod(7);
		} else if (source == this.box[8]) {
			this.BoxesMethod(8);
		} else if (source == this.box[9]) {
			this.BoxesMethod(9);
		} else if (source == this.restart) {
			this.extra.Refresh(3);
		} else if (source == this.gameClassic) {
			if (!this.Classic) {
				this.Classic = true;
				this.extra.Refresh(0);
				this.gameClassic.setText("Classic");
			} else if (this.Classic) {
				this.Classic = false;
				this.extra.Refresh(0);
				this.gameClassic.setText("No classic");
			}
		} else if (source == this.clear) {
			this.extra.Refresh(0);
		} else if (source == this.Vs) {
			if (this.Vs.isSelected()) {
				this.Vs.setText("Player(1) Vs Player(2)");
				this.PcMode = false;
			} else {
				this.Vs.setText("Player(1) Vs Computer");
				this.PcMode = true;
			}
			
			this.extra.Refresh(4);
			System.out.println(this.PcMode);
		}
		
	}
	
	public void BoxesMethod(int i) {
		if (this.boardEnabled) {
			this.WhoPlay(i);
			if (this.Vs.isSelected()) {
				this.extra.Results(this.Volume.isSelected());
			}
		}
		
	}
}
