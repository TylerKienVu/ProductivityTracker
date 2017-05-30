package common;

import java.io.Serializable;
import java.security.InvalidParameterException;

public class Task implements Serializable{
	private static final long serialVersionUID = 9119835871674844610L;
	private String name;
	private String description;
	private int[] frequency;
	public Task(String name,String desc,int[] frequencies){
		if(frequencies.length != 7){
			throw new InvalidParameterException("frequency list length != 7");
		}
		for(int i = 0; i < 7; i++){
			if(!(frequencies[i] == 0 || frequencies[i] == 1)){
				throw new InvalidParameterException("frequency list does not contain only 0 and 1");
			}
		}
		this.name = name;
		this.description = desc;
		this.frequency = frequencies;
	}
	public String toString(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setFrequency(int[] frequencies){
		if(frequencies.length != 7){
			throw new InvalidParameterException("frequency list length != 7");
		}
		for(int i = 0; i < 7; i++){
			if(!(frequencies[i] == 0 || frequencies[i] == 1)){
				throw new InvalidParameterException("frequency list does not contain only 0 and 1");
			}
		}
		frequency = frequencies;
	}
	public void setDescription(String desc){
		description = desc;
	}
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public int[] getFrequency(){
		return frequency;
	}
}
