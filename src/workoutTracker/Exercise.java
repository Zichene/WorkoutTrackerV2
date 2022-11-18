package workoutTracker;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;

public class Exercise extends JPanel {
	
	static final int WIDTH = 150;
	static final int HEIGHT = 150;
	static final Color BG_COLOR = Color.LIGHT_GRAY;
	
	// important info about exercise
	private int numberOfSets;
	private String name;
	private ArrayList<Integer> numberOfReps;
	private ArrayList<Double> weight;
	private String weightUnit;
	
	// labels and all that
	private JLabel nameLabel;
	private ArrayList<JLabel> repLabels;
	
	//font
	Font myFont;
	
	public Exercise(String name, int numberOfSets, String weightUnit) {
		myFont = MainFrame.myFont;
		if (name.equals("")) {
			this.name = "Exercise";
		} else {
			this.name = name;
		}
		this.numberOfSets = numberOfSets;
		this.weightUnit = weightUnit;
		numberOfReps = new ArrayList<Integer>();
		weight = new ArrayList<Double>();
		
		// initiate + add name label
		nameLabel = new JLabel(this.name, JLabel.CENTER);
		nameLabel.setFont(myFont.deriveFont(Font.BOLD, 20f));
		nameLabel.setPreferredSize(new Dimension(125,30));
		this.add(nameLabel);

		// initiate and add all rep labels
		repLabels = new ArrayList<JLabel>(); 
		
		for (int i = 0; i < numberOfSets; i++) {
			JLabel repLabel = new JLabel("", JLabel.CENTER);
			repLabel.setFont(myFont.deriveFont(Font.PLAIN, 16f));
			this.add(repLabel);
			repLabels.add(repLabel); 
		}
		
		
		// configure the exercise panel
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.gray, Color.black, Color.gray));
		this.setBackground(BG_COLOR);
		this.setOpaque(true);
		this.setVisible(true);

		this.setLayout(new FlowLayout());     // CHANGE LATER
	}
	
	// method that adds all the set labels
	public void addSetLabels() {
		if (numberOfReps != null && weight != null) {
			for (int i = 0; i < this.numberOfSets; i++) {
				if (repLabels != null) {
					// converting weight to respective (lbs or kg)
					if (this.weightUnit.equals("lbs") && !MainFrame.isLbs()) {
						weight = convertLbsToKg(weight);
						this.weightUnit = "kg";
					} else if (this.weightUnit.equals("kg") && MainFrame.isLbs()) {
						weight = convertKgToLbs(weight);
						this.weightUnit = "lbs";
					}
					JLabel currentRepLabel = repLabels.get(i);
					currentRepLabel.setText("Rep #" + (i+1) + " : " + weight.get(i) + " " + this.weightUnit + " x " + numberOfReps.get(i));
					currentRepLabel.setHorizontalTextPosition(JLabel.CENTER);
					currentRepLabel.setPreferredSize(new Dimension(125, 15));
					currentRepLabel.setVisible(true);
				}
			}
		}
	}
	
	// method that converts an array list of lbs values to kg values
	public ArrayList<Double> convertLbsToKg(ArrayList<Double> weights) {
		for (int i = 0; i < weights.size(); i++) {
			weights.set(i, (double) Math.round(weights.get(i)/2.205));
		}
		return weights;
	}
	
	// method that converts an array list of kg values to lbs values
	public ArrayList<Double> convertKgToLbs(ArrayList<Double> weights) {
		for (int i = 0; i < weights.size(); i++) {
			weights.set(i,(double) Math.round(weights.get(i)*2.205));
		} 
		return weights;
	}
	
	
	
	// getters and setters
	public int getNumberOfSets() {
		return numberOfSets;
	}

	public void setNumberOfSets(int numberOfSets) {
		this.numberOfSets = numberOfSets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getNumberOfReps() {
		return numberOfReps;
	}

	public void setNumberOfReps(ArrayList<Integer> numberOfReps) {
		this.numberOfReps = numberOfReps;
	}

	public ArrayList<Double> getWeight() {
		return weight;
	}

	public void setWeight(ArrayList<Double> weight) {
		this.weight = weight;
	}

}

