package menuController;

public class NavigationData {
	private int navTo;
	private String stockID = null;

	public NavigationData(int n) {
		navTo = n;
	}

	public NavigationData(int navEnquire, String s) {
		navTo = navEnquire;
		stockID = s;
	}

	public int getNavTo() {
		return navTo;
	}

	public String getStockID() {
		return stockID;
	}
}
