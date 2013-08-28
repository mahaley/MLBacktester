package summary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _tester {

	
	public static void main (String[] args) {
		Pattern p = Pattern.compile("Labeling confidence is: (.*)");
		//Pattern p = Pattern.compile("([0-9]*)[.]*");
		//String p = "([0-9]{1,2})(.*)";
		String s = "Labeling confidence is: 0.0010014624600425174";
		Matcher m = p.matcher(s);
		System.out.println(m.groupCount());
		//String ans = null;
		if (m.matches()) {
		System.out.println(m.group(1));
		System.out.println(Float.parseFloat(m.group(1)));
		}
		
	}
	
}
