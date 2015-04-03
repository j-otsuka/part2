package part2;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args) {
		
		
		ICSFileParser event = new ICSFileParser("studyfinal.ics");
		ICSFileParser event2 = new ICSFileParser("studyfinal2.ics");
		ICSFileParser event3 = new ICSFileParser("studyfinal3.ics");
		
		//arraylist for now
		ArrayList<ICSFileParser> a = new ArrayList<ICSFileParser>();
		
		a.add(event);
		a.add(event2);
		a.add(event3);
		
		//check tzid (exit if error)
		checkTZ(a);
		
		//check same date (exit if error)
		checkDate(a);
	
		//sort time of event
		a = sortTime(a);
		
		//check if the time is overlapping (exit if error)
		checkTime(a);
		
		//generate free time ice files
		
		
	}
	
	//exit if timezone is different
	public static void checkTZ(ArrayList<ICSFileParser> a){
		
		String TZ =  a.get(0).getTzid();
		String start = TZ;
		
		//for all ICS files
		for(int i = 1; i < a.size() ; i++){
			
			String TZ2 = a.get(i).getTzid();

			//exit if any date is different from the first one
			if(!TZ.equals(TZ2)){
				
				System.out.println("Please give events with the same timezone.");
	
				System.exit(0);
			}		
		}	
	}
	
	//exit if date is different
	public static void checkDate(ArrayList<ICSFileParser> a){
		
		//going to compare with this date
		int date =  Integer.parseInt(a.get(0).getDtend().substring(0,8));

		
		//for all ICS files
		for(int i = 0; i < a.size() ; i++){
		
			int start = Integer.parseInt(a.get(i).getDtstart().substring(0,8));
			int end   = Integer.parseInt(a.get(i).getDtend().substring(0,8));

			//exit if any date is different from the first one
			if(date != end || date != start){
				
				System.out.println("Please give events with the same date.");
				
				System.exit(0);
			}
		}
	}
	
	//return arraylist with increasing order of time
	public static ArrayList<ICSFileParser> sortTime(ArrayList<ICSFileParser> a){
		
		ArrayList<ICSFileParser> a2 = new ArrayList<ICSFileParser>();
		
		//copy files
		for (int i = 0; i < a.size(); i++) {
			 a2.add(a.get(i));
		}
		 
		//bubble sort in ascending order of time
		for (int i = 0; i < ( a.size() - 1 ); i++) {
			for (int j = 1; j < (a.size() - i); j++) {
		    	  
			    int t1 = Integer.parseInt(a.get(j-1).getDtstart().substring(9));
			    int t2 = Integer.parseInt(a.get(j).getDtstart().substring(9));
			    	    
			    if (t1 > t2) 
			    {
			    	//System.out.println(t1 + ">" + t2);
			    	ICSFileParser swap   = a.get(j-1);
			        a2.set(j-1, a.get(j));
			        a2.set(j, swap);
			    }
			}
		}	

		//debug to print the sorted array
		//for (int i = 0; i <  a2.size(); i++) {
		//System.out.println(a2.get(i).dtstart);
		//}
		    
		return a2;
				
	}

	//exit if time is overlapping
	public static void checkTime(ArrayList<ICSFileParser> a){
		
		//for all ICS files
		for(int i = 0; i < a.size()-1 ; i++){
		
			int end   = Integer.parseInt(a.get(i).getDtend().substring(9));
			int start = Integer.parseInt(a.get(i+1).getDtstart().substring(9));	

			//System.out.println(end + " " + start);
			
			//exit if the end time of previous event is later (or same) than the start time of the next event
			if(end >= start){
				
				System.out.println("Please give events which does not overlap time with each other.");
				
				System.exit(0);
			}
		}
		
	}


}
