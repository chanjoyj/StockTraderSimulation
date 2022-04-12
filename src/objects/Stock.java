package objects;

public class Stock {
	private String stockID;
	private int type;
	private Stock[] ETFstocks; 
	
	public String getStockID() {
		return stockID;
	}
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Stock[] getETFstocks() {
		return ETFstocks;
	}
	public void setETFstocks(Stock[] eTFstocks) {
		ETFstocks = eTFstocks;
	}
	
}
