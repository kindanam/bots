package skal.tools.DHBot;

public class ScoutWorkerData{
	private int glory;
	private int gold;

	public ScoutWorkerData(int glory, int gold)
	{
		this.setGlory(glory);
		this.setGold(gold);
		
	}

	public int getGlory() {
		return glory;
	}

	public void setGlory(int glory) {
		this.glory = glory;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
}