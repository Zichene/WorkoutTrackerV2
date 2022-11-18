package workoutTracker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFrame extends JFrame implements ActionListener {
	static final int FRAME_WIDTH = 1000;
	static final int FRAME_HEIGHT = 1000;
	static final int MAIN_PANEL_HEIGHT = 250;
	static final Color FRAME_COLOR = Color.gray;
	static final Color MAIN_PANEL_COLOR = Color.DARK_GRAY;
	static final int BUTTON_WIDTH = 200;
	static final int BUTTON_HEIGHT = 100;
	static final int WORKOUT_PANEL_SPACING = 35;
	static int workoutCounter = 0;
	static final int MAX_WORKOUT_NO = 3;
	
	private static boolean isLbs = false;
	private static WorkoutList wl;
	private ArrayList<Workout> workoutList; 
	static final String[] muscleGroups = {"Chest and triceps", "Back and biceps", "Legs", "Other"};
	
	public static Font myFont;
	
	public MainFrame() {

		setup();
		TitleScreen ts = new TitleScreen(this);
	//	addComponents();
		
		// validation
		this.validate();
		this.repaint();	
		this.setVisible(true);
	}
	
	
	/**
	 * Sets up the MainFrame, loads font, instantiates workout list, etc.
	 */
	public void setup() {
		// loading font
		loadFont();
		
		// workout list
		wl = new WorkoutList();
		workoutList = new ArrayList<Workout>();
		wl.setWorkoutList(workoutList);
		
		// 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// end program when press on X
		this.setResizable(false);
		this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.setTitle("Workout Tracker 9000");
		
		// layout manager
	    this.setLayout(null);
		
		// Setting Icon logo to custom image
		ImageIcon image = new ImageIcon("workoutTracker/resources/dumbell.png");
		this.setIconImage(image.getImage());
		
		// change background color
		this.getContentPane().setBackground(FRAME_COLOR);
	}
	
	/**
	 *  Adds all the visual components of the MainFrame (Workout and Settings panel, along with buttons)
	 */
	public void addComponents() {
		// Middle workout panel
		JPanel WorkoutPanel = new JPanel();
		
		//JPanel WorkoutPanel = new JPanel();
		WorkoutPanel.setBounds(0,0, FRAME_WIDTH, FRAME_HEIGHT - MAIN_PANEL_HEIGHT);
		WorkoutPanel.setBackground(FRAME_COLOR);
		WorkoutPanel.setOpaque(false);
		WorkoutPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, WORKOUT_PANEL_SPACING));
		this.add(WorkoutPanel);
		
		// Background image
//		try {
//			BufferedImage myPicture = ImageIO.read(new File("background.jpg"));
//			JLabel bg = new JLabel(new ImageIcon(myPicture));
//			WorkoutPanel.add(bg);
//		} catch (IOException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
		
		
		
		// Bottom setup and settings panel
		JPanel MainPanel = new JPanel();
		MainPanel.setBounds(0, FRAME_HEIGHT - MAIN_PANEL_HEIGHT, FRAME_WIDTH, MAIN_PANEL_HEIGHT);
		MainPanel.setBackground(MAIN_PANEL_COLOR);
		MainPanel.setOpaque(true);
		MainPanel.setLayout(new FlowLayout()); // to change later
		this.add(MainPanel);
		
		// Button to add new workout
		JButton addWorkoutBtn = new JButton("Add Workout");
		addWorkoutBtn.setFont(myFont.deriveFont(Font.BOLD, 25)); 
		addWorkoutBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		addWorkoutBtn.addActionListener(e -> {
			if (workoutCounter < MAX_WORKOUT_NO) {
				Workout currentWorkout = getNewWorkout();
				WorkoutPanel.add(currentWorkout); 
				int prompt = promptUser(currentWorkout);
				wl.add(currentWorkout);
				currentWorkout.displayDetails();
				currentWorkout.displayButtons();
				if (prompt == 0) {
					this.setVisible(true);
					workoutCounter++;
					System.out.println("workoutCounter: " + workoutCounter);
				} else {
					currentWorkout.setVisible(false);
				}
			} else {
				displayErrorMsg();
			}
		});
		MainPanel.add(addWorkoutBtn);
		
		// Button to save workout
		JButton saveBtn = new JButton("Save Workouts");
		saveBtn.setFont(myFont.deriveFont(Font.BOLD, 25)); 
		saveBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		saveBtn.addActionListener(e -> {
			// writing all Workout objects into file
			try {
				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("src/workoutTracker/resources/workoutSaveFile.txt"));
				ArrayList<Workout> savedWorkoutList = wl.getWorkoutList();
				output.writeObject(savedWorkoutList);
				output.close();
			}
			catch (IOException ioe) {
				System.err.println("Error printing to file!");
			}
			
		});
		MainPanel.add(saveBtn);
		
		// Button to load workout
		JButton loadBtn = new JButton("Load Workouts");
		loadBtn.setFont(myFont.deriveFont(Font.BOLD, 25)); 
		loadBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		loadBtn.addActionListener(e -> {
			try {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream("src/workoutTracker/resources/workoutSaveFile.txt"));
				@SuppressWarnings("unchecked")
				ArrayList<Workout> loadedWorkoutList = (ArrayList<Workout>) input.readObject();
				int workoutSize = loadedWorkoutList.size();
	
				System.out.println("workoutList size: " + workoutSize);
				if (workoutSize != 0) {
					int total = workoutSize + workoutCounter;
					for (int i = 0; i < workoutSize; i++) {
						System.out.println("Total: " + total);
						if (total <= MAX_WORKOUT_NO) {
							Workout currentWorkout = loadedWorkoutList.get(i);
							currentWorkout.deleteButtons();
							currentWorkout.displayButtons();
							WorkoutPanel.add(currentWorkout);
							wl.add(currentWorkout); 
							// add one workout for each one loaded in
							if (currentWorkout.isVisible()) {
								workoutCounter++;
							}
							System.out.println(workoutCounter);
						} else if (workoutCounter + workoutSize > MAX_WORKOUT_NO){
							displayErrorMsg();
							break;
						}
					}					
				}
				input.close();
				WorkoutPanel.validate();
				WorkoutPanel.repaint();
			}
			catch(IOException ioe) {
				System.err.println("Error printing to file!");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		MainPanel.add(loadBtn);
		
		// toggle button for lbs or kg
		JToggleButton lbsToggleBtn = new JToggleButton("Weight in lbs (default in kg)");
		lbsToggleBtn.setFont(myFont.deriveFont(Font.BOLD, 17));
		lbsToggleBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		lbsToggleBtn.addActionListener(e -> {
			if (lbsToggleBtn.isSelected()) {
				isLbs = true;
			} else {
				isLbs = false;
			}
			//System.out.println(isLbs); DEBUG
		});
		MainPanel.add(lbsToggleBtn);
	}
	
	
	/**
	 * Creates a new Workout if the maximum amount has not been exceeded.
	 * @return The new Workout object.
	 */
	public Workout getNewWorkout() {
		System.out.println(workoutCounter);
		if (workoutCounter < MAX_WORKOUT_NO) {
			System.out.println("added workout");
			if (workoutCounter == 2) {
				
			}
			return new Workout();
		}
		return null;
	}
	 

	/**
	 * Displays a message pane when the maximum workout number has been reached.
	 */
	public void displayErrorMsg() {
		JOptionPane.showMessageDialog(this, "Maximum workout number reached! " + "(" + MAX_WORKOUT_NO + ")", "Warning", JOptionPane.WARNING_MESSAGE);
	} 
	
	/**
	 * Prompts the user for the date and muscle group of the new workout and stores these values in the parameter workout. A return value of
	 * 0 indicates a successful prompt and a return value of -1 indicates an error.
	 * @param workout
	 * @return An int indicating success of prompt
	 */
	public int promptUser(Workout workout) {
		// Ask user for name?
	//	workout.setName(JOptionPane.showInputDialog("Name of the workout? "));
		while(true) {
			// get todays date in string format
			String today = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			// ask for date of workout, with default being today
			String date = JOptionPane.showInputDialog("Date of the workout? (MM/dd/yyyy) ",today);
			if (date == null) {
				return -1;
			}
			if (isValidFormat(date)) {
			// transform date from MM/dd/yyyy to written date (e.g. January 31st 2022)
				date = transformToWrittenDate(date);
				workout.setDate(date);	
				break;
		} else {
			JOptionPane.showMessageDialog(null, "Invalid date!", "Error", JOptionPane.ERROR_MESSAGE);
		  }
		}
			
			JComboBox<String> muscleBox = new JComboBox<String>(muscleGroups);
			JOptionPane.showMessageDialog(null, muscleBox, "Muscle group worked? ", JOptionPane.QUESTION_MESSAGE);
			String type = (String) muscleBox.getSelectedItem();
			if (type != null) {
				if (type.equals("Other")) {
					type = JOptionPane.showInputDialog("Muscle group worked? ");
				}
				workout.setType(type);
			} else {
				return -1;
			}
			return 0;
	}
	
	/**
	 *  Transforms a string of the form "MM/dd/yyyy" to written form 
	 * @param date - to be transformed
	 * @return date in written form (i.e. 3 January, 2022)
	 */
	public static String transformToWrittenDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date actualDate = new Date();
		try {
			actualDate = sdf.parse(date);
			// remove the minute/hour part
			return actualDate.toString().replace(" 00:00:00 EST", ",").replace(" 00:00:00 EDT", ",");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method that loads in the custom font (ASENINE_.ttf)
	 */
	public void loadFont() {
		// custom font
		try{
            // load a custom font in your project folder
			myFont = Font.createFont(Font.TRUETYPE_FONT, new File("src\\workoutTracker\\resources\\ASENINE_.ttf")).deriveFont(Font.BOLD, 40f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src\\workoutTracker\\resources\\ASENINE_.ttf")));
		}
		catch(IOException | FontFormatException e){
			e.printStackTrace();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// Copy pasted code from stackoverflow
	// TODO: Specify error type (day, month or year problem)
    public static boolean isValidFormat(String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }
    
	public static int getWorkoutCounter() {
		return workoutCounter;
	}
	
	public static WorkoutList getWorkoutList() { 
		return wl;
	}
	
	public static boolean isLbs() {
		return isLbs;
	}
	

}
