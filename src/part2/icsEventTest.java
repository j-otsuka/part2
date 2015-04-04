package part1;

import static org.junit.Assert.*;

import java.util.TimeZone;

import org.junit.Test;

public class icsEventTest {

	@Test
	public void test() {	
		// Testing the constructor defaults here
		icsEvent test = new icsEvent();
		assertEquals("BEGIN:VCALENDAR", test.getCalBeginTag());
		assertEquals("VERSION:2.0", test.getVersion());
		assertEquals("BEGIN:VEVENT", test.getEventBeginTag());
		assertEquals(null, test.getSummary());
		assertEquals(null, test.getLocation());
		assertEquals("CLASSIFICATION:PUBLIC", test.getClassification());
		assertEquals("PRIORITY:0", test.getPriority());
		assertEquals(null, test.getDtstart());
		assertEquals(null, test.getDtend());
		assertEquals("END:VEVENT", test.getEventEndTag());
		assertEquals("BEGIN:VTIMEZONE", test.getTzBeginTag());
		assertEquals("TZID:Pacific/Honolulu", test.getTzid()); // Also works with TimeZone.getDefault().getID()
		assertEquals("END:VTIMEZONE", test.getTzEndTag());
		assertEquals("END:VCALENDAR", test.getCalEndTag());
		
		// Making sure these are null when initialized
		assertNull(test.getSummary());
		assertNull(test.getLocation());
		assertNull(test.getDtstart());
		assertNull(test.getDtend());
		
		// Testing set methods here
		assertEquals(test.setClassification(), test.getClassification());
		assertEquals(test.setPriority(), test.getPriority());
		assertEquals(test.setSummary(), test.getSummary());
		assertEquals(test.setLocation(), test.getLocation());
		assertEquals(test.setDtstart(), test.getDtstart());
		assertEquals(test.setDtend(), test.getDtend());
		
		// Making sure that they are not null after calling the set methods
		assertNotNull(test.getSummary());
		assertNotNull(test.getLocation());
		assertNotNull(test.getDtstart());
		assertNotNull(test.getDtend());
		
		// Testing other methods here
		assertEquals(false, test.validateDTime("DTSTART:20150322T200000", "DTEND:20150322T160000"));
		assertEquals(true, test.validateDTime("DTSTART:20150322T1200000", "DTEND:20150322T160000"));
		assertEquals(true, test.writeIcsFile(test, "test.ics"));
		
		// Testing the constructor with presets here
		icsEvent party = new icsEvent("Party", "School", "PUBLIC", "0", "20150322T1200000", "20150322T160000");
		assertEquals("Party", party.getSummary());
		assertEquals("School", party.getLocation());
		assertEquals("PUBLIC", party.getClassification());
		assertEquals("0", party.getPriority());
		assertEquals("20150322T1200000", party.getDtstart());
		assertEquals("20150322T160000", party.getDtend());
		
		// Testing free time constructor
		FreeTimeGenerator free = new FreeTimeGenerator();
		assertEquals(0, free.eventList.size());
		assertTrue(free.eventList.isEmpty());
		free.eventList.add(test);
		assertEquals(1, free.eventList.size());
		assertFalse(free.eventList.isEmpty());
		
		// Testing free time readFile method
		icsEvent read = free.readIcs("studyfinal.ics");
		assertEquals("BEGIN:VCALENDAR", read.getCalBeginTag());
		assertEquals("VERSION:2.0", read.getVersion());
		assertEquals("BEGIN:VEVENT", read.getEventBeginTag());
		assertEquals("SUMMARY:Studying for finals", read.getSummary());
		assertEquals("LOCATION:Hamilton Library", read.getLocation());
		assertEquals("CLASSIFICATION:PUBLIC", read.getClassification());
		assertEquals("PRIORITY:1", read.getPriority());
		assertEquals("DTSTART:20150508T180000", read.getDtstart());
		assertEquals("DTEND:20150508T220000", read.getDtend());
		assertEquals("END:VEVENT", read.getEventEndTag());
		assertEquals("BEGIN:VTIMEZONE", read.getTzBeginTag());
		assertEquals("TZID:Pacific/Honolulu", read.getTzid());
		assertEquals("END:VTIMEZONE", read.getTzEndTag());
		assertEquals("END:VCALENDAR", read.getCalEndTag());
	}

}
