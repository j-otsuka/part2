package part1;

import java.io.*;
import java.util.*;

public class GenerateICS {
	
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		
		try{		
			//create blank ics file
			File file = new File("studyfinal.ics");
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			//begin calendar
			bw.write("BEGIN:VCALENDAR");
			bw.newLine();

			//VERSION
			bw.write("VERSION:2.0");
			bw.newLine();
			
			//create new event
			bw.write("BEGIN:VEVENT");
			bw.newLine();		
			
			//SUMMARY
			bw.write("SUMMARY:"+getSummary());
			bw.newLine();	
		
			//LOCATION
			bw.write("LOCATION:"+getLocation());
			bw.newLine();
			
			//CLASSIFICATION	
			bw.write("CLASSIFICATION:"+getClassification());			
			bw.newLine();
	
			//PRIORITY
			bw.write("PRIORITY:"+getPriority());
			bw.newLine();
			
			String start;
			String end;
			boolean tf=true;
			
			do{
				if(!tf){
					System.out.println("Oops, the end time is earlier than the start time. Try again.");
				}

				//DTSTART
				System.out.println("Enter START date & time of the event.");
				start = getDTime();
					
				//DTEND
				System.out.println("Enter END date & time of the event.");
				end = getDTime();
			
				tf = validateDTEnd(start,end);
			}while(!tf); //loop if 
			
			bw.write("DTSTART:"+start);
			bw.newLine();	
			bw.write("DTEND:"+ end);
			bw.newLine();
			
			//DTSTART and DTEND check
			
			bw.write("END:VEVENT");
			bw.newLine();		
			
			//Time zone identifier
			bw.write("BEGIN:VTIMEZONE");
			bw.newLine();					
			
			bw.write("TZID:"+TimeZone.getDefault().getID());
			bw.newLine();
			
			bw.write("END:VTIMEZONE");
			bw.newLine();	
			
			bw.write("END:VCALENDAR");
			bw.close();
			  
		}catch(IOException e){
			System.out.println(e);
		}				
	}
	
	public static String getSummary() {
		String summary;
		   
		System.out.print("Enter the summary for this event: ");
		summary = in.nextLine();
		return summary;
	}
	
	public static String getLocation() {
		String location;

		System.out.print("Enter the location for this event: ");
		location = in.nextLine();
		return location;
	}
	
	public static int getPriority() {
		int priority = 0;
		String yesno;
		
		while(true){
			System.out.print("Is there priority for this event?(y/n): ");
			yesno = in.nextLine();
			
			if(yesno.equals("n")){ //no priority
				return priority;
			}
			else if(yesno.equals("y")){ //there is priority
				
				while(true){
					System.out.print("Enter priority for this event (1 for highest, 9 for lowest): ");
					
					priority = in.nextInt();
					
					if(priority > 0 && priority < 10){ //if it is between 1-9, return
						return priority;
					}
					else{
						System.out.println("Please enter a number from 1 to 9.");
					}
				}			
			}
			else{ //if user inputs something other than y/n
				System.out.println("Please enter \"y\" or \"n\".");
			}
		}

	}//end of getPriority
	
	public static String getClassification(){
		
		while(true){
			System.out.println("Is the event (1)public, (2)private, or (3)confidential?: ");
			System.out.print("Enter number: ");
			int input = Integer.parseInt(in.nextLine());
			
			if(input < 1 || input > 3){ //if the number is not 1,2,3
				System.out.println("Please enter a number from 1 to 3.");
			}
			else{
				switch(input){
					case 1: return "PUBLIC";
					case 2: return "PRIVATE";
					case 3: return "CONFIDENTIAL";
				}
			}
		}
		
	}
	
	private static String getDTime(){
	    Scanner input = new Scanner(System.in);
	    String dstart = "";
	    String dateInput = "";
	    String timeInput = "";
	    String hour = "0";
	    String year = "";
	    String month = "";
	    String day = "";
	    int hh = 0;
	    String minute = "0";
	    int mm = 0;
	    String amOrPm = "";
	    
	    DateValidator date = new DateValidator();
	    
	    //do-while loop to get date portion of DSTART
	    do{
	      System.out.print("Enter the date in the format: mm/dd/yyyy (e.g. 03/22/2015) ");
	      dateInput = input.nextLine();
	    
	    if (!date.validate(dateInput))
	      System.out.println("Oops, that is an incorrect date format, please try again.");
	    
	    } while (!date.validate(dateInput));
	    
	    //do-while loop to get the time portion of DSTART
	    do{
	      
	      System.out.print("Enter the time in the format: hh:mm (e.g. 02:30, 22:15 ...) ");
	      timeInput = input.nextLine();
	      
	      //break up hour and minute portions to validate input
	      if (timeInput.length() < 5){
	        System.out.println("Oops, time format appears incorrect, please try again.");
	        continue;
	      }
	      hour = timeInput.substring(0, 2);
	      minute = timeInput.substring(3, 5);
	      hh = Integer.parseInt(hour);
	      mm = Integer.parseInt(minute);
	      
	      if (!(hh > 0 && hh < 25))
	        System.out.println("Oops, the hour portion appears incorrect, please try again.");
	      if (!(mm >= 0 && mm <= 60))
	        System.out.println("Oops, the minute portion appears incorrect, please try again.");
	      
	    } while ((!timeInput.matches("\\d\\d:\\d\\d")) || !(hh > 0 && hh < 24) || !(mm >= 0 && mm <= 60));

	    if (hh < 13) {
	      do{
	      System.out.print("Is this " + hh + " 'o clock AM or PM? (Please enter AM or PM) ");
	      amOrPm = input.nextLine();
	      } while (!amOrPm.equals("AM") && !amOrPm.equals("PM"));
	    }
	      
	    if (amOrPm.equals("AM"))
	    {
	      if (hh == 12){
	        hour = "00";
	      }
	    }
	    else if (amOrPm.equals("PM"))
	    {
	      switch (hh){
	      case 1: hour = "13";
	          break;
	      case 2: hour = "14";
	          break;
	      case 3: hour = "15";
	        break;
	      case 4: hour = "16";
	        break;
	      case 5: hour = "17";
	        break;
	      case 6: hour = "18";
	        break;
	      case 7: hour = "19";
	        break;
	      case 8: hour = "20";
	        break;
	      case 9: hour = "21";
	        break;
	      case 10: hour = "22";
	        break;
	      case 11: hour = "23";
	        break;
	      case 12: hour = "12";
	        break;
	      }
	    }
	    //convert to format: yyyymmddThhmmss 20150227T130000
	    year = dateInput.substring(6);
	    month = dateInput.substring(0, 2);
	    day = dateInput.substring(3, 5);
	    
	    dstart = (year + month + day + "T" + hour + minute + "00");
	    
	    return dstart;
	  }
	
	  //return true if date is valid
	  private static boolean validateDTEnd(String start, String end){
		    String pattern = "\\d{8}T\\d{6}";
		    if (!start.matches(pattern) || !end.matches(pattern)){		    	
		      return false;
		    }
		    
		    //if end date is later, date is valid
		    if (Integer.parseInt(end.substring(0, 8)) > Integer.parseInt(start.substring(0, 8))){
		        return true;
    
		    }	
		    //if date is same, then if the end time is later, date is valid
		    if (Integer.parseInt(end.substring(0, 8)) == Integer.parseInt(start.substring(0, 8))){ 
			      if (Integer.parseInt(end.substring(9)) > Integer.parseInt(start.substring(9))){
			        return true;
			      }		      
			}	
		    //if not, it is invalid
		    return false;
		  }
}
