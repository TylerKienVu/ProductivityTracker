package views;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;

import common.*;

import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//todo:
//check if current day is different from saved day
//calculate streak
//deleting tasks
//junit tests
//saving current complete and incomplete for the day

@SuppressWarnings("serial")
public class PT_GUI extends JFrame {

	private JPanel contentPane;
	private JList<Task> lstIncompleteTasks;
	private JTextField txtTaskName;
	private JList<Task> lstCompleteTasks;
	private JProgressBar progressBar;
	private JTextArea txtaTaskDescription;
	private JCheckBox chckbxMonday;
	private JCheckBox chckbxTuesday;
	private JCheckBox chckbxWednesday;
	private JCheckBox chckbxThursday;
	private JCheckBox chckbxFriday;
	private JCheckBox chckbxSaturday;
	private JCheckBox chckbxSunday;
	private JButton btnAddTask;
	private JButton button;
	private JLabel lblCurrentStreak;
	
	//Componenet Data Management
	////////////////////////////////
	private DefaultListModel<Task> incompleteTaskList = new DefaultListModel<Task>();
	private DefaultListModel<Task> completeTaskList = new DefaultListModel<Task>();
	private int taskCount;
	private int currentStreak;
	///////////////////////////////

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PT_GUI frame = new PT_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PT_GUI() {
		File f = new File("src/resources/data/date.ser");
		//if date of last access exists, check if next day and init saved data
		if(f.exists() && !f.isDirectory()) { 
			if(isNewDay()){
				System.out.println("It is a new day!");
				//new day so reset tasks to incomplete
				cacheDate();
			}
			else{
				System.out.println("It isn't a new day..");
				//load saved tasks from previous session
				cacheDate();
			}
		}
		//first time opening application
		else{
			System.out.println("First time opening app");
			cacheDate();
		}
		
		initComponents();
		createEvents();
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents(){
		setIconImage(Toolkit.getDefaultToolkit().getImage(PT_GUI.class.getResource("/resources/bars-chart-ascending.png")));
		setTitle("Productivity Tracker");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 600);
		contentPane = new JPanel();
		contentPane.setFocusable(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		//Initialize and sync both task lists to GUI
		lstCompleteTasks = new JList();
		lstCompleteTasks.setModel(completeTaskList);
		lstIncompleteTasks = new JList();
		lstIncompleteTasks.setModel(incompleteTaskList);
		initTaskList();
		
		///////////////////////////////////////
		/////// Window Builder Layout Code
		/////////////////////////////////////
		JTabbedPane tp_Menu = new JTabbedPane(JTabbedPane.TOP);
		tp_Menu.setBorder(null);
		tp_Menu.setFocusable(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tp_Menu, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tp_Menu, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
		);
						
		JPanel pnl_Productivity = new JPanel();
		pnl_Productivity.setBorder(null);
		pnl_Productivity.setFocusable(false);
		tp_Menu.addTab("Productivity", new ImageIcon(PT_GUI.class.getResource("/resources/graphic-progression.png")), pnl_Productivity, "Check your productivity");

		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(50, 205, 50));
		progressBar.setFocusable(false);
		
		JLabel lblIncompleteTasks = new JLabel("Incomplete Tasks");
		lblIncompleteTasks.setFont(new Font("Rockwell", Font.PLAIN, 16));
		lblIncompleteTasks.setForeground(Color.RED);
		lblIncompleteTasks.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncompleteTasks.setFocusable(false);
		
		JScrollPane spIncompleteTasks = new JScrollPane();
		
		JLabel lblCompleteTasks = new JLabel("Complete Tasks");
		lblCompleteTasks.setFont(new Font("Rockwell", Font.PLAIN, 16));
		lblCompleteTasks.setForeground(new Color(50, 205, 50));
		
		JScrollPane spCompleteTasks = new JScrollPane();
		
		button = new JButton("");
		button.setFocusable(false);
		button.setIcon(new ImageIcon(PT_GUI.class.getResource("/resources/switch.png")));
		
		JLabel lblProductivityForThe = new JLabel("Productivity for the Day");
		lblProductivityForThe.setFont(new Font("Rockwell", Font.PLAIN, 17));
		
		lblCurrentStreak = new JLabel("Current Streak: 0");
		lblCurrentStreak.setFont(new Font("Rockwell", Font.PLAIN, 16));
		GroupLayout gl_pnl_Productivity = new GroupLayout(pnl_Productivity);
		gl_pnl_Productivity.setHorizontalGroup(
			gl_pnl_Productivity.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_Productivity.createSequentialGroup()
					.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_Productivity.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.TRAILING)
								.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
								.addGroup(gl_pnl_Productivity.createSequentialGroup()
									.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.LEADING)
										.addComponent(lblIncompleteTasks)
										.addComponent(spIncompleteTasks, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
									.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCompleteTasks)
										.addComponent(spCompleteTasks, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_pnl_Productivity.createSequentialGroup()
							.addGap(89)
							.addComponent(lblProductivityForThe))
						.addGroup(gl_pnl_Productivity.createSequentialGroup()
							.addGap(115)
							.addComponent(lblCurrentStreak)))
					.addContainerGap())
				.addGroup(gl_pnl_Productivity.createSequentialGroup()
					.addContainerGap(137, Short.MAX_VALUE)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addGap(130))
		);
		gl_pnl_Productivity.setVerticalGroup(
			gl_pnl_Productivity.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_Productivity.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblProductivityForThe)
					.addGap(18)
					.addComponent(lblCurrentStreak)
					.addGap(29)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIncompleteTasks)
						.addComponent(lblCompleteTasks))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnl_Productivity.createParallelGroup(Alignment.LEADING)
						.addComponent(spCompleteTasks, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
						.addComponent(spIncompleteTasks, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(107))
		);
		

		
		
		lstCompleteTasks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lstCompleteTasks.setFocusable(false);

		lstCompleteTasks.setSelectionBackground(new Color(50, 205, 50));
		spCompleteTasks.setViewportView(lstCompleteTasks);
		

		
		lstIncompleteTasks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lstIncompleteTasks.setSelectionBackground(Color.RED);
		spIncompleteTasks.setViewportView(lstIncompleteTasks);
		lstIncompleteTasks.setFocusable(false);
		pnl_Productivity.setLayout(gl_pnl_Productivity);

		JPanel pnl_AddTask = new JPanel();
		pnl_AddTask.setBorder(null);
		pnl_AddTask.setFocusable(false);
		tp_Menu.addTab("Add Task", new ImageIcon(PT_GUI.class.getResource("/resources/add.png")), pnl_AddTask, "Add a task");
		
		JLabel lblTaskName = new JLabel("Task Name:");
		lblTaskName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		txtTaskName = new JTextField();
		txtTaskName.setColumns(10);
		
		JLabel lblTaskWeeklyFrequency = new JLabel("Task Weekly Frequency");
		lblTaskWeeklyFrequency.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		chckbxMonday = new JCheckBox("Monday");
		chckbxMonday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxMonday.setFocusable(false);
		
		chckbxTuesday = new JCheckBox("Tuesday");
		chckbxTuesday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxTuesday.setFocusable(false);
		
		chckbxWednesday = new JCheckBox("Wednesday");
		chckbxWednesday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxWednesday.setFocusable(false);
		
		chckbxThursday = new JCheckBox("Thursday");
		chckbxThursday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxThursday.setFocusable(false);
		
		chckbxFriday = new JCheckBox("Friday");
		chckbxFriday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxFriday.setFocusable(false);
		
		chckbxSaturday = new JCheckBox("Saturday");
		chckbxSaturday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxSaturday.setFocusable(false);
		
		chckbxSunday = new JCheckBox("Sunday");
		chckbxSunday.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chckbxSunday.setFocusable(false);
		
		JLabel lblTaskDescription = new JLabel("Task Description:");
		lblTaskDescription.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JScrollPane spDescription = new JScrollPane();
		
		btnAddTask = new JButton("Add Task");
		btnAddTask.setFocusable(false);
		
		GroupLayout gl_pnl_AddTask = new GroupLayout(pnl_AddTask);
		gl_pnl_AddTask.setHorizontalGroup(
			gl_pnl_AddTask.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_AddTask.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_AddTask.createParallelGroup(Alignment.LEADING)
						.addComponent(spDescription, GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
						.addGroup(gl_pnl_AddTask.createSequentialGroup()
							.addComponent(lblTaskName)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtTaskName, GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE))
						.addComponent(lblTaskDescription)
						.addComponent(chckbxSunday)
						.addComponent(chckbxSaturday)
						.addComponent(chckbxFriday)
						.addComponent(chckbxThursday)
						.addComponent(chckbxWednesday)
						.addComponent(chckbxTuesday)
						.addComponent(chckbxMonday)
						.addComponent(lblTaskWeeklyFrequency)
						.addComponent(btnAddTask, Alignment.TRAILING))
					.addContainerGap())
		);
		gl_pnl_AddTask.setVerticalGroup(
			gl_pnl_AddTask.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_AddTask.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl_AddTask.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTaskName)
						.addComponent(txtTaskName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(lblTaskDescription)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spDescription, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTaskWeeklyFrequency)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxMonday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxTuesday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxWednesday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxThursday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxFriday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxSaturday)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxSunday)
					.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
					.addComponent(btnAddTask)
					.addContainerGap())
		);
		
		txtaTaskDescription = new JTextArea();
		txtaTaskDescription.setLineWrap(true);
		spDescription.setViewportView(txtaTaskDescription);
		
		pnl_AddTask.setLayout(gl_pnl_AddTask);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void createEvents(){
		//Pressing switch button
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<Task> switchIncomplete = lstIncompleteTasks.getSelectedValuesList();
				List<Task> switchComplete = lstCompleteTasks.getSelectedValuesList();
				if(switchIncomplete.size() != 0){
					for(int i = 0; i < switchIncomplete.size();i++){
						completeTaskList.addElement(switchIncomplete.get(i));
						incompleteTaskList.removeElement(switchIncomplete.get(i));
					}
				}
				else if(switchComplete.size() != 0){
					for(int i = 0; i < switchComplete.size();i++){
						incompleteTaskList.addElement(switchComplete.get(i));
						completeTaskList.removeElement(switchComplete.get(i));
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "You must make a selection");
				}
				calculateProgress();
			}
		});
		
		//Selecting Incomplete list
		lstIncompleteTasks.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lstCompleteTasks.clearSelection();
			}
		});
		
		//Selecting Complete list
		lstCompleteTasks.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lstIncompleteTasks.clearSelection();
			}
		});
		
		//Creating Task button clicked
		btnAddTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtTaskName.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "\"Task Name\" must be filled out");
					return;
				}
				FileWriter output = null;
				BufferedWriter bufWrite = null;
				try {
					output = new FileWriter("src/resources/tasks",true);
					bufWrite = new BufferedWriter(output);
					int[] frequency = new int[7];
					frequency[0] = chckbxMonday.isSelected() ? 1 : 0;
					frequency[1] = chckbxTuesday.isSelected() ? 1 : 0;
					frequency[2] = chckbxWednesday.isSelected() ? 1 : 0;
					frequency[3] = chckbxThursday.isSelected() ? 1 : 0;
					frequency[4] = chckbxFriday.isSelected() ? 1 : 0;
					frequency[5] = chckbxSaturday.isSelected() ? 1 : 0;
					frequency[6] = chckbxSunday.isSelected() ? 1 : 0;
					

					String toWrite = txtTaskName.getText() + ":" + txtaTaskDescription.getText() + ":" + Arrays.toString(frequency) + "\n";
					bufWrite.write(toWrite);
					incompleteTaskList.addElement(new Task(txtTaskName.getText(),txtaTaskDescription.getText(),frequency));
					clearCreateTask();
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally{
					try{
						if(bufWrite != null){
							bufWrite.close();
						}
						if(output != null){
							output.close();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
		});
	}
	private void clearCreateTask(){
		txtTaskName.setText(null);
		txtaTaskDescription.setText(null);
		chckbxMonday.setSelected(false);
		chckbxTuesday.setSelected(false);
		chckbxWednesday.setSelected(false);
		chckbxThursday.setSelected(false);
		chckbxFriday.setSelected(false);
		chckbxSaturday.setSelected(false);
		chckbxSunday.setSelected(false);
	}
	//this method should only be called if new day or no save data exists
	private void initTaskList(){
		FileReader input = null;
		BufferedReader bufRead = null;
		try{
			input = new FileReader("src/resources/tasks");
			bufRead = new BufferedReader(input);
			String myLine = null;
			while ( (myLine = bufRead.readLine()) != null)
			{    
				taskCount++;
				String[] splitLine = myLine.split(":");
				
				String[] stringFrequencies = splitLine[2].replace("[","").replace("]","").replace(" ","").split(",");
				int[] frequency = new int[7];
				//convert to int[] array
				for(int i = 0;i < 7;i++){
					frequency[i] = Integer.parseInt(stringFrequencies[i]);
				}
				
				incompleteTaskList.addElement(new Task(splitLine[0],splitLine[1],frequency));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(bufRead != null){
					bufRead.close();
				}
				if(input != null){
					input.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	private void calculateProgress(){
		double completeSize = (double) completeTaskList.getSize();
		double calc = (completeSize/taskCount)* 100;
		progressBar.setValue((int) calc);
	}
	private void cacheDate(){
		WriteObject obj = new WriteObject();
		DateSerial date = new DateSerial();
		obj.serializeDateSerial(date);
	}
	private Boolean isNewDay(){
		ReadObject obj = new ReadObject();
		DateSerial date = obj.deserialzeDateSerial("src/resources/data/date.ser");
		return compareDay(date);
	}
	//if it is the same day, returns false
	private Boolean compareDay(DateSerial cachedDate){
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Date currentDate = new Date();
		cal1.setTime(cachedDate.getSaveDate());
		cal2.setTime(currentDate);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		return !sameDay;
	}
}
