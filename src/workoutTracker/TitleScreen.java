package workoutTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TitleScreen {

	MainFrame frame;
	Font myFont;
	boolean isClicked = false;
	
	public TitleScreen(MainFrame frame) {
		this.frame = frame;
		myFont = MainFrame.myFont;
		
		// Load Image for title screen
		JLabel title = new JLabel("Workout Tracker 9000", JLabel.CENTER);
		title.setBounds(0,0, 1000,750);
		title.setFont(myFont.deriveFont(Font.BOLD, 50f));
		title.setVisible(true);
		
		JLabel subtitle = new JLabel("Click anywhere to begin", JLabel.CENTER);
		subtitle.setBounds(0,750,1000,250);
		subtitle.setFont(myFont.deriveFont(Font.BOLD, 30f));
		subtitle.setVisible(true);
		
		this.frame.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				isClicked = true;
				System.out.println(isClicked);
				title.setVisible(false);
				subtitle.setVisible(false);
				frame.addComponents();
				// validation
				frame.validate();
				frame.repaint();	
				frame.setVisible(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.frame.add(title);
		this.frame.add(subtitle);
		this.frame.setVisible(true);
	}
}
