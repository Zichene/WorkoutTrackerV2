package workoutTracker;

import java.util.ArrayList;

public class WorkoutList {
	
	private ArrayList<Workout> workoutList = new ArrayList<Workout>();
	
	public void remove(int index) {
		this.workoutList.remove(index);
	}
	
	public void remove(Workout workout) {
		this.workoutList.remove(workout);
	}
	
	public void add(Workout workout) {
		if (workout != null) {
			this.workoutList.add(workout);
		}	
	}

	public ArrayList<Workout> getWorkoutList() {
		return workoutList;
	}

	public void setWorkoutList(ArrayList<Workout> workoutList) {
		this.workoutList = workoutList;
	} 
}
