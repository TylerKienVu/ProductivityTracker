package common;

import java.io.Serializable;
import java.util.Date;

public class DateSerial implements Serializable{
	private static final long serialVersionUID = -4837405874058175031L;
	private int currentStreak;
	
	public DateSerial(int currentStreak){
		this.currentStreak = currentStreak;
	}
	
	//current date saved
	private Date saveDate = new Date();
	
	public Date getSaveDate(){
		return saveDate;
	}
	public int getCurrentStreak(){
		return currentStreak;
	}
}
