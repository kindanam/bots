package skal.tools.DHBot;

import org.sikuli.basics.Debug;
import org.sikuli.natives.OSUtil;
import org.sikuli.natives.SysUtil;
import org.sikuli.script.App;
import org.sikuli.script.IScreen;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.sikuli.script.Sikulix;

public class AppRegion extends Region
{

	public AppRegion()
	{
	}

	public void dbg(String appName)
	{
		Debug.user("%s - x: %d, y: %d, w: %d, h: %d",appName, this.x,this.y,this.w, this.h);
	}

	public static Region highlightStoredRegion(String prefname, int secs)
	{
		Region r=getStoredRegion(prefname);
		if (r != null)
		{
			r.highlight(secs);
		}
		return r;
	}

	public static Region getStoredRegion(String prefname)
	{
		String prefregs=PrefsHelper.load(prefname);
		RegionRect rr=RegionRect.fromJson(prefregs);
		if (rr == null) 
			return null;
		else
			return rr.toRegion();
	}
	
	public static boolean  isRegionValid(Region rg, String msg)
	{
		if (rg == null) return false;
		rg.highlight(1);
		return Sikulix.popAsk("Is it ok ?",msg);		
	}
	
	public static Region getRegion(String prefname, String selectMsg, boolean skipRegionChecks) {
		Region rg=getStoredRegion(prefname);
		if (rg!=null && skipRegionChecks)
		{
			rg.highlight(1);
			return rg;
		}
		if (! isRegionValid(rg, "Check region"))   
			rg = defineRegion(prefname, selectMsg);
		return rg; 	
	}
	
	public static Region defineRegion(String prefname, String selectMsg) {
		PrefsHelper.remove(prefname);	

		int screenId=Screen.getPrimaryScreen().getID();
		Screen screen = Screen.getScreen(screenId);
		Region rg=null;

		while (true) {
			rg=screen.selectRegion(selectMsg);

			if (isRegionValid(rg, "Define region"))
				break;
		}
		
		String rgs = RegionRect.fromRegion(rg).toJson();
		PrefsHelper.store(prefname, rgs);  
		return rg;	
	}	
}