package skal.tools.DHBot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;

import org.sikuli.basics.Debug;
import org.sikuli.script.ObserveEvent;
import org.sikuli.script.ObserverCallBack;
import org.sikuli.script.Observing;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.Sikulix;

public class RaidsRegion extends AppRegion
{
	private static final Pattern digitsOnlyPattern = Pattern.compile("(\\d+)");

	private Region _gp;
	private Region _bt;
	private Region _ir;
	
	public RaidsRegion()
	{
	}

	public int readGlory() {
		return readGlory(_gp);
	}
   
	private int readGlory(Region gloryRegion)
	{
		if (gloryRegion == null) {
			Sikulix.popError("Glory region is not defined","Error reading glory");		
			return -1;
		}
		String t=gloryRegion.text();
		Debug.user("Raid Glory Text : %s",t);

		Matcher matcher = digitsOnlyPattern.matcher(t.trim());

		boolean matches = matcher.find();

		if (!matches) {
			Sikulix.popError("Glory amount could not be read from glory Region.","Error reading glory");		
			return -1;
		}
		
		return Integer.parseInt(matcher.group(1));
	}
	
	public void changeOpponent() {
		_bt.click();	
		_ir.observe(5);
	}


	protected Region getRaidsInfoRegion(boolean skipRegionChecks) {
		return getRegion(PrefsHelper.RAIDSINFOREGION,"Surround name, power, and glory", skipRegionChecks);
	}

	protected Region getRaidsGloryRegion(boolean skipRegionChecks) {
		return getRegion(PrefsHelper.RAIDSGLORYREGION,"Surround Glory Points", skipRegionChecks);
	}
	
	protected Region getRaidsButtonRegion(boolean skipRegionChecks) {
		return getRegion(PrefsHelper.RAIDSBUTTONREGION,"Surround Change Opponent Button",skipRegionChecks);
	}
	
	private boolean  acceptsToRedefineGloryRegion() {
		return Sikulix.popAsk(
"Glory amount could not be read from glory Region.\n"+
"You should try to redefine it.\n"+
"If you still have issues, you should try to redefine your Emulator region.\n\n"+
"Do you want to redefine the glory region ?","Error reading glory");
	};
	
	public boolean defineAllRegions(boolean skipRegionChecks)
	{
		if (_ir != null)
		{
			Observing.remove(_ir);
		}
		_ir = getRaidsInfoRegion(skipRegionChecks);
		if  (_ir == null)
			return false;			
	

		_gp =defineRaidsGloryRegion(skipRegionChecks);
		if  (_gp == null)
			return false;			

		_bt = getRaidsButtonRegion(skipRegionChecks);
		
		_ir.onChange(50,new ObserverCallBack() {
		     public void changed(ObserveEvent e) {
		         e.stopObserver(); 
		     }
		});
		return true;
	}

	public int tryReadGlory()
	{
		int glory = -1;			  
		while (glory < 0){
			glory=readGlory();
			if (glory == -1) {
				if (! acceptsToRedefineGloryRegion())
					break;				
				else
					PrefsHelper.removeRaidsGloryRegion();
					_gp = defineRaidsGloryRegion(false);
					continue;
			}
		}
		return glory;
	}	
	
	public int readAndValidateGlory(Region rg)
	{
		int glory = readGlory(rg);
		
		if (glory < 0) return -1;
		
		boolean	isOk=Sikulix.popAsk("I read " + Integer.toString(glory) + "\n\n" +
"Is it the correct amount ?","Correct amount ?");
		
		return isOk ? glory : -1;		
	}
	private Region defineRaidsGloryRegion(boolean skipRegionChecks) {
		Region rg= getStoredRegion(PrefsHelper.RAIDSGLORYREGION);
		
		if (rg !=null && skipRegionChecks)
		{
			rg.highlight(1);
			return rg;
		}
			
		if (!isRegionValid(rg, "Check region") )
		{
			rg=getRaidsGloryRegion(false);			
		}

		do {
			int glory = readAndValidateGlory(rg);
			
			boolean isOk = (glory >= 0);
			if (isOk) break;
			
			isOk=Sikulix.popAsk("cannot read the glory." + "\n\n" +
"Shall we retry defining the region?","Retry ?");

			if (!isOk) return null;
			
			PrefsHelper.removeRaidsGloryRegion();
			rg=getRaidsGloryRegion(false);
		} while (true);
		
		return rg;
	}
	
//	public static void ResetRegions(boolean withPopups) {
//		boolean isOk=true;
//		if (withPopups)
//			isOk=Sikulix.popAsk(
//"		This will remove from stored preferences\n"+
//"		- The raids regions\n"+
//"\n"+
//"		It will be redefined when you go hunting.\n"+
//"\n"+
//"		Proceed ?","Reset raids regions");
//
//		if (isOk) {
//			PrefsHelper.removeRaidsRegions();	
//			if (withPopups) 		
//				Sikulix.popup("done","Reset raids regions");
//		}
//	}
}
