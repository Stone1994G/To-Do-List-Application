package CSE360_GUI_Priority_list;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.w3c.dom.events.EventException;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI
{
	int[] normalYearDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	int[] leapYearDays = {0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	static boolean deleteMouseActions = false;
	static boolean reportMouseActions = false;
	static boolean changeDisplayMouseActions = false;
	static boolean completeMouseActions = false;
	static boolean displayMouseActions = false;
	ArrayList<Entry> EntryList = new ArrayList<Entry>();
	//private boolean displayMouseActions = false;
	private JFrame frame;
	private JTextField Description_TextField;
	private JTextField Priority_TextField;
	private JTextField Due_Date;
	private JTextField textField_4;
	private JTextField Status_TextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame. This is where the ulk of the code is
	 */
	private void initialize()
	{
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.RED);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 1024, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JList entList = new JList();
		
		
		
		
		
		JInternalFrame DeleteFrame = new JInternalFrame("Delete Entry");
		DeleteFrame.setVisible(false);
		DeleteFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(DeleteFrame);
		DeleteFrame.getContentPane().setLayout(null);
		
		JList Delete_List = new JList();
		Delete_List.setToolTipText("");
		Delete_List.setBounds(0, 48, 576, 207);
		DeleteFrame.getContentPane().add(Delete_List);
		
		JLabel label = new JLabel("All current tasks:");
		label.setBounds(15, 16, 121, 20);
		DeleteFrame.getContentPane().add(label);
		
		JScrollPane deleteScroller = new JScrollPane(Delete_List);
		deleteScroller.setBounds(0, 50, 600, 200);
		DeleteFrame.getContentPane().add(deleteScroller);

		
		JScrollPane displayScroller = new JScrollPane(entList);
		displayScroller.setBounds(0,50,600,200);
		
		
		JInternalFrame DisplayFrame = new JInternalFrame("Current Active Entries");
		DisplayFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(DisplayFrame);
		DisplayFrame.getContentPane().setLayout(null);
		DisplayFrame.getContentPane().add(displayScroller);
		//DisplayFrame.getContentPane().add(new JScrollPane(entList));
		
		JLabel sortingOptions = new JLabel("Sort by: ");
		sortingOptions.setHorizontalAlignment(SwingConstants.CENTER);
		sortingOptions.setBounds(210, 0, 121, 20);
		DisplayFrame.getContentPane().add(sortingOptions);
		
		
		JButton sortByDescriptionDisplay = new JButton("Description");
		sortByDescriptionDisplay.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles the sorting of the items in the EntryList on the display frame when the user clicks on the sort by description
			 * @param E E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent E)
			{
				String[] tomoDesuArray = new String[EntryList.size()];
				String[] desuArray = new String[EntryList.size()];
				for(int i = 0; i < EntryList.size(); i++) {
					desuArray[i] = EntryList.get(i).get_description();
					tomoDesuArray[i] = EntryList.get(i).get_description().toUpperCase();
				}
				
				for(int i = 0; i < EntryList.size() - 1; i++)
				{
					for(int j = i + 1; j < EntryList.size(); j++)
					{
						int mainIndex = 0;
						int maxLoop = 0;
						if(tomoDesuArray[i].length() < tomoDesuArray[j].length())
						{
							maxLoop = tomoDesuArray[i].length();
						}
						else {
							maxLoop = tomoDesuArray[j].length();
						}
						while(mainIndex < maxLoop)
						{
							int difference = tomoDesuArray[i].charAt(mainIndex) - tomoDesuArray[j].charAt(mainIndex);
							if(difference > 0)
							{
								String tempDes = desuArray[i];
								desuArray[i] = desuArray[j];
								desuArray[j] = tempDes;
								String tempTomoDes = tomoDesuArray[i];
								tomoDesuArray[i] = tomoDesuArray[j];
								tomoDesuArray[j] = tempTomoDes;
								break;
							}
							else if(difference < 0)
							{
								break;
							}
							mainIndex++;
						}
					}
				}
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					for(int j = 0; j < EntryList.size(); j++)
					{
						if(desuArray[i].contentEquals(EntryList.get(j).get_description()))
						{
							if((EntryList.get(j).isItDeleted() == false) && (EntryList.get(j).isItCompleted() == false))
							{
								listModel.addElement(EntryList.get(j).toString());
							}
						}
					}
				}
				
				entList.setModel(listModel);
				
				entList.setToolTipText("");
				entList.setBounds(0, 48, 576, 207);
				
				MouseListener displayMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) entList.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && displayMouseActions == false)
				{
					entList.addMouseListener(displayMouseListener);
					displayMouseActions = true;
				}
			}
		});
		sortByDescriptionDisplay.setBounds(28, 25, 121, 20);
		DisplayFrame.getContentPane().add(sortByDescriptionDisplay);

		JButton sortByPriorityDisplay = new JButton("Priority");
		sortByPriorityDisplay .setHorizontalAlignment(SwingConstants.CENTER);
		sortByPriorityDisplay.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles the sorting of the items in the EntryList on the display frame when the user clicks on the sort by priority.
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent E)
			{
				int[] desuArray = new int[EntryList.size()];
				for(int i = 0; i < EntryList.size(); i++) {
					desuArray[i] = EntryList.get(i).get_priority();
				}
				
				for(int i = 0; i < EntryList.size() - 1; i++)
				{
					for(int j = i + 1; j < EntryList.size(); j++)
					{
							if(desuArray[i] > desuArray[j])
							{
								int tempDes = desuArray[i];
								desuArray[i] = desuArray[j];
								desuArray[j] = tempDes;
							}
						
					}
				}
				
				DefaultListModel listModel = new DefaultListModel();
				int previousNum = 0;
				for(int i = 0; i < EntryList.size(); i++)
				{
					if(desuArray[i] != previousNum)
					{
						for(int j = 0; j < EntryList.size(); j++)
						{
							if(desuArray[i] == EntryList.get(j).get_priority())
							{
								if(EntryList.get(j).deleted != true)
								{
									if(EntryList.get(j).completed != true)
									{
										listModel.addElement(EntryList.get(j).toString());
									}
									previousNum = desuArray[i];
									
								}
							}
						}
					}
					
				}
				
				entList.setModel(listModel);
				
				entList.setToolTipText("");
				entList.setBounds(0, 48, 576, 207);
				
				MouseListener displayMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) entList.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && displayMouseActions == false)
				{
					entList.addMouseListener(displayMouseListener);
					displayMouseActions = true;
				}
			}
		});
		sortByPriorityDisplay .setBounds(163, 25, 121, 20);
		DisplayFrame.getContentPane().add(sortByPriorityDisplay );
		
		
		
		JButton sortByDueDateDisplay = new JButton("Due Date");
		sortByDueDateDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		sortByDueDateDisplay.setBounds(296, 25, 121, 20);
		sortByDueDateDisplay.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles the sorting of the items in the EntryList on the display frame when the user clicks on the sort by due date
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent E)
			{
				String[] desuArray = new String[EntryList.size()];
				for(int i = 0; i < EntryList.size(); i++) {
					desuArray[i] = EntryList.get(i).get_due_date();
				}
				
				for(int i = 0; i < EntryList.size() - 1; i++)
				{
					for(int j = i + 1; j < EntryList.size(); j++)
					{
						boolean switched = false;
						
							if((Integer.parseInt(desuArray[i].substring(6))) > (Integer.parseInt(desuArray[j].substring(6))))
							{
								String tempDes = desuArray[i];
								desuArray[i] = desuArray[j];
								desuArray[j] = tempDes;
								switched = true;
							}
							
							else if((Integer.parseInt(desuArray[i].substring(0, 2))) > (Integer.parseInt(desuArray[j].substring(0, 2))))
							{
								if(switched == false)
								{
									String tempDes = desuArray[i];
									desuArray[i] = desuArray[j];
									desuArray[j] = tempDes;
									switched = true;
								}
								
								
							}
							
							else if((Integer.parseInt(desuArray[i].substring(3, 5))) > (Integer.parseInt(desuArray[j].substring(3, 5))))
							{
								if(switched == false)
								{
									String tempDes = desuArray[i];
									desuArray[i] = desuArray[j];
									desuArray[j] = tempDes;
								}
								
							}	
						
					}
				}
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					for(int j = 0; j < EntryList.size(); j++)
					{
						if(desuArray[i] == EntryList.get(j).get_due_date())
						{
							if((EntryList.get(j).isItDeleted() == false) && (EntryList.get(j).isItCompleted() == false))
							{
								listModel.addElement(EntryList.get(j).toString());
							}
						}
					}
				}
				
				entList.setModel(listModel);
				
				entList.setToolTipText("");
				entList.setBounds(0, 48, 576, 207);
				
				MouseListener displayMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) entList.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && displayMouseActions == false)
				{
					entList.addMouseListener(displayMouseListener);
					displayMouseActions = true;
				}
			}
		});
		DisplayFrame.getContentPane().add(sortByDueDateDisplay);
		
		JButton sortByStatusDisplay = new JButton("Status");
		sortByStatusDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		sortByStatusDisplay.setBounds(432, 25, 121, 20);
		sortByStatusDisplay.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles the sorting of the items in the EntryList on the display frame when the user clicks on the sort by status
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent E)
			{
				ArrayList<Entry> finalEntryList = new ArrayList<Entry>();
				for(int i = 0; i < EntryList.size(); i++)
				{
					finalEntryList.add(EntryList.get(i));
				}
				String[] desuArray = new String[EntryList.size()];
				for(int i = 0; i < EntryList.size(); i++) {
					desuArray[i] = EntryList.get(i).get_status();
				}
				
				for(int i = 0; i < EntryList.size() - 1; i++)
				{
					for(int j = i + 1; j < EntryList.size(); j++)
					{
							if((finalEntryList.get(i).get_status().contentEquals("Finished")) && (finalEntryList.get(j).get_status().contentEquals("Not started")))
							{
								Entry tempDes = new Entry();
								tempDes = finalEntryList.get(i);
								finalEntryList.set(i, finalEntryList.get(j));
								finalEntryList.set(j, tempDes);
							}
							if((finalEntryList.get(i).get_status().contentEquals("Finished")) && (finalEntryList.get(j).get_status().contentEquals("In Progress")))
							{
								Entry tempDes = new Entry();
								tempDes = finalEntryList.get(i);
								finalEntryList.set(i, finalEntryList.get(j));
								finalEntryList.set(j, tempDes);
							}
							if((finalEntryList.get(i).get_status().contentEquals("In Progress")) && (finalEntryList.get(j).get_status().contentEquals("Not started")))
							{
								Entry tempDes = new Entry();
								tempDes = finalEntryList.get(i);
								finalEntryList.set(i, finalEntryList.get(j));
								finalEntryList.set(j, tempDes);
							}
					}
				}
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					for(int j = 0; j < EntryList.size(); j++)
					{
						if(finalEntryList.get(i).get_description().contentEquals(EntryList.get(j).get_description()))
						{
							if((EntryList.get(j).isItDeleted() == false) && (EntryList.get(j).isItCompleted() == false))
							{
								listModel.addElement(EntryList.get(j).toString());
							}
						}
					}
				}
				
				entList.setModel(listModel);
				
				entList.setToolTipText("");
				entList.setBounds(0, 48, 576, 207);
				
				MouseListener displayMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) entList.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && displayMouseActions == false)
				{
					entList.addMouseListener(displayMouseListener);
					displayMouseActions = true;
				}
			}
		});
		DisplayFrame.getContentPane().add(sortByStatusDisplay);
		
		DisplayFrame.setVisible(true);
		
		JInternalFrame addFrame = new JInternalFrame("Add Entry");
		addFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(addFrame);
		addFrame.getContentPane().setLayout(null);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setBounds(30, 57, 121, 20);
		addFrame.getContentPane().add(lblDescription);
		
		JLabel lblPriority = new JLabel("Priority");
		lblPriority.setBounds(30, 93, 121, 20);
		addFrame.getContentPane().add(lblPriority);
		
		JLabel lblDueDate = new JLabel("Due Date:");
		lblDueDate.setBounds(400, 27, 121, 20);
		addFrame.getContentPane().add(lblDueDate);
		
		JLabel lblDueDateDay = new JLabel("Day:");
		lblDueDateDay.setBounds(330, 57, 121, 20);
		addFrame.getContentPane().add(lblDueDateDay);
		
		JTextField DueDateDays_TextField = new JTextField();
		DueDateDays_TextField.setBounds(380, 57, 146, 26);
		addFrame.getContentPane().add(DueDateDays_TextField);
		DueDateDays_TextField.setColumns(10);
		
		JLabel lblDueDateMonth = new JLabel("Month: ");
		lblDueDateMonth.setBounds(330, 93, 121, 20);
		addFrame.getContentPane().add(lblDueDateMonth);
		
		JLabel lblDueDateYear = new JLabel("Year:");
		lblDueDateYear.setBounds(330, 129, 121, 20);
		addFrame.getContentPane().add(lblDueDateYear);
		
		JTextField DueDateYears_TextField = new JTextField();
		DueDateYears_TextField.setBounds(380, 129, 146, 26);
		addFrame.getContentPane().add(DueDateYears_TextField);
		DueDateYears_TextField.setColumns(10);
		
		String[] monthArray = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		
		JComboBox chooseMonth = new JComboBox(monthArray);
		chooseMonth.setBounds(380, 93, 121, 20);
		addFrame.getContentPane().add(chooseMonth);
		
		String[] statusOptions = {"Not started", "In progress", "Finished"};
		
		
		Description_TextField = new JTextField();
		Description_TextField.setBounds(130, 52, 146, 26);
		addFrame.getContentPane().add(Description_TextField);
		Description_TextField.setColumns(10);
		
		Priority_TextField = new JTextField();
		Priority_TextField.setBounds(130, 90, 146, 26);
		addFrame.getContentPane().add(Priority_TextField);
		Priority_TextField.setColumns(10);
		
		JButton Entry_submit = new JButton("Add Entry");
		Entry_submit.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles the options the user has to create and add an entry to the EntryList
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
				Entry entry = new Entry();
				
				int maxDays = 0;
				int currentYear = 0;
				int currentMonth = 0;
				
				
				boolean found = false;
				boolean isANum = true;
				
				
				if((Description_TextField.getText().length() == 0) || Priority_TextField.getText().length() == 0 || DueDateDays_TextField.getText().length() == 0|| DueDateYears_TextField.getText().length() == 0)
				{
					JOptionPane.showMessageDialog(frame, "At least one text field was empty. Please provide information for every field.");
					return;
				}
				
				for(int i = 0; i < EntryList.size(); i++)
				{
					if((EntryList.get(i).isItCompleted() == false) && (EntryList.get(i).isItDeleted() == false))
					{
						if((Description_TextField.getText().contentEquals(EntryList.get(i).get_description())) && found == false)
						{
							JOptionPane.showMessageDialog(frame, "That exact description is already taken.");
							found = true;
						}
						if((Priority_TextField.getText().contentEquals(Integer.toString(EntryList.get(i).get_priority()))) && found == false)
						{
							JOptionPane.showMessageDialog(frame, "That priority number is already taken.");
							found = true;
						}
					}
					
				}
				
				if(found == false && isANum == true)
				{
					maxDays = 0;
					currentYear = Integer.parseInt(DueDateYears_TextField.getText());
					currentMonth = 0;
					
					for(int i = 0; i < monthArray.length; i++)
					{
						if(chooseMonth.getSelectedItem().toString().contentEquals(monthArray[i]))
						{
							currentMonth = i + 1;
						}
					}
					
					if(currentYear % 4 == 0)
					{
						maxDays = leapYearDays[currentMonth];
					}
					else {
						maxDays = normalYearDays[currentMonth];
					}
					
					String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "!", "~", "`", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "{", "}", "[", "]", "|", "<", ">", ",", ".", "?", "/", ":", ";", "'"};
					for(int i = 0; i < Priority_TextField.getText().length(); i++)
					{
						if(isANum == false)
						{
							break;
						}
						
						for(int j = 0; j < 56; j++)
						{
							if(i == Priority_TextField.getText().length()-1)
							{
								if(Priority_TextField.getText().substring(i).contentEquals(alphabet[j]))
								{
									isANum = false;
								}
							}
							else {
								if(Priority_TextField.getText().substring(i, i+1).contentEquals(alphabet[j]))
								{
									isANum = false;
								}
							}
						}
					}
				}
				
				
				if(isANum == false || Integer.parseInt(Priority_TextField.getText()) < 1)
				{
					JOptionPane.showMessageDialog(frame, "Please enter an integer greater than zero as your priority number.");
					isANum = false;
				}
				
				if(isANum == true && found == false && Integer.parseInt(DueDateYears_TextField.getText()) < 2019)
				{
					JOptionPane.showMessageDialog(frame, "Please enter a year greater than or equal to the current year.");
					found = true;
					isANum = false;
				}
				if(isANum == true && found == false && ((Integer.parseInt(DueDateDays_TextField.getText()) > maxDays) || (Integer.parseInt(DueDateDays_TextField.getText()) < 1)))
				{
					JOptionPane.showMessageDialog(frame, "Enter a valid number for the day. Note, leap years are taken into account.");
					found = true;
					isANum = false;
				}
				
				
				if(found == false && isANum == true) {
					entry.set_description(Description_TextField.getText());
					entry.set_priority(Integer.parseInt(Priority_TextField.getText()));
					String dateString = "";
					if(currentMonth < 10)
					{
						dateString = dateString + "0" + currentMonth + "/";
					}
					else {
						dateString = dateString + currentMonth + "/";
					}
					if(Integer.parseInt(DueDateDays_TextField.getText()) < 10)
					{
						dateString = dateString + "0" + Integer.parseInt(DueDateDays_TextField.getText()) + "/";
					}
					else {
						dateString = dateString + Integer.parseInt(DueDateDays_TextField.getText()) + "/";
					}
					dateString += currentYear;
					entry.set_due_date(dateString);
				}
				
				
				
				if(found == false && isANum == true)
				{
					EntryList.add(entry);
					frame.repaint();
				}
				
				
				}
				catch (EventException bugblock)
				{
					
				}
				
			}
			
		});
		Entry_submit.setBounds(372, 210, 115, 29);
		addFrame.getContentPane().add(Entry_submit);

		
		addFrame.setVisible(false);
		
		JInternalFrame reportFrame = new JInternalFrame("Print Report");
		reportFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(reportFrame);
		reportFrame.getContentPane().setLayout(null);
		
		JList Report_List = new JList();
		Report_List.setToolTipText("");
		Report_List.setBounds(0, 48, 576, 207);
		reportFrame.getContentPane().add(Report_List);
		
		JLabel label_12 = new JLabel("Every Task Added:");
		label_12.setBounds(15, 16, 121, 20);
		reportFrame.getContentPane().add(label_12);
		
		JScrollPane reportScroller = new JScrollPane(Report_List);
		reportScroller.setBounds(0, 50, 600, 200);
		reportFrame.getContentPane().add(reportScroller);
		
		JInternalFrame completeFrame = new JInternalFrame("Complete Entry");
		completeFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(completeFrame);
		completeFrame.getContentPane().setLayout(null);
		
		JList Complete_List = new JList();
		Complete_List.setToolTipText("");
		Complete_List.setBounds(0, 48, 576, 207);
		completeFrame.getContentPane().add(Complete_List);
		
		JLabel label_8 = new JLabel("All current tasks:");
		label_8.setBounds(15, 16, 121, 20);
		completeFrame.getContentPane().add(label_8);
		
		JScrollPane completeScroller = new JScrollPane(Complete_List);
		completeScroller.setBounds(0, 50, 600, 200);
		completeFrame.getContentPane().add(completeScroller);
		
		JInternalFrame changeFrame = new JInternalFrame("Change Entry");
		changeFrame.setBounds(395, 48, 592, 296);
		frame.getContentPane().add(changeFrame);
		changeFrame.getContentPane().setLayout(null);
		
		JList Change_List = new JList();
		Change_List.setToolTipText("");
		Change_List.setBounds(0, 48, 576, 207);
		changeFrame.getContentPane().add(Change_List);
		
		JLabel label_4 = new JLabel("All current tasks:");
		label_4.setBounds(15, 16, 121, 20);
		changeFrame.getContentPane().add(label_4);
		
		JScrollPane changeScroller = new JScrollPane(Change_List);
		changeScroller.setBounds(0, 50, 600, 200);
		changeFrame.getContentPane().add(changeScroller);
		
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setEditable(false);
		textField.setFont(new Font("Microsoft YaHei", Font.BOLD, 20));
		textField.setSize(new Dimension(50, 50));
		textField.setText("The Priority Tracker Application");
		textField.setBounds(0, 0, 421, 42);
		frame.getContentPane().add(textField);
		
		Button Add_Entry_Main = new Button("Add Entry");
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Not Started", "Started - (Start date)", "Finished - (Finish date)"}));
		comboBox.setBounds(362, 161, 146, 26);
		
		/**
		 * Add Entry Button
		 */
		Add_Entry_Main.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles the button confirming the entry the user has created, and putting it in the list of entries
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				addFrame.setVisible(true);
				DisplayFrame.setVisible(false);
				DeleteFrame.setVisible(false);
				changeFrame.setVisible(false);
				completeFrame.setVisible(false);
				reportFrame.setVisible(false);
				
			}
		});
		Add_Entry_Main.setBounds(15, 48, 184, 80);
		frame.getContentPane().add(Add_Entry_Main);
		
		Button button_2 = new Button("Complete Entry");
		button_2.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles what the user can see when they wish to complete an entry, and how they can interact with the page.
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				addFrame.setVisible(false);
				DisplayFrame.setVisible(false);
				DeleteFrame.setVisible(false);
				changeFrame.setVisible(false);
				completeFrame.setVisible(true);
				reportFrame.setVisible(false);
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					if((EntryList.get(i).isItDeleted() == false))
					{
						if((EntryList.get(i).isItCompleted() == false))
						{
							listModel.addElement(EntryList.get(i).toString());
						}
						
					}
				}
				
				
				Complete_List.setModel(listModel);
				

				Complete_List.setToolTipText("");
				Complete_List.setBounds(0, 48, 576, 207);
				
				MouseListener completeMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				    	int completedNum = 0;
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) Complete_List.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           int answer = JOptionPane.showConfirmDialog(frame, "Complete the following entry? " + selectedItem);
							if(answer == JOptionPane.YES_NO_OPTION)
							{
								try 
								{
									for(int i = EntryList.size()-1; i > -1; i--)
									{
										if(selectedItem.contentEquals(EntryList.get(i).toString()))
										{
											if(EntryList.get(i).isItDeleted() == false)
											{
												EntryList.get(i).complete();
												completedNum = EntryList.get(i).get_priority();
												break;
											}
										}
									}
									for(int i = 0; i < EntryList.size(); i++)
							        {
							        	if(EntryList.get(i).get_priority() > completedNum)
							        	{
							        		int tempNum = EntryList.get(i).get_priority() - 1;
							        		EntryList.get(i).set_priority(tempNum);
							        	}
							        }
								}
								catch(Exception exc)
								{
									exc.printStackTrace();
								}
								
							}
							else if(answer == JOptionPane.NO_OPTION || answer == JOptionPane.CANCEL_OPTION)
							{
								
							}
							Complete_List.updateUI();
							DefaultListModel listModel = new DefaultListModel();
							for(int i = 0; i < EntryList.size(); i++)
							{
								if((EntryList.get(i).isItDeleted() == false))
								{
									if((EntryList.get(i).isItCompleted() == false))
									{
										if(EntryList.get(i).get_priority() > 0)
										{
											listModel.addElement(EntryList.get(i).toString());
										}
									}
								}
							}
							
							
							
							Complete_List.setModel(listModel);
				         }
				       
				        
				    }
				};
				if(!EntryList.isEmpty() && completeMouseActions == false)
				{
					Complete_List.addMouseListener(completeMouseListener);
					completeMouseActions = true;
				}
			}
		});
		button_2.setBounds(15, 222, 184, 80);
		frame.getContentPane().add(button_2);

		
		Button button_3 = new Button("Display Entries");
		button_3.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles what the user can see when they wish to see all entries, and how they can interact with the page.
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e) 
			{
				SwingUtilities.updateComponentTreeUI(DisplayFrame);
//				DisplayFrame.invalidate();
//				DisplayFrame.validate();
				DisplayFrame.repaint();
				addFrame.setVisible(false);
				DisplayFrame.setVisible(true);
				//DisplayFrame.setUndecorated(true);
				DeleteFrame.setVisible(false);
				changeFrame.setVisible(false);
				completeFrame.setVisible(false);
				reportFrame.setVisible(false);
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					if((EntryList.get(i).isItDeleted() == false) && (EntryList.get(i).isItCompleted() == false))
					{
						listModel.addElement(EntryList.get(i).toString());
					}
				}
				
				entList.setModel(listModel);
				
				entList.setToolTipText("");
				entList.setBounds(0, 48, 576, 207);
				
				MouseListener displayMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) entList.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && displayMouseActions == false)
				{
					entList.addMouseListener(displayMouseListener);
					displayMouseActions = true;
				}
				
			}
		});
		button_3.setBounds(205, 48, 184, 80);
		frame.getContentPane().add(button_3);
		
		Button button_4 = new Button("Change Entry");
		button_4.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles what the user can see when they wish to alter an existing entry, and how they can interact with the page.
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				addFrame.setVisible(false);
				DisplayFrame.setVisible(false);
				DeleteFrame.setVisible(false);
				changeFrame.setVisible(true);
				completeFrame.setVisible(false);
				reportFrame.setVisible(false);
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					if((EntryList.get(i).isItDeleted() == false) && (EntryList.get(i).isItCompleted() == false))
					{
						listModel.addElement(EntryList.get(i).toString());
					}
				}
				
				
				
				Change_List.setModel(listModel);
				
				//entList.updateUI();
				String[] options = {"Exit", "Description", "Priority", "Due Date", "Status"};
				
				Change_List.setToolTipText("");
				Change_List.setBounds(0, 48, 576, 207);
				
				MouseListener changeMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				    	int tempPoint = 0;
				    	int loopingIndex = 0;
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) Change_List.getSelectedValue().toString();
				           StringList.add(selectedItem);
				        	   int choice = JOptionPane.showOptionDialog(frame, "Change Any of the Following:", "Change (Update)", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
					           if(choice == 0)
					           {
					        	   
					           }
					           if(choice == 1)
					           {
					        	   while(true)
					        	   {
					        		   if(selectedItem.charAt(loopingIndex) == '|')
					        		   {
					        			   tempPoint = loopingIndex - 1;
					        			   break;
					        		   }
					        		   loopingIndex++;
					        	   }
					        	   String newDes = JOptionPane.showInputDialog(frame, "Enter a new description:", selectedItem.substring(13, tempPoint));
					        	   if(newDes.length() == 0)
					        	   {
					        		   JOptionPane.showMessageDialog(frame, "You cannot enter a blank description.");
					        	   }
					        	   else {
					        		   int loopyIndex = 0;
					        		   boolean canUpdate = true;
					        		   for(int i = EntryList.size()-1; i > -1; i--)
										{
											if(selectedItem.contentEquals(EntryList.get(i).toString()))
											{
												loopyIndex = i;
											}
										}
					        		   for(int i = EntryList.size()-1; i > -1; i--)
										{
					        			   if(i != loopyIndex)
					        			   {
					        				   if(newDes.contentEquals(EntryList.get(i).get_description()))
												{
													JOptionPane.showMessageDialog(frame, "That description is already taken.");
													canUpdate = false;
												}
					        			   }
											
										}
					        		   if(canUpdate)
					        		   {
					        			   for(int i = EntryList.size()-1; i > -1; i--)
											{
												if(selectedItem.contentEquals(EntryList.get(i).toString()))
												{
													EntryList.get(i).set_description(newDes);
												}
											}
					        		   }
					        		   
					        	   }
					           }
					           else if(choice == 2)
					           {
					        	   boolean foul = true;
					        	   int displayNum = 0;
					        	   for(int i = EntryList.size()-1; i > -1; i--)
									{
										if(selectedItem.contentEquals(EntryList.get(i).toString()))
										{
											displayNum = EntryList.get(i).get_priority();
											break;
										}
									}
					        	   String newPri = JOptionPane.showInputDialog(frame, "Enter a new priority:", displayNum);
					        	   if(newPri.length() == 0)
					        	   {
					        		   JOptionPane.showMessageDialog(frame, "You cannot enter a blank priority number.");
					        	   }
					        	   else {
					        		   String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "!", "~", "`", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "{", "}", "[", "]", "|", "<", ">", ",", ".", "?", "/", ":", ";", "'", " "};
										for(int i = 0; i < newPri.length(); i++)
										{
											if(foul == false)
											{
												break;
											}
											
											for(int j = 0; j < 57; j++)
											{
												if(foul == false)
												{
													break;
												}
												if(i == newPri.length()-1)
												{
													if(newPri.substring(i).contentEquals(alphabet[j]))
													{
														foul = false;
													}
												}
												else {
													if(newPri.substring(i, i+1).contentEquals(alphabet[j]))
													{
														foul = false;
													}
												}
											}
										}
										if(foul == false || Integer.parseInt(newPri) < 1)
										{
											JOptionPane.showMessageDialog(frame, "Please enter an integer greater than zero as your priority number.");
											foul = false;
										}
										if(foul == true)
										{
											int dupeCount = 0;
											int dupeIndex[] = new int[2];
											for(int i = EntryList.size()-1; i > -1; i--)
											{
												if(Integer.parseInt(newPri) == EntryList.get(i).get_priority())
												{
													dupeIndex[dupeCount] = i;
													dupeCount++;
												}
											}
											for(int i = EntryList.size()-1; i > -1; i--)
											{
												if(selectedItem.contentEquals(EntryList.get(i).toString()))
												{
													int tempDupe = EntryList.get(i).get_priority();
													EntryList.get(i).set_priority(Integer.parseInt(newPri));
													if(!(selectedItem.contentEquals(EntryList.get(dupeIndex[0]).toString())))
													{
														EntryList.get(dupeIndex[0]).set_priority(tempDupe);
													}
													else {
														EntryList.get(dupeIndex[1]).set_priority(tempDupe);
													}
												}
											}
										}
					        	   
								}
								
					        	   
					           }
					           else if(choice == 3)
					           {
					        	   boolean mowl = true;
					        	   int mDays = 0;
								   int cYear = 0;
								   int cMonth = 0;
					        	   String newDate = "";
					        	   int newMonth = 0;
					        	   int newDay = 0;
					        	   int newYear = 0;
					        	   String chosenMonth = (String) JOptionPane.showInputDialog(frame, "Choose a new month", "Update Due Date", JOptionPane.QUESTION_MESSAGE, null, monthArray, monthArray[0]);
					        	   for(int i = 0; i < 12; i++)
					        	   {
					        		   if(chosenMonth.contentEquals(monthArray[i]))
					        		   {
					        			   newMonth = i + 1;
					        		   }
					        	   }
					        	   String chosenDay = JOptionPane.showInputDialog(frame, "Enter a new day:");
					        	   if(chosenDay.length() == 0)
					        	   {
					        		   JOptionPane.showMessageDialog(frame, "You cannot enter a blank day.");
					        	   }
					        	   else {
					        		   String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "!", "~", "`", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "{", "}", "[", "]", "|", "<", ">", ",", ".", "?", "/", ":", ";", "'", " "};
										for(int i = 0; i < chosenDay.length(); i++)
										{
											if(mowl == false)
											{
												break;
											}
											
											for(int j = 0; j < 57; j++)
											{
												if(mowl == false)
												{
													break;
												}
												if(i == chosenDay.length()-1)
												{
													if(chosenDay.substring(i).contentEquals(alphabet[j]))
													{
														mowl = false;
													}
												}
												else {
													if(chosenDay.substring(i, i+1).contentEquals(alphabet[j]))
													{
														mowl = false;
													}
												}
											}
										}
										if(mowl == false || Integer.parseInt(chosenDay) < 1)
										{
											JOptionPane.showMessageDialog(frame, "Please enter an appropriate integer for the day.");
											mowl = false;
										}
										if(mowl== true)
										{
											newDay = Integer.parseInt(chosenDay);
								        	   String chosenYear = JOptionPane.showInputDialog(frame, "Enter a new year:");
								        	   if(chosenYear.length() == 0)
								        	   {
								        		   JOptionPane.showMessageDialog(frame, "You cannot enter a blank year.");
								        	   }
								        	   else {
								        		   boolean yarl = true;
								        		   newYear = Integer.parseInt(chosenYear);
								        		   if(newYear < 2019)
									        	   {
									        		   JOptionPane.showMessageDialog(frame, "You cannot enter a year from the past.");
									        	   }
								        		   else{
								        			   String[] alphabeto = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "!", "~", "`", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "{", "}", "[", "]", "|", "<", ">", ",", ".", "?", "/", ":", ";", "'", " "};
														for(int i = 0; i < chosenDay.length(); i++)
														{
															if(yarl == false)
															{
																break;
															}
															
															for(int j = 0; j < 57; j++)
															{
																if(yarl == false)
																{
																	break;
																}
																if(i == chosenYear.length()-1)
																{
																	if(chosenYear.substring(i).contentEquals(alphabeto[j]))
																	{
																		yarl = false;
																	}
																}
																else {
																	if(chosenYear.substring(i, i+1).contentEquals(alphabeto[j]))
																	{
																		yarl = false;
																	}
																}
															}
														}
														if(yarl == false || Integer.parseInt(chosenYear) < 1)
														{
															JOptionPane.showMessageDialog(frame, "Please enter an appropriate integer for the year.");
															yarl = false;
														}
														if(yarl == true)
														{
															int maximumDay = 0;
															if(newYear % 4 == 0)
															{
																maximumDay = leapYearDays[newMonth];
															}
															else {
																maximumDay = normalYearDays[newMonth];
															}
															
															if(newDay > maximumDay || newDay < 1)
															{
																JOptionPane.showMessageDialog(frame, "Please enter an existing date. Leap years are taken into account.");
															}
															else {
																for(int i = EntryList.size()-1; i > -1; i--)
																{
																	if(selectedItem.contentEquals(EntryList.get(i).toString()))
																	{
																		if(newMonth < 10)
																		{
																			newDate = newDate + "0" + newMonth + "/";
																		}
																		else {
																			newDate = newDate + newMonth + "/";
																		}
																		if(newDay < 10)
																		{
																			newDate = newDate + "0" + newDay + "/";
																		}
																		else {
																			newDate = newDate + newDay + "/";
																		}
																		newDate = newDate + newYear;
																		EntryList.get(i).set_due_date(newDate);
																		break;
																	}
																}
															}
																
															
														}
								        		   }
									        	   
								        	   }
										}
								
					        		   
					        	   } 
					           }
					           else if(choice == 4)
					           {
					        	   String[] statusChoices = {"Not started", "In Progress", "Finished"};
					        	   String chosenStat = (String) JOptionPane.showInputDialog(frame, "Choose a new status", "Update Status", JOptionPane.QUESTION_MESSAGE, null, statusChoices, statusChoices[0]);
					        	   for(int i = EntryList.size()-1; i > -1; i--)
									{
										if(selectedItem.contentEquals(EntryList.get(i).toString()))
										{
											if(chosenStat.contentEquals("Finished"))
											{
												EntryList.get(i).finish();
											}
											else if (chosenStat.contentEquals("In Progress"))
											{
												EntryList.get(i).start();
											}
											else {
												EntryList.get(i).doOver();
											}
										}
									}
					           }
					           Change_List.updateUI();
								DefaultListModel listModel = new DefaultListModel();
								for(int i = 0; i < EntryList.size(); i++)
								{
									if((EntryList.get(i).isItDeleted() == false))
									{
										if((EntryList.get(i).isItCompleted() == false))
										{
											if(EntryList.get(i).get_priority() > 0)
											{
												listModel.addElement(EntryList.get(i).toString());
											}
										}
									}
								}
								
								
								
								Change_List.setModel(listModel);
				           
				         }
				    }
				};
				if(!EntryList.isEmpty() && changeDisplayMouseActions == false)
				{
					Change_List.addMouseListener(changeMouseListener);
					changeDisplayMouseActions = true;
				}
			}
		});
		button_4.setBounds(205, 136, 184, 80);
		frame.getContentPane().add(button_4);
		
		Button button_5 = new Button("Print Report");
		button_5.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles what the user can see when they wish to see all entries that have ever been created, and how they can interact with the page.
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<Entry> ReportList = new ArrayList<Entry>();
				for(int i = 0; i < EntryList.size(); i++)
				{
					ReportList.add(EntryList.get(i));
				}
				addFrame.setVisible(false);
				DisplayFrame.setVisible(false);
				DeleteFrame.setVisible(false);
				changeFrame.setVisible(false);
				completeFrame.setVisible(false);
				reportFrame.setVisible(true);
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					listModel.addElement(ReportList.get(i).toString());
				}
				
				
				
				Report_List.setModel(listModel);
				
				//entList.updateUI();
				
				Report_List.setToolTipText("");
				Report_List.setBounds(0, 48, 576, 207);
				
				MouseListener reportMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) Report_List.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           JOptionPane.showMessageDialog(frame, selectedItem);
				         }
				    }
				};
				if(!EntryList.isEmpty() && reportMouseActions == false)
				{
					Report_List.addMouseListener(reportMouseListener);
					reportMouseActions = true;
				}
			}
		});
		button_5.setBounds(205, 222, 184, 80);
		frame.getContentPane().add(button_5);
		
		Button button_6 = new Button("Quit");
		button_6.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles what happens when the user clicks on the button to quit the program
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent arg0)
			{
				frame.dispose();
			}
		});
		button_6.setBounds(547, 350, 184, 80);
		frame.getContentPane().add(button_6);
		
		Button button_7 = new Button("Restore");
		button_7.addActionListener(new ActionListener()
		{
			/**
			 * This actionPerformed handles what happens when the user wants to load in a list they created and saved before
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				int answer = JOptionPane.showConfirmDialog(frame, "Would you like to restore last save changes?");
				if(answer  == JOptionPane.YES_NO_OPTION)
				{
					try
					{
					File file = new File("EntryList");
					
					FileInputStream fin = new FileInputStream("EntryList");
					ObjectInputStream ois = new ObjectInputStream(fin);
					EntryList = (ArrayList<Entry>)ois.readObject();
					ois.close();
					DefaultListModel listModel = new DefaultListModel();
					for(int i = 0; i < EntryList.size(); i++)
					{
						if((EntryList.get(i).isItDeleted() == false))
						{
							if((EntryList.get(i).isItCompleted() == false))
							{
								if(EntryList.get(i).get_priority() > 0)
								{
									listModel.addElement(EntryList.get(i).toString());
								}
							}
						}
					}
					
					
					entList.setModel(listModel);
					}
				
					catch(Exception restoreExc)
					{
						JOptionPane.showMessageDialog(frame, "No save file exists to restore.");
					}
				}
				else if(answer == JOptionPane.NO_OPTION || answer == JOptionPane.CANCEL_OPTION)
				{
					
				}
				
				
			}
		}
				);
		
		button_7.setBounds(205, 308, 184, 80);
		frame.getContentPane().add(button_7);
		
		Button button_8 = new Button("Save");
		button_8.addActionListener(new ActionListener() {
			/**
			 * This actionPerformed handles what happens when the user clicks on the save button to save their current tasks entered in the list
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				int answer = JOptionPane.showConfirmDialog(frame, "Would you like to save?");
				if(answer == JOptionPane.YES_NO_OPTION)
				{
					try 
					{
						FileOutputStream fout = new FileOutputStream("EntryList");
						ObjectOutputStream oos = new ObjectOutputStream(fout);
						oos.writeObject(EntryList);
						oos.close();
					}
					catch(Exception exc)
					{
						exc.printStackTrace();
					}
				}
				else if(answer == JOptionPane.NO_OPTION || answer == JOptionPane.CANCEL_OPTION)
				{
					
				}
			}
		});
		button_8.setBounds(15, 308, 184, 80);
		frame.getContentPane().add(button_8);
		
		Button button_1 = new Button("Delete Entry");
		button_1.addActionListener(new ActionListener() 
		{
			/**
			 * This actionPerformed handles what happens when the user wishes to delete an existing entry and how they can interact with the page
			 * @param E is an ActionEvent that represents the user clicking on this button
			 * @return
			 */
			public void actionPerformed(ActionEvent e)
			{
				
				addFrame.setVisible(false);
				DisplayFrame.setVisible(false);
				DeleteFrame.setVisible(true);
				changeFrame.setVisible(false);
				completeFrame.setVisible(false);
				reportFrame.setVisible(false);
				
				DefaultListModel listModel = new DefaultListModel();
				for(int i = 0; i < EntryList.size(); i++)
				{
					if((EntryList.get(i).isItDeleted() == false))
					{
						if((EntryList.get(i).isItCompleted() == false))
						{
							if(EntryList.get(i).get_priority() > 0)
							{
								listModel.addElement(EntryList.get(i).toString());
							}
						}
					}
				}
				
				
				Delete_List.setModel(listModel);
				
				//entList.updateUI();
				
				Delete_List.setToolTipText("");
				Delete_List.setBounds(0, 48, 576, 207);
				
				MouseListener deleteMouseListener = new MouseAdapter() {
					/**
					 * This method handles what happens when the user double clicks on a list entry per page
					 * @param e e is a mouseEvent that represents when the user clicks on a JList entry in some way
					 * @return
					 */
				    public void mouseClicked(MouseEvent e) {
				    	int deletedNum = 0;
				        if (e.getClickCount() == 2) {
				        	ArrayList<String> StringList = new ArrayList<String>();
				           String selectedItem = (String) Delete_List.getSelectedValue().toString();
				           StringList.add(selectedItem);
				           int answer = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete the following entry: " + selectedItem);
							if(answer == JOptionPane.YES_NO_OPTION)
							{
								try 
								{
									for(int i = EntryList.size()-1; i > -1; i--)
									{
										if(selectedItem.contentEquals(EntryList.get(i).toString()))
										{
											if(EntryList.get(i).isItDeleted() == false)
											{
												EntryList.get(i).delete();
												deletedNum = EntryList.get(i).get_priority();
												break;
											}
										}
									}
									for(int i = 0; i < EntryList.size(); i++)
							        {
							        	if(EntryList.get(i).get_priority() > deletedNum)
							        	{
							        		int tempNum = EntryList.get(i).get_priority() - 1;
							        		EntryList.get(i).set_priority(tempNum);
							        	}
							        }
								}
								catch(Exception exc)
								{
									exc.printStackTrace();
								}
							}
							else if(answer == JOptionPane.NO_OPTION || answer == JOptionPane.CANCEL_OPTION)
							{
								
							}
							Delete_List.updateUI();
							DefaultListModel listModel = new DefaultListModel();
							for(int i = 0; i < EntryList.size(); i++)
							{
								if((EntryList.get(i).isItDeleted() == false))
								{
									if((EntryList.get(i).isItCompleted() == false))
									{
										if(EntryList.get(i).get_priority() > 0)
										{
											listModel.addElement(EntryList.get(i).toString());
										}
									}
								}
							}
							
							Delete_List.setModel(listModel);
				         }
				       
				        
				    }
				};
				if(!EntryList.isEmpty() && deleteMouseActions == false)
				{
					Delete_List.addMouseListener(deleteMouseListener);
					deleteMouseActions = true;
				}
			}
		});
		button_1.setBounds(15, 136, 184, 80);
		frame.getContentPane().add(button_1);
		
		
		
		
		
		
		
		
	}
}
