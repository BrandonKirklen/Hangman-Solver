import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
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
		//set graphics to graphics 2D
		Graphics2D g2 = (Graphics2D) g;
		
		//set line type to rounded line for cleaner look
		BasicStroke rounded = new BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
		g2.setStroke(rounded);

		//turn on anti-aliasing to clean up circles
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				            RenderingHints.VALUE_ANTIALIAS_ON);
		
		//set color to black
		g2.setColor(Color.BLACK);

		// first guess
		if(guesses > 0) {
			//draws base
			g2.drawLine(1, 299, 299, 299);
	
			//draws gallows
			g2.drawRect(10, 10, 15, 299);
			g2.drawRect(10, 10, 160, 15);

			//draws rope
			g.drawLine(150, 25, 150, 70);
		}
		
		// second guess
		if(guesses > 1) {
			//draws head
			g2.drawOval(150-25, 70, 50, 50);
			
			//eyes
			g2.drawOval(140,87,2,4);
			g2.drawOval(160,87,2,4);
			
			//mouth - happy
		    g2.drawArc(140, 100, 21, 12, 180, 180);	
		}
		
		// third guess
		if(guesses > 2) {
			//body
			g2.drawLine(150, 120, 150, 200);
		}
		
		// fourth guess
		if(guesses > 3) {
			//left arm
			g2.drawLine(150, 140, 140, 185);
		}
		
		// fifth guess
		if(guesses > 4) {
			//right arm
			g2.drawLine(150, 140, 160, 185);
		}
		
		// sixth guess
		if(guesses > 5) {
			//change face to worried ._.
			g2.clearRect(138, 98, 26, 16);
			g2.drawLine(140,105,160,105);	
		}
		
		// seventh guess
		if(guesses > 6) {
			//left leg
			g2.drawLine(150, 200, 135, 250);
		}
		
		//eighth guess
		if(guesses > 7) {
			//right leg
			g2.drawLine(150, 200, 165, 250);
		}
		
		//ninth guess
		if(guesses > 8) {
			g2.clearRect(138, 98, 26, 16);
			g2.drawArc(140, 105, 21, 8, -180, -180);		
		}
		
		//tenth guess
		if(guesses > 9) {
			//resets panel
			g2.clearRect(0,0,300,300);
			
			//re-draws base
			g2.drawLine(1, 299, 299, 299);
	
			//re-draws gallows
			g2.drawRect(10, 10, 15, 299);
			g2.drawRect(10, 10, 160, 15);

			//draws longer rope
			g.drawLine(150, 25, 150, 85);
			
			//re-draws head
			g2.drawOval(150-25, 85, 50, 50);
			
			//draws dead face
			g2.drawLine(140,99,145,107);				//left eye
			g2.drawLine(140,107,145,99);				//left eye
			g2.drawLine(155,99,160,107);				//right eye
			g2.drawLine(155,107,160,99);				//right eye
			g2.drawArc(140, 120, 21, 8, -180, -180);	        //mouth
			
			//re-draws body
			g2.drawLine(150, 135, 150, 215);
			
			//re-draws left arm
			g2.drawLine(150, 155, 140, 200);
			
			//re-draws right arm
			g2.drawLine(150, 155, 160, 200);
			
			//re-draws left leg
			g2.drawLine(150, 215, 135, 265);
			
			//re-draws right leg
			g2.drawLine(150, 215, 165, 265);
		}
	}
	
	public void set() {
		guesses++;
		paintComponent(getGraphics());
	}
	public void set(int guess) {
		guesses = guess;
		paintComponent(getGraphics());
	}
	
}
