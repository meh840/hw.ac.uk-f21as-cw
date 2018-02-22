package src.uk.ac.hw.F21AS.GROUPms256as294pt45.Core;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ErrorLogger {
	
	private ArrayList <String> Error_List;
	private ArrayList <String> Incorrect_Entries;

	public ErrorLogger(){
		Error_List = new ArrayList<String>() ;
		Incorrect_Entries =new ArrayList <String>();
	}
	
	 public int getSizeofEntry() {
	    	return Incorrect_Entries.size();
	    }
	 
	 public void addError(String person) 
	    {
		 Error_List.add(person);
	    }
	 
	 public void addEntry(String booking) 
	    {
		 Incorrect_Entries.add(booking);
	    }
	 
	// write the Arraylist in a file
		public  void WriteToFile(String FileName) {
			
			 FileWriter DataFile;
			 try {
			    DataFile = new FileWriter(FileName);
			    DataFile.write(this.setTabletoFile());
			 	DataFile.close();
			 }
			 //message and stop if file not found
			 catch (FileNotFoundException fnf){
				 System.out.println(FileName + " can not be found ");
				 System.exit(0);
			 }
			 //stack trace here because we don't expect to come here
			 catch (IOException ioe){
			    ioe.printStackTrace();
			    System.exit(1);
			 }
		}
	    
		// create a table to save into a file
		public String setTabletoFile()
		{
			String instance = " Incorrect Entries ("+ this.getSizeofEntry()+ ") \r\r\n";
			for (String c  : Incorrect_Entries){
				instance += String.format("%-2s", c);
				instance += "\r\n";
			}
			instance += " \r\n";
			instance += " \r\n";
			instance += String.format("%s", "Unsuccessful tries \r\r\n");
			for (String c  : Error_List){
				instance += String.format("%-2s", c);
				instance += "\r\n";
			}
			return instance;
		}

}
