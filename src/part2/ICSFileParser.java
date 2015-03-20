package part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ICSFileParser {
	
	String filename;
	String dtstart;
	String dtend;
	String tzid;

	public ICSFileParser(String filename){
		this.filename = filename;
		
		File file = new File(filename);
		try {
			Scanner s = new Scanner(file);
			
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			s.nextLine();
			dtstart = s.nextLine().substring(8);
			dtend = s.nextLine().substring(6);
			s.nextLine();
			s.nextLine();
			tzid = s.nextLine().substring(5);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public String getDtstart(){
		System.out.println(dtstart);
		return dtstart;
	}
	
	public String getDtend(){
		System.out.println(dtend);
		return dtend;
	}
	
	public String getTzid(){
		System.out.println(tzid);
		return tzid;
	}
	
}
