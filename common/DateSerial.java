package common;

import java.io.Serializable;
import java.util.Date;

public class DateSerial implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//current date saved
	private Date saveDate = new Date();
	
	public Date getSaveDate(){
		return saveDate;
	}
}
