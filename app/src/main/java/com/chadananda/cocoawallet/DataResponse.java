package com.chadananda.cocoawallet;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataResponse{

	@SerializedName("price_usd")
	private double priceUsd;

	@SerializedName("symbol")
	private String symbol;

	@SerializedName("rewardsInHour")
	private double rewardsInHour;

	@SerializedName("rewardsInMonth")
	private double rewardsInMonth;

	@SerializedName("revenueInDayUSD")
	private double revenueInDayUSD;

	@SerializedName("price_btc")
	private double priceBtc;

	@SerializedName("rewardsInWeek")
	private double rewardsInWeek;

	@SerializedName("lowestAsk")
	private double lowestAsk;

	@SerializedName("currentDifficulty")
	private double currentDifficulty;

	@SerializedName("powerWatt")
	private double powerWatt;

	@SerializedName("difficulty24")
	private double difficulty24;

	@SerializedName("marketCapUSD")
	private double marketCapUSD;

	@SerializedName("revenueInYearUSD")
	private double revenueInYearUSD;

	@SerializedName("percentChange_7d")
	private double percentChange7d;

	@SerializedName("highestBid")
	private double highestBid;

	@SerializedName("profitInDayUSD")
	private double profitInDayUSD;

	@SerializedName("nethash3")
	private double nethash3;

	@SerializedName("tag")
	private Object tag;

	@SerializedName("volume_usd")
	private double volumeUsd;

	@SerializedName("nethash6")
	private double nethash6;

	@SerializedName("revenueInMonthUSD")
	private double revenueInMonthUSD;

	@SerializedName("profitInMonthUSD")
	private double profitInMonthUSD;

	@SerializedName("algorithm")
	private String algorithm;

	@SerializedName("image")
	private String image;

	@SerializedName("percentChange_1h")
	private double percentChange1h;

	@SerializedName("rewardsInDay")
	private double rewardsInDay;

	@SerializedName("profitInYearUSD")
	private double profitInYearUSD;

	@SerializedName("currentNethash")
	private double currentNethash;

	@SerializedName("profitInWeekUSD")
	private double profitInWeekUSD;

	@SerializedName("totalSupply")
	private double totalSupply;

	@SerializedName("exchanges")
	private List<ExchangesItem> exchanges;

	@SerializedName("difficulty6")
	private double difficulty6;

	@SerializedName("yourHashrate")
	private double yourHashrate;

	@SerializedName("difficulty3")
	private double difficulty3;

	@SerializedName("blockReward")
	private double blockReward;

	@SerializedName("rewardsInYear")
	private double rewardsInYear;

	@SerializedName("lastBlock")
	private double lastBlock;

	@SerializedName("kWhPriceUSD")
	private double kWhPriceUSD;

	@SerializedName("revenueInWeekUSD")
	private double revenueInWeekUSD;

	@SerializedName("lastUpdate")
	private long lastUpdate;

	@SerializedName("name")
	private String name;

	@SerializedName("nethash24")
	private double nethash24;

	@SerializedName("blockTime")
	private double blockTime;

	@SerializedName("revenueInHourUSD")
	private double revenueInHourUSD;

	@SerializedName("profitInHourUSD")
	private double profitInHourUSD;

	@SerializedName("deactive")
	private boolean deactive;

	@SerializedName("percentChange_24h")
	private double percentChange24h;

	public void setPriceUsd(double priceUsd){
		this.priceUsd = priceUsd;
	}

	public double getPriceUsd(){
		return priceUsd;
	}

	public void setSymbol(String symbol){
		this.symbol = symbol;
	}

	public String getSymbol(){
		return symbol;
	}

	public void setRewardsInHour(double rewardsInHour){
		this.rewardsInHour = rewardsInHour;
	}

	public double getRewardsInHour(){
		return rewardsInHour;
	}

	public void setRewardsInMonth(double rewardsInMonth){
		this.rewardsInMonth = rewardsInMonth;
	}

	public double getRewardsInMonth(){
		return rewardsInMonth;
	}

	public void setRevenueInDayUSD(double revenueInDayUSD){
		this.revenueInDayUSD = revenueInDayUSD;
	}

	public double getRevenueInDayUSD(){
		return revenueInDayUSD;
	}

	public void setPriceBtc(double priceBtc){
		this.priceBtc = priceBtc;
	}

	public double getPriceBtc(){
		return priceBtc;
	}

	public void setRewardsInWeek(double rewardsInWeek){
		this.rewardsInWeek = rewardsInWeek;
	}

	public double getRewardsInWeek(){
		return rewardsInWeek;
	}

	public void setLowestAsk(double lowestAsk){
		this.lowestAsk = lowestAsk;
	}

	public double getLowestAsk(){
		return lowestAsk;
	}

	public void setCurrentDifficulty(double currentDifficulty){
		this.currentDifficulty = currentDifficulty;
	}

	public double getCurrentDifficulty(){
		return currentDifficulty;
	}

	public void setPowerWatt(double powerWatt){
		this.powerWatt = powerWatt;
	}

	public double getPowerWatt(){
		return powerWatt;
	}

	public void setDifficulty24(double difficulty24){
		this.difficulty24 = difficulty24;
	}

	public double getDifficulty24(){
		return difficulty24;
	}

	public void setMarketCapUSD(double marketCapUSD){
		this.marketCapUSD = marketCapUSD;
	}

	public double getMarketCapUSD(){
		return marketCapUSD;
	}

	public void setRevenueInYearUSD(double revenueInYearUSD){
		this.revenueInYearUSD = revenueInYearUSD;
	}

	public double getRevenueInYearUSD(){
		return revenueInYearUSD;
	}

	public void setPercentChange7d(double percentChange7d){
		this.percentChange7d = percentChange7d;
	}

	public double getPercentChange7d(){
		return percentChange7d;
	}

	public void setHighestBid(double highestBid){
		this.highestBid = highestBid;
	}

	public double getHighestBid(){
		return highestBid;
	}

	public void setProfitInDayUSD(double profitInDayUSD){
		this.profitInDayUSD = profitInDayUSD;
	}

	public double getProfitInDayUSD(){
		return profitInDayUSD;
	}

	public void setNethash3(double nethash3){
		this.nethash3 = nethash3;
	}

	public double getNethash3(){
		return nethash3;
	}

	public void setTag(Object tag){
		this.tag = tag;
	}

	public Object getTag(){
		return tag;
	}

	public void setVolumeUsd(double volumeUsd){
		this.volumeUsd = volumeUsd;
	}

	public double getVolumeUsd(){
		return volumeUsd;
	}

	public void setNethash6(double nethash6){
		this.nethash6 = nethash6;
	}

	public double getNethash6(){
		return nethash6;
	}

	public void setRevenueInMonthUSD(double revenueInMonthUSD){
		this.revenueInMonthUSD = revenueInMonthUSD;
	}

	public double getRevenueInMonthUSD(){
		return revenueInMonthUSD;
	}

	public void setProfitInMonthUSD(double profitInMonthUSD){
		this.profitInMonthUSD = profitInMonthUSD;
	}

	public double getProfitInMonthUSD(){
		return profitInMonthUSD;
	}

	public void setAlgorithm(String algorithm){
		this.algorithm = algorithm;
	}

	public String getAlgorithm(){
		return algorithm;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setPercentChange1h(double percentChange1h){
		this.percentChange1h = percentChange1h;
	}

	public double getPercentChange1h(){
		return percentChange1h;
	}

	public void setRewardsInDay(double rewardsInDay){
		this.rewardsInDay = rewardsInDay;
	}

	public double getRewardsInDay(){
		return rewardsInDay;
	}

	public void setProfitInYearUSD(double profitInYearUSD){
		this.profitInYearUSD = profitInYearUSD;
	}

	public double getProfitInYearUSD(){
		return profitInYearUSD;
	}

	public void setCurrentNethash(double currentNethash){
		this.currentNethash = currentNethash;
	}

	public double getCurrentNethash(){
		return currentNethash;
	}

	public void setProfitInWeekUSD(double profitInWeekUSD){
		this.profitInWeekUSD = profitInWeekUSD;
	}

	public double getProfitInWeekUSD(){
		return profitInWeekUSD;
	}

	public void setTotalSupply(double totalSupply){
		this.totalSupply = totalSupply;
	}

	public double getTotalSupply(){
		return totalSupply;
	}

	public void setExchanges(List<ExchangesItem> exchanges){
		this.exchanges = exchanges;
	}

	public List<ExchangesItem> getExchanges(){
		return exchanges;
	}

	public void setDifficulty6(double difficulty6){
		this.difficulty6 = difficulty6;
	}

	public double getDifficulty6(){
		return difficulty6;
	}

	public void setYourHashrate(double yourHashrate){
		this.yourHashrate = yourHashrate;
	}

	public double getYourHashrate(){
		return yourHashrate;
	}

	public void setDifficulty3(double difficulty3){
		this.difficulty3 = difficulty3;
	}

	public double getDifficulty3(){
		return difficulty3;
	}

	public void setBlockReward(double blockReward){
		this.blockReward = blockReward;
	}

	public double getBlockReward(){
		return blockReward;
	}

	public void setRewardsInYear(double rewardsInYear){
		this.rewardsInYear = rewardsInYear;
	}

	public double getRewardsInYear(){
		return rewardsInYear;
	}

	public void setLastBlock(double lastBlock){
		this.lastBlock = lastBlock;
	}

	public double getLastBlock(){
		return lastBlock;
	}

	public void setKWhPriceUSD(double kWhPriceUSD){
		this.kWhPriceUSD = kWhPriceUSD;
	}

	public double getKWhPriceUSD(){
		return kWhPriceUSD;
	}

	public void setRevenueInWeekUSD(double revenueInWeekUSD){
		this.revenueInWeekUSD = revenueInWeekUSD;
	}

	public double getRevenueInWeekUSD(){
		return revenueInWeekUSD;
	}

	public void setLastUpdate(long lastUpdate){
		this.lastUpdate = lastUpdate;
	}

	public long getLastUpdate(){
		return lastUpdate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setNethash24(double nethash24){
		this.nethash24 = nethash24;
	}

	public double getNethash24(){
		return nethash24;
	}

	public void setBlockTime(double blockTime){
		this.blockTime = blockTime;
	}

	public double getBlockTime(){
		return blockTime;
	}

	public void setRevenueInHourUSD(double revenueInHourUSD){
		this.revenueInHourUSD = revenueInHourUSD;
	}

	public double getRevenueInHourUSD(){
		return revenueInHourUSD;
	}

	public void setProfitInHourUSD(double profitInHourUSD){
		this.profitInHourUSD = profitInHourUSD;
	}

	public double getProfitInHourUSD(){
		return profitInHourUSD;
	}

	public void setDeactive(boolean deactive){
		this.deactive = deactive;
	}

	public boolean isDeactive(){
		return deactive;
	}

	public void setPercentChange24h(double percentChange24h){
		this.percentChange24h = percentChange24h;
	}

	public double getPercentChange24h(){
		return percentChange24h;
	}
}