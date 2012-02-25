package com.twitstreet.db.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockInPortfolio implements DataObjectIF{
	long stockId;
	String stockName;
	double amount;
	private double total;
	String pictureUrl;
	double capital;
	double changePerHour;
	private double totalChangePerHour;
	private double percentage;
	private boolean verified;
	private boolean changePerHourCalculated;

	public StockInPortfolio() {
		// TODO Auto-generated constructor stub
	}

	public long getStockId() {
		return stockId;
	}

	public void setStockId(long stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public double getChangePerHour() {
		return changePerHour;
	}

	public void setChangePerHour(double changePerHour) {
		this.changePerHour = changePerHour;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public void getDataFromResultSet(ResultSet rs) throws SQLException {
	
		setStockId(rs.getLong("stockId"));
		setStockName(rs.getString("stockName"));
		setAmount(rs.getDouble("amount"));
		setPictureUrl(rs.getString("pictureUrl"));
		setCapital(rs.getDouble("capital"));
		setChangePerHour(rs.getDouble("changePerHour"));
		if(rs.wasNull()){
			
			changePerHour = 0;
			changePerHourCalculated = false;
		}
		else{
			
			changePerHourCalculated = true;
		}
		setPercentage(rs.getDouble("percentage"));
		setVerified(rs.getBoolean("verified"));
		setTotal(rs.getDouble("total"));	
		setTotalChangePerHour(rs.getDouble("totalChangePerHour"));		
		
		
	}

	public boolean isChangePerHourCalculated() {
		return changePerHourCalculated;
	}

	public void setChangePerHourCalculated(boolean changePerHourCalculated) {
		this.changePerHourCalculated = changePerHourCalculated;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotalChangePerHour() {
		return totalChangePerHour;
	}

	public void setTotalChangePerHour(double totalChangePerHour) {
		this.totalChangePerHour = totalChangePerHour;
	}
}
