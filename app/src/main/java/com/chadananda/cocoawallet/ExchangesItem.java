package com.chadananda.cocoawallet;

import com.google.gson.annotations.SerializedName;

public class ExchangesItem{

	@SerializedName("volume")
	private double volume;

	@SerializedName("buyPrice")
	private double buyPrice;

	@SerializedName("quote")
	private String quote;

	@SerializedName("price")
	private double price;

	@SerializedName("change")
	private double change;

	@SerializedName("lastUpdate")
	private int lastUpdate;

	@SerializedName("name")
	private String name;

	@SerializedName("sellPrice")
	private double sellPrice;

	@SerializedName("price_in_base")
	private double priceInBase;

	@SerializedName("base")
	private String base;

	public void setVolume(double volume){
		this.volume = volume;
	}

	public double getVolume(){
		return volume;
	}

	public void setBuyPrice(double buyPrice){
		this.buyPrice = buyPrice;
	}

	public double getBuyPrice(){
		return buyPrice;
	}

	public void setQuote(String quote){
		this.quote = quote;
	}

	public String getQuote(){
		return quote;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setChange(double change){
		this.change = change;
	}

	public double getChange(){
		return change;
	}

	public void setLastUpdate(int lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public int getLastUpdate(){
		return lastUpdate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setSellPrice(double sellPrice){
		this.sellPrice = sellPrice;
	}

	public double getSellPrice(){
		return sellPrice;
	}

	public void setPriceInBase(double priceInBase){
		this.priceInBase = priceInBase;
	}

	public double getPriceInBase(){
		return priceInBase;
	}

	public void setBase(String base){
		this.base = base;
	}

	public String getBase(){
		return base;
	}
}