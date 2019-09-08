package CSE360_GUI_Priority_list;
import java.util.Date;
import java.io.Serializable;

/**
 * Entry class that will represent a entry or a task on the to do list. It accompanies Strings
 * that represent attributes like the description and dates. Multiple booleans to help
 * dictate the current status, and a int to hold the priority of that task in respect to other entries
 * created
 * @author CSE Team 
 */
public class Entry implements Serializable
{
	boolean started;
	boolean finished;
	String description;
	int priority;
	String due_date;
	String status;
	boolean deleted;
	boolean completed;
	Date startDate;
	String startDateString;
	Date endDate;
	String endDateString;
	
	/**
	 * Default Constructor for Entry Class
	 */
	public Entry()
	{
		description = "";
		priority = 0;
		due_date = "00/00/0000";
		status = "Not started";
		deleted = false;
		completed = false;
		startDate = new Date();
		startDateString = startDate.toString();
		started = false;
		finished = false;
	}
	
	/**
	 * public void start performs doOver and creates a new startDate and equates it's toString
	 * to the new startDateString. Status becomes "In Progress" and started boolean is set to true
	 */
	public void start()
	{
		doOver();
			startDate = new Date();
			startDateString = startDate.toString();
		status = "In Progress";
		started = true;
	}
	
	/**
	 * doOver sets all the attributes to null and status "Not started"
	 */
	public void doOver()
	{
		startDate = null;
		endDate = null;
		status = "Not started";
		started = false;
		finished = false;
	}
	
	/**
	 * get_description is the accessor Method for the String description
	 * @return
	 */
	public String get_description()
	{
		return description;
	}
	
	/**
	 * set_description is the mutator method for the String description
	 * @param new_description
	 */
	public void set_description(String new_description)
	{
		description = new_description;
	}
	
	/**
	 * delete this
	 * @return
	 */
	public boolean check_unique()
	{
		return true;
	}
	
	/**
	 * Accessor method for int priority
	 * @return priority
	 */
	public int get_priority()
	{
		return priority;
	}
	
	/**
	 * Mutator method for set_priority
	 * @param new_priority
	 */
	public void set_priority(int new_priority)
	{
		if(!completed && !deleted)
		{
			priority = new_priority;
		}
		
	}
	
	/**
	 * Not Used delete
	 * @return
	 */
	public boolean check_priority()
	{
		return true;
	}
	
	/**
	 * Accessor method for the String due_date
	 * @return due_date
	 */
	public String get_due_date()
	{
		return due_date;
	}
	
	/**
	 * Mutator method for the String due_date
	 * @param new_due_date
	 */
	public void set_due_date(String new_due_date)
	{
		due_date = new_due_date; 
	}
	
	/**
	 * Accessor method for the String status
	 * @return status
	 */
	public String get_status()
	{
		return status;
	}
	
	/**
	 * Mutator method for the String Status
	 * @param new_status
	 */
	public void set_status(String new_status)
	{
		status = new_status;
	}
	
	/**
	 * delete will set the delete and completed boolean to true
	 */
	public void delete()
	{
		deleted = true;
		completed = true;
	}
	
	/**
	 * Finish will set the boolean status of finished and started to true as well
	 * as setting the status string to equate to "Finished"
	 */
	public void finish()
	{
		// if the entry has not been started a new start date will be created
		if(started == false)
		{
			startDate = new Date();
			startDateString = startDate.toString();
		}
		// if the entry has not been finished a new end date will be created
		if(!finished)
		{
			endDate = new Date();
			endDateString = endDate.toString();	
		}
		
		finished = true;
		started = true;
		status = "Finished";
	}
	
	/**
	 * Complete will set the boolean status of finished and started to true. Start and End dates
	 * will be set. The status string will equated to "Finished"
	 */
	public void complete()
	{
		//if started equals false a new start date will be created
		if(started == false)
		{
			startDate = new Date();
			startDateString = startDate.toString();
		}
		completed = true;
		deleted = true;
		
		// if finished is false a new finish date will be created
		if(finished == false)
		{
			status = "Finished";
			endDate = new Date();
			endDateString = endDate.toString();
		}
		
		
		
	}
	
	/**
	 * boolean method to check the return value of the deleted method
	 * @return deleted
	 */
	public boolean isItDeleted()
	{
		return deleted;
	}
	
	/**
	 * boolean method to check the return value of the completed method
	 * @return completed
	 */
	public boolean isItCompleted()
	{
		return completed;
	}
	
	/**
	 * toString method that will print the Entry in a String format with all its attributes
	 */
	public String toString()
	{
		String returnString = "";
		if(status.contentEquals("Not started"))
		{
			return ("Description: " + description + " | " + "Priority: " + priority + " | " + "Due Date: " + due_date + " | " + "Status: " + status); 
		}
		else if(status.contentEquals("In Progress"))
		{
			return ("Description: " + description + " | " + "Priority: " + priority + " | " + "Due Date: " + due_date + " | " + "Date Started: " + startDateString + " | " + "Status: " + status); 
		}
		else
		{
				returnString = "Description: " + description + " | " + "Priority: " + priority + " | " + "Due Date: " + due_date + " | " + "Date Started: " + startDateString + " | " + "Date Finished On: " + endDateString + " | " + "Status: " + status;; 
			return returnString;
			
		}
		
	}
}
