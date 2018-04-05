package skal.tools.DHBot;

import org.sikuli.script.Sikulix;

public class PrefsHelper {
	public static final String RAIDSINFOREGION = "dhbotraidir";
	public static final String RAIDSGLORYREGION = "dhbotraidgr";
	public static final String RAIDSBUTTONREGION = "dhbotraidbr";
	public static final String RAIDSMINGLORY = "dhbotraidmng";
	public static final String RAIDSMAXGOLD = "dhbotraidmxg";
	public static final String TRIALSINFOREGION = "dhbotrialsir";
	

	public static String removeRaidsGloryRegion() {
		return PrefsHelper.remove(RAIDSGLORYREGION);	   
	}
	public static String loadRaidsGloryRegion() {
		return PrefsHelper.load(RAIDSGLORYREGION);	   
	}
	public static void removeRaidsRegions() {
		PrefsHelper.remove(RAIDSINFOREGION);	   
		PrefsHelper.removeRaidsGloryRegion();	   
		PrefsHelper.remove(RAIDSBUTTONREGION);   
	}
	
	public static int loadRaidsMinGlory() {
	 String value=PrefsHelper.load(RAIDSMINGLORY);  
	 if (Helpers.isInteger(value))
		 return Integer.parseInt(value);
	 else
		 return 10000;
	}
		
	 public static void storeRaidsMinGlory(int minGlory) {
		 PrefsHelper.store(RAIDSMINGLORY, Integer.toString(minGlory));	 
	 }
	 public static int loadRaidsMaxGold() {
	 
		 String value=PrefsHelper.load(PrefsHelper.RAIDSMAXGOLD);  
		 if (Helpers.isInteger(value))
			 return Integer.parseInt(value);
		 else
			 return 50000000;
	 }
	public static void storeRaidsMaxGold(int maxGold) {
		store(RAIDSMAXGOLD, Integer.toString(maxGold));	 
	}
		
	public static String load( String prefname) {
		return Sikulix.prefLoad(prefname);	   
	}
	public static void store( String prefname, String value) {
		Sikulix.prefStore(prefname, value);	   
	}
	public static String remove(String prefname) {
		return Sikulix.prefRemove(prefname);  	
	}
}