package skal.tools.DHBot;

public class ScoutDataHolder extends BasePropertyChangeSupport
{
	private int totalGoldSpent =0;
	private int goldSpent =0;
	private int glory = -1;
	private ScoutResultStatusCodes huntResultStatus = ScoutResultStatusCodes.SUCCESS;
	private Boolean skipRegionChecks = false;

	private String gloryAsString = "---";

	public int getGoldSpent() {
		return goldSpent;
	}

	private void setGoldSpent(int newValue) {
		int oldvalue = goldSpent;
		if (oldvalue == newValue) return;
		this.goldSpent = newValue;
		firePropertyChange("goldSpent", oldvalue, newValue);
	}
	
	public int spendGold(int gold)
	{
		setGoldSpent(getGoldSpent() + gold);
		setTotalGoldSpent(getTotalGoldSpent() + gold);
		return getGoldSpent();
	}

	public void resetGoldSpend() {
		setGoldSpent(0);
	}

	public int getGlory() {
		return glory;
	}

	public void setGlory(int glory) {
		if (glory == this.glory) return;
		
		this.glory = glory;
		String oldvalue = gloryAsString;
		this.gloryAsString = glory >= 0 ? Integer.toString(glory) : "---";
		firePropertyChange("gloryAsString", oldvalue, gloryAsString);
	}

	public String getGloryAsString() {
		return gloryAsString;
	}
	
	public ScoutResultStatusCodes getHuntResultStatus() {
		return huntResultStatus;
	}

	public void setHuntResultStatus(ScoutResultStatusCodes huntResultStatus) {
		this.huntResultStatus = huntResultStatus;
	}

	public int getTotalGoldSpent() {
		return totalGoldSpent;
	}

	private void setTotalGoldSpent(int newValue) {
		int oldvalue = totalGoldSpent;
		if (oldvalue == newValue) return;
		this.totalGoldSpent = newValue;
		firePropertyChange("totalGoldSpent", oldvalue, newValue);
	}

	public Boolean getSkipRegionChecks() {
		return skipRegionChecks;
	}

	public void setSkipRegionChecks(Boolean newValue) {
		Boolean oldvalue = skipRegionChecks;
		if (oldvalue == newValue) return;
		this.skipRegionChecks = newValue;
		firePropertyChange("skipRegionChecks", oldvalue, newValue);
	}


	

}