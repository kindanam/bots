package skal.tools.DHBot;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import org.sikuli.basics.HotkeyEvent;
import org.sikuli.basics.HotkeyListener;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Region;


public class ScoutWorker extends SwingWorker<ScoutResultStatusCodes, ScoutWorkerData> {

	private ScoutDataHolder _huntDataHolder;

	public ScoutWorker(ScoutDataHolder huntDataHolder) {
		super();
		this._huntDataHolder = huntDataHolder;
	}

	@Override
	protected ScoutResultStatusCodes doInBackground() throws Exception {
		
		RaidsRegion raidRegion=new RaidsRegion();
		
		if (! raidRegion.defineAllRegions(_huntDataHolder.getSkipRegionChecks()) )
		{
			return ScoutResultStatusCodes.ERROR_DEFINE_REGIONS;
		}
		
		int minGlory = PrefsHelper.loadRaidsMinGlory();
		int maxGold=PrefsHelper.loadRaidsMaxGold();
		
		
		int gold = 0;
		int glory = 0;			  
		while (true){
			
			glory=raidRegion.tryReadGlory();
			if (glory == -1) {
				return ScoutResultStatusCodes.ERROR_READ_GLORY;
			}

			publish(new ScoutWorkerData(glory, 0));

			if (glory >= minGlory) {
				break;	  
			}
			if (this.isCancelled())
				return ScoutResultStatusCodes.CANCELLED;
			
			raidRegion.changeOpponent();
			gold += 1000;
			publish(new ScoutWorkerData(glory, 1000));
			
			if (gold >= maxGold) {
				return ScoutResultStatusCodes.ERROR_MAX_GOLD_SPENT;
			}
			if (this.isCancelled())
				return ScoutResultStatusCodes.CANCELLED;
				
		}
		return ScoutResultStatusCodes.SUCCESS;
	}	
	
	@Override
	protected void process(List<ScoutWorkerData> chunks) {
		for (ScoutWorkerData scoutData : chunks) {
			if (scoutData.getGold() > 0)
				_huntDataHolder.spendGold(scoutData.getGold());
			_huntDataHolder.setGlory(scoutData.getGlory());
		}		
	}
	
	@Override
	protected void done()
	{
		if (this.isCancelled())
		{
			_huntDataHolder.setHuntResultStatus(ScoutResultStatusCodes.CANCELLED);	
		}
		else
		{
			ScoutResultStatusCodes result = ScoutResultStatusCodes.ERROR_UNDEFINED;
			try {
				result = get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			_huntDataHolder.setHuntResultStatus(result);
		}
	}
	
}