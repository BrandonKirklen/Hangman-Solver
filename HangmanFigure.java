import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HangmanFigure extends JPanel {
	
	private int guesses;

	public HangmanFigure() {
		super();
		guesses = 0;
		setPreferredSize(new Dimension(300, 300));
		setOpaque(true);
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		
		// base
		if(guesses > 0) {
			g.drawLine(1, 299, 299, 299);
		}
		
		// right wall
		if(guesses > 1) {
			g.drawLine(299, 299, 299, 1);
		}
		
		// top line
		if(guesses > 2) {
			g.drawLine(150, 1, 299, 1);
		}
		
		// hanging line
		if(guesses > 3) {
			g.drawLine(150, 1, 150, 70);
		}
		
		// face
		if(guesses > 4) {
			g.drawOval(150-25, 70, 50, 50);
		}
		
		// body
		if(guesses > 5) {
			g.drawLine(150, 120, 150, 200);
		}
		
		// left hand
		if(guesses > 6) {
			g.drawLine(150, 150, 110, 140);
		}
		
		// right hand
		if(guesses > 7) {
			g.drawLine(150, 150, 190, 140);
		}
		
		// left leg
		if(guesses > 8) {
			g.drawLine(150, 200, 120, 250);
		}
		
		// right leg
		if(guesses > 9) {
			g.drawLine(150, 200, 180, 250);
		}
	}
	
	public void set() {
		guesses++;
		paintComponent(getGraphics());
	}
	
}