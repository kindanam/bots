package skal.tools.DHBot;

import org.sikuli.script.Region;

import com.google.gson.Gson;

public class RegionRect
{
	public int x;
	public int y;
	public int w;
	public int h;
	
	public RegionRect(int x,int y,int w,int h)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;		
	}
	
	public static RegionRect fromRegion(Region region)
	{
		return new RegionRect(region.x, region.y,region.w,region.h);
	}

	public String toJson() 
	{
		return new Gson().toJson(this);	
		//return json.dumps( { "x" : self.x, "y" : self.y, "w" : self.w, "h" : self.h } );		
	}

	public Region toRegion()
	{
		return new Region(this.x, this.y,this.w,this.h);
	}

	public static RegionRect fromJson(String s) 
	{
		if ( s==null ||  s.isEmpty() )
			return null;

		return new Gson().fromJson(s, RegionRect.class);	
	}

}