package objects;

public class TradeRecord {
	private String date;
	private String stockID;
	private double price;
	private int numShares;
	private int direction;

	public TradeRecord(String date, String stockID, double price, int numShares, int direction) {
		this.date = date;
		this.stockID = stockID;
		this.price = price;
		this.numShares = numShares;
		this.direction = direction;
	}

	public TradeRecord() {
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStockID() {
		return stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNumShares() {
		return numShares;
	}

	public void setNumShares(int numShares) {
		this.numShares = numShares;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
