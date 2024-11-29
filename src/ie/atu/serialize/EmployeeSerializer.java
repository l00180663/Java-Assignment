package ie.atu.serialize;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
 
import javax.swing.JOptionPane;
 
import ie.atu.hotel.Employee;
 
public class EmployeeSerializer {
	private ArrayList<Employee> employees;
	private final String FILENAME = "employees.bin";	
	private File employeesFile;	
	// Default Constructor
	public EmployeeSerializer(){
		// Construct EmployeeList ArrayList
		employees = new ArrayList<Employee>();
 
		// TODO Construct employeesFile and physically create the File
		employeesFile = new File(FILENAME);
	}	
 
	/////////////////////////////////////////////////////////////
	// Method Name : add()								              //
	// Return Type : void								              //
	// Parameters : None								                 //
	// Purpose : Reads one Employee record from the user       //
	//           and adds it to the ArrayList called employees //
	/////////////////////////////////////////////////////////////
	public void add(){
	    // Create an Employee object
		 Employee employee = new Employee();
       // TODO - Update add() so it checks if Cancel was clicked when reading Employee
		 // Read its details
		 employee.read();
		 // And add it to the employees ArrayList
		 employees.add(employee);	
	}
 
	///////////////////////////////////////////////////////
	// Method Name : list()						              //
	// Return Type : void					   	           //
	// Parameters : None						                 //
	// Purpose : Lists all Employee records in employees //
	///////////////////////////////////////////////////////		
	public void list(){
		String employeesToList="";
 
		if(!employees.isEmpty()) {
			// for every Employee object in employees
			for(Employee tmpEmployee : employees) {
				// add it to employeesToList and
				employeesToList+=tmpEmployee;
				// add a newline
				employeesToList+="\n";
			}
			// Display employeesToList in a messageDialog
		   JOptionPane.showMessageDialog(null, employeesToList, "EMPLOYEE LIST", JOptionPane.INFORMATION_MESSAGE);	
		}
		else
			// Display "No Employees stored..." in messageDialog
		   JOptionPane.showMessageDialog(null, "No Employees to list.", "EMPLOYEE LIST", JOptionPane.INFORMATION_MESSAGE);	
	}	
 
	////////////////////////////////////////////////////////////////
	// Method Name : view()									              //
	// Return Type : Employee								              //
	// Parameters : None								                    //
	// Purpose : Displays the required Employee record on screen  //
	//         : And returns it, or null if not found             //   
	////////////////////////////////////////////////////////////////	
	public Employee view() {
	    if (employees.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No employees available.", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
 
	    String empNum = JOptionPane.showInputDialog("Enter employee number: ");
	    if (empNum == null || empNum.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Employee number is empty", "Error", JOptionPane.ERROR_MESSAGE);
	        return null;
	    }
 
	    try {
	        // Convert string to integer
	        int intNum = Integer.parseInt(empNum.trim());
 
	        // Find the employee
	        for (Employee employee : employees) {
	            if (employee.getNumber() == intNum) {
	                JOptionPane.showMessageDialog(null, "Employee found: " + employee, "Employee Details", JOptionPane.INFORMATION_MESSAGE);
	                return employee;
	            }
	        }
 
	        JOptionPane.showMessageDialog(null, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid numeric employee number.", "Error", JOptionPane.ERROR_MESSAGE);
	    }
 
	    return null;
	}
 
	///////////////////////////////////////////////////////////////////
	// Method Name : delete()							        	           //
	// Return Type : void								        	           //
	// Parameters : None									                    //
	// Purpose : Deletes the required Employee record from employees //
	///////////////////////////////////////////////////////////////////	
	public void delete() {
	    Employee emp = view(); // Use view() to locate the employee
	    if (emp != null) {
	        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
	        if (confirm == JOptionPane.YES_OPTION) {
	            employees.remove(emp);
	            JOptionPane.showMessageDialog(null, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	}
 
 
	///////////////////////////////////////////////////////////////
	// Method Name : edit()			  					                //
	// Return Type : void								    	          //
	// Parameters : None								     	             //
	// Purpose : Edits the required Employee record in employees //
	///////////////////////////////////////////////////////////////	
	public void edit() {
	    Employee emp = view(); // Use view() to locate the employee
	    if (emp != null) {
	        JOptionPane.showMessageDialog(null, "Update the employee details.", "Edit Employee", JOptionPane.INFORMATION_MESSAGE);
	        emp.read(); // Assuming `read()` allows you to update employee details
	        JOptionPane.showMessageDialog(null, "Employee details updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
 
	// This method will serialize the employees ArrayList when called,
	// i.e. it will write employees to a file called employees.bin
	public void serializeEmployees(){
		 // TODO - Write the code for serializeEmployees()
 
		try (FileOutputStream fileStream = new FileOutputStream(employeesFile);
				ObjectOutputStream os = new ObjectOutputStream(fileStream))
		{
			//serialise the arraylist
			os.writeObject(employees);
			JOptionPane.showMessageDialog(null, "Employees saved to file.");
		}
		catch (FileNotFoundException e) {
			System.out.println("Error: file not found.");
		}
		catch (IOException e) {
			System.out.println("Error: Unable to save employees to file");
		}
		 //JOptionPane.showMessageDialog(null, "You must write the code for the serializeEmployees() method.", "NOT IMPLEMENTED", JOptionPane.INFORMATION_MESSAGE);		
	}
 
	// This method will deserialize the Employees ArrayList when called,
	// i.e. it will restore the employees ArrayList from the file employees.bin
	public void deserializeEmployees(){
		 ObjectInputStream is=null;
		 try {
			 // Deserialize the ArrayList...
			 FileInputStream fileStream = new FileInputStream(employeesFile);
			 is = new ObjectInputStream(fileStream);
			 employees = (ArrayList<Employee>)is.readObject();
          is.close();
		}
		catch(FileNotFoundException fNFE){
			 System.out.println("Cannot open file " + employeesFile.getName() + ".");
		}
		catch(IOException ioE){
			 System.out.println("Cannot read from " + employeesFile.getName() + ".");
		}
		catch(Exception e){
			 System.out.println("Cannot read from " + employeesFile.getName() + ".");
		}
	}
}
 