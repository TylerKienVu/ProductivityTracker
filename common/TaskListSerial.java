package common;

import java.io.Serializable;

import javax.swing.DefaultListModel;


public class TaskListSerial implements Serializable{
	private static final long serialVersionUID = -6324420378434632071L;
	//current date saved
	private Task[] saveTaskList;
	
	public TaskListSerial(Task[] lst){
		saveTaskList = lst;
	}
	
	public Task[] getSaveTaskList(){
		return saveTaskList;
	}
}
