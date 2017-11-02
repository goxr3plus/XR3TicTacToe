package main.java.com.goxr3plus.xr3tictactoe.application;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class SquareButton extends JButton {
	public SquareButton(String text, ImageIcon Icon) {
		setText(text);
		setIcon(Icon);
		setFocusable(false);
	}
}
