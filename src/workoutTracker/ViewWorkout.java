package workoutTracker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ViewWorkout extends JFrame {
	
	Workout workout;
	final int FRAME_WIDTH = 500;
	final int FRAME_HEIGHT = 500;
	
	public ViewWorkout(Workout workout) {
		
		this.workout = workout;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Viewing " + workout.getName());
		this.setLayout(new FlowLayout());
		
		// get exercise list
		ArrayList<Exercise> exerciseList = workout.getExerciseList();
		
		// reset all exercises

		// prepare and add all exercises from list
		for (Exercise exercise : exerciseList) {
			exercise.addSetLabels();
			this.add(exercise);
		}
		
		// Setting Icon logo to custom image
		ImageIcon image = new ImageIcon("workoutTracker/resources/dumbell.png");
		this.setIconImage(image.getImage());
		
		// change background color
		this.getContentPane().setBackground(Color.GRAY);
		
	
		// set visible
		this.setVisible(true);
		this.validate();
		this.repaint();
	}


}
