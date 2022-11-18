package workoutTracker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Workout extends JPanel implements ActionListener, Serializable {
	
	static final int PANEL_WIDTH = 900;
	static final int PANEL_HEIGHT = 200;
	static final Color PANEL_COLOR = Color.LIGHT_GRAY;
	static final Color DETAILS_PANEL_COLOR = Color.LIGHT_GRAY;
	static final Dimension BUTTON_DIMENSIONS = new Dimension(PANEL_WIDTH - 700, PANEL_HEIGHT - 150);
	static final Dimension TEXT_DIMENSIONS = BUTTON_DIMENSIONS;
	
	private String name;
	private String date;
    private String type;
	public boolean isViewed = false;
	public boolean isEdited = false;
	private JLabel nameLabel;
	private JLabel dateLabel;
	private JLabel typeLabel;
	private JPanel buttonsPanel;
	private JButton viewWorkoutBtn;
	private JButton editWorkoutBtn;
	private JButton deleteWorkoutBtn;
	
	private Font myFont;
	
	private ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
	
	public Workout() {
		setup();
	}
	
	public void setup() {
		// font
		myFont = MainFrame.myFont;
		
		// setup panel
		this.setVisible(true);
		this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		this.setBackground(PANEL_COLOR);
		this.setLayout(new GridLayout(1,2));
		this.name = "Workout #" + (MainFrame.workoutCounter+1);
		
		// border
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.black, Color.gray, Color.black, Color.gray));
		
		// buttons
		buttonsPanel = new JPanel();
		viewWorkoutBtn = new JButton();
		editWorkoutBtn = new JButton();
		deleteWorkoutBtn = new JButton();
	}
	
	public void displayDetails() {
		// create a new panel for all the details
		JPanel detailsPanel = new JPanel();
		detailsPanel.setVisible(true);
		detailsPanel.setPreferredSize(new Dimension(PANEL_WIDTH - 500, PANEL_HEIGHT));
		detailsPanel.setBackground(DETAILS_PANEL_COLOR);
		detailsPanel.setLayout(new GridLayout(3,1));
		
		this.nameLabel = new JLabel(this.name, SwingConstants.CENTER);
		//this.nameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.nameLabel.setFont(myFont);
		this.nameLabel.setSize(TEXT_DIMENSIONS);
		this.nameLabel.setVisible(true);
		detailsPanel.add(this.nameLabel);
		
		this.dateLabel = new JLabel(this.date, SwingConstants.CENTER);
		this.dateLabel.setFont(myFont.deriveFont(Font.PLAIN, 25)); 
		this.dateLabel.setSize(TEXT_DIMENSIONS);
		this.dateLabel.setVisible(true);
		detailsPanel.add(this.dateLabel);


		this.typeLabel = new JLabel(this.type, SwingConstants.CENTER);
		this.typeLabel.setFont(myFont.deriveFont(Font.PLAIN, 25));
		this.typeLabel.setSize(TEXT_DIMENSIONS);
		this.typeLabel.setVisible(true);
		detailsPanel.add(this.typeLabel);
		
		// new panel just for image
		JPanel imagePanel = new JPanel();
		imagePanel.setBackground(new Color(230, 232, 232));
		
		// add picture depending on muscle group
		if (this.type != null) {
			switch(this.type) {
			case "Chest and triceps":
				addPicture(imagePanel, "src\\workoutTracker\\resources\\chest.png");
				break; 
			case "Back and biceps":
				addPicture(imagePanel, "src\\workoutTracker\\resources\\back.png");
				break;
			case "Legs":
				addPicture(imagePanel, "src\\workoutTracker\\resources\\legs.png");
				break;
			default:
				addPicture(imagePanel, "src\\workoutTracker\\resources\\muscle.png");
				break;
			}
		}

		this.add(detailsPanel);
		this.add(imagePanel);
	}
	
	
	public void displayButtons() {
		buttonsPanel.setVisible(true);
		buttonsPanel.setPreferredSize(new Dimension(PANEL_WIDTH - 400, PANEL_HEIGHT));
		buttonsPanel.setBackground(PANEL_COLOR);
		buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		
		// view workout button
		viewWorkoutBtn.setText("View Workout");
		viewWorkoutBtn.setFont(myFont.deriveFont(Font.BOLD, 20f));
		viewWorkoutBtn.setPreferredSize(BUTTON_DIMENSIONS);
		viewWorkoutBtn.addActionListener(e -> {
				new ViewWorkout(this);
				isViewed = true;	
		});
		buttonsPanel.add(viewWorkoutBtn);
		
		// edit workout button 
		editWorkoutBtn.setText("Edit Workout");
		editWorkoutBtn.setFont(myFont.deriveFont(Font.BOLD, 20f));
		editWorkoutBtn.setPreferredSize(BUTTON_DIMENSIONS);
		editWorkoutBtn.addActionListener(e -> {
			if (!isEdited) {
				new EditWorkout(this);
			}
		});
		buttonsPanel.add(editWorkoutBtn);
		
		// delete workout button
		deleteWorkoutBtn.setText("Delete Workout");
		deleteWorkoutBtn.setFont(myFont.deriveFont(Font.BOLD, 20f));
		deleteWorkoutBtn.setPreferredSize(BUTTON_DIMENSIONS);
		deleteWorkoutBtn.addActionListener(e -> {
			this.setVisible(false); 
			this.setEnabled(false); 
			// when we delete a workout, remove it from the list + decrease counter by 1
			MainFrame.getWorkoutList().remove(this);
			MainFrame.workoutCounter -= 1;
		});
		
		buttonsPanel.add(deleteWorkoutBtn);
		this.add(buttonsPanel);
	}
	
	public void deleteButtons() {
		buttonsPanel.remove(viewWorkoutBtn);
		buttonsPanel.remove(deleteWorkoutBtn);
		buttonsPanel.remove(editWorkoutBtn);
	}
	
	public void addPicture(JPanel imagePanel, String pathName) {
		try {
			BufferedImage myPicture = ImageIO.read(new File(pathName));
			JLabel pic = new JLabel(new ImageIcon(myPicture));
			pic.setBackground(PANEL_COLOR);
			imagePanel.add(pic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	// getters and setters for instance vars
	
	public ArrayList<Exercise> getExerciseList() {
		return exerciseList;
	}

	public void setExerciseList(ArrayList<Exercise> exerciseList) {
		this.exerciseList = exerciseList;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (this.nameLabel != null) {
			this.nameLabel.setText(name);
		}
		this.name = name;
	}

	public JPanel getButtonsPanel() {
		return buttonsPanel;
	}

	public void setButtonsPanel(JPanel buttonsPanel) {
		this.buttonsPanel = buttonsPanel;
	}

	public JButton getViewWorkoutBtn() {
		return viewWorkoutBtn;
	}

	public void setViewWorkoutBtn(JButton viewWorkoutBtn) {
		this.viewWorkoutBtn = viewWorkoutBtn;
	}

	public JButton getEditWorkoutBtn() {
		return editWorkoutBtn;
	}

	public void setEditWorkoutBtn(JButton editWorkoutBtn) {
		this.editWorkoutBtn = editWorkoutBtn;
	}

	public JButton getDeleteWorkoutBtn() {
		return deleteWorkoutBtn;
	}

	public void setDeleteWorkoutBtn(JButton deleteWorkoutBtn) {
		this.deleteWorkoutBtn = deleteWorkoutBtn;
	}

	public String getDate() {
		return this.date;
	}
	
	public void setDate(String date) {
		if (this.dateLabel != null) {
			this.dateLabel.setText(date);
		}
		this.date = date;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		if (this.typeLabel != null) {
			this.typeLabel.setText(type);
		}
		this.type = type;
	}
	
	public boolean isViewed() {
		return isViewed;
	}

	public void setViewed(boolean isViewed) {
		this.isViewed = isViewed;
	}

	public boolean isEdited() {
		return isEdited;
	}

	public void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}


}
