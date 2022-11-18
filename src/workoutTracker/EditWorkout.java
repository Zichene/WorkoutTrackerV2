package workoutTracker;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditWorkout extends JFrame implements ActionListener{
	
	Workout workout;
	String exerciseName;
	int numberOfSets;
	
	final static int FRAME_WIDTH = 500;
	final static int FRAME_HEIGHT = 300;
	
	JButton addExBtn = new JButton();
	final static Dimension TEXT_DIMENSIONS = new Dimension(FRAME_WIDTH, 50);
	final static Color EXERCISE_LABEL_COLORS = Color.LIGHT_GRAY;
	Font myFont;
	
	public EditWorkout(Workout workout) {
		this.workout = workout;
		setup();
	}
	
	public void setup() {
		myFont = MainFrame.myFont;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setTitle("Editing " + workout.getName());
		
		// Setting Icon logo to custom image
		ImageIcon image = new ImageIcon("workoutTracker/resources/dumbell.png");
		this.setIconImage(image.getImage());
		
		// change background color
		this.getContentPane().setBackground(Color.GRAY);
		
		// layout manager
		this.setLayout(new FlowLayout());
		
		// add workout edit buttons (edit name, edit type, etc.)
		this.addWorkoutEditBtns();
		
		// add labels and buttons for adding an exercise
		this.addExercise();
	
		// set visible
		this.setVisible(true);
	}
	
	public void addWorkoutEditBtns() {
	
			// name editing
		JTextField editNameField = new JTextField();
		editNameField.setPreferredSize(new Dimension(300,25));
		
		
		JButton editNameBtn = new JButton("Edit Name");
		editNameBtn.setFont(myFont.deriveFont(Font.BOLD, 17f));
		editNameBtn.setPreferredSize(new Dimension(100,25));
		editNameBtn.addActionListener(e -> {
			//System.out.println(editNameField.getText()); debug
			if (!editNameField.getText().equals("")) {
				workout.setName(editNameField.getText());
			}
		});
		this.add(editNameBtn);
        this.add(editNameField);
		
		// date editing
		JButton editDateBtn = new JButton("Edit Date");
		editDateBtn.setFont(myFont.deriveFont(Font.BOLD, 17f));
		editDateBtn.setPreferredSize(new Dimension(100,25));		
		JTextField editDateField = new JTextField();
		editDateField.setPreferredSize(new Dimension(300,25));
		editDateBtn.addActionListener(e -> {
			String newDate = editDateField.getText();
			if (MainFrame.isValidFormat(newDate)) {
				newDate = MainFrame.transformToWrittenDate(newDate);
				workout.setDate(newDate);
			} else {
				JOptionPane.showMessageDialog(null, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		this.add(editDateBtn);
		this.add(editDateField);
		
		// type editing
		JButton editTypeBtn = new JButton("Edit Type");
		editTypeBtn.setFont(myFont.deriveFont(Font.BOLD, 17f));
		editTypeBtn.setPreferredSize(new Dimension(100,25));
		JTextField editTypeField = new JTextField();
		editTypeField.setPreferredSize(new Dimension(300,25));
		editTypeBtn.addActionListener(e -> {
			if (!editNameField.getText().equals("")) {
				workout.setType(editTypeField.getText());
			}
		});		
		this.add(editTypeBtn);
		this.add(editTypeField);

	}
	
	public void addExercise() {
		// Add new exercise
		JLabel exercise = new JLabel("Add new exercise", JLabel.CENTER);
		exercise.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.gray, Color.black, Color.gray));
		exercise.setFont(myFont.deriveFont(Font.BOLD, 20f));
		exercise.setPreferredSize(new Dimension(TEXT_DIMENSIONS));
		exercise.setBackground(EXERCISE_LABEL_COLORS);
		exercise.setOpaque(true);
		exercise.setVisible(true); 
		this.add(exercise);
		
		// Exercise name label
		JLabel exerciseNameLabel = new JLabel("Name", JLabel.CENTER);
		exerciseNameLabel.setFont(myFont.deriveFont(Font.BOLD, 20f));
		exerciseNameLabel.setPreferredSize(new Dimension(100,25));
		exerciseNameLabel.setBackground(EXERCISE_LABEL_COLORS);
		exerciseNameLabel.setOpaque(true);
		exerciseNameLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.gray, Color.black, Color.gray));
		exerciseNameLabel.setVisible(true);
		this.add(exerciseNameLabel);
		
		// Exercise name field
		JTextField exerciseNameField = new JTextField();
		exerciseNameField.setPreferredSize(new Dimension(300, 25));
		this.add(exerciseNameField);
		
		// Add Exercise Button
		JButton addExerciseButton = new JButton("Add Exercise");
		addExerciseButton.setFont(myFont.deriveFont(Font.BOLD, 20f));
		addExerciseButton.setPreferredSize(new Dimension(150, 25));
		addExerciseButton.addActionListener(e -> {		
			exerciseName = exerciseNameField.getText();
			promptUser();
		});
		this.add(addExerciseButton);
	}
	
	// Check if a string is an integer (code from stackoverflow)
	public static boolean isInteger(String s) {
	    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
	    if(s.isEmpty()) return false;
	    for(int i = 0; i < s.length(); i++) {
	        if(i == 0 && s.charAt(i) == '-') {
	            if(s.length() == 1) return false;
	            else continue;
	        }
	        if(Character.digit(s.charAt(i),radix) < 0) return false;
	    }
	    return true;
	}
	
	// Error pop up to be displayed when a non integer has been entered
	public void errorMsg() {
		JOptionPane.showMessageDialog(this, "Enter a valid integer", "Warning", JOptionPane.WARNING_MESSAGE);
	}
	
	// method incorporates all of the info retrieval for adding exercise
	public void promptUser() {
		// get exerciseList from workout object
		ArrayList<Exercise> exerciseList = workout.getExerciseList();

		// create arrayLists
		ArrayList<Integer> numberOfReps = new ArrayList<Integer>();
		ArrayList<Double> weight = new ArrayList<Double>();
		
		while (true) {
			// Ask for number of sets
			String input = JOptionPane.showInputDialog("Number of sets");
			if (isInteger(input)) {
				numberOfSets = Integer.parseInt(input);
				break;
			} else {
				errorMsg();
				continue;  
			}
		}
		
		// create exercise object
		String weightUnit = "kg";
		if (MainFrame.isLbs()) {
			weightUnit = "lbs";
		}
		Exercise currentExercise = new Exercise(exerciseName, numberOfSets, weightUnit);
		
			// for each set, ask for reps and weight, add to corresponding arraylists
			for (int i = 0; i < numberOfSets; i++) {
				while (true) {
					String input = JOptionPane.showInputDialog("Set #" + (i+1) + ": Number of reps?");
					if (isInteger(input)) {
						numberOfReps.add(Integer.parseInt(input));
						break;
					} else {
						errorMsg();
						continue; 
					}
				}
				// add verifications to input2
				while (true) {
					String input2 = JOptionPane.showInputDialog("Set #" + (i+1) + ": Weight? " + "(" + weightUnit + ")");
					if (isInteger(input2)) {
						weight.add(Double.parseDouble(input2));
						break;
					} else {
						errorMsg();
						continue; 
					}
				}
			}
			
			// add arraylists with reps and weight to current exercise obj and add exercise to exercise list
			currentExercise.setNumberOfReps(numberOfReps);
			currentExercise.setWeight(weight);
			exerciseList.add(currentExercise);
			
			// show message to user
			JOptionPane.showMessageDialog(this, currentExercise.getName() + " has been added successfully!");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
