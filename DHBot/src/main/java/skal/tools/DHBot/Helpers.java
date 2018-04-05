package skal.tools.DHBot;

import java.util.regex.Pattern;

public class Helpers {
	private static final Pattern isIntPattern = Pattern.compile("[-+]?\\d+(\\.0*)?$");

	public static boolean isInteger(String v)
	{
		if (v == null) return false;
		return isIntPattern.matcher(v.trim()).matches();
	}	
}