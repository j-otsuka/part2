package part2;

public class Test {
	public static void main(String[] args) {
		ICSFileParser parse = new ICSFileParser("studyfinal.ics");
		
		System.out.println(parse.getDtend());
	}
}
