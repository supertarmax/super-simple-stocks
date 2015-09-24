package com.jpmorgan.supersimplestoks.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.jpmorgan.supersimplestoks.model.Security;
import com.jpmorgan.supersimplestoks.model.Security.StockSymbol;
import com.jpmorgan.supersimplestoks.model.Trade;

public class Util {

  private static Logger logger = Logger.getLogger(Util.class);

  public static void printTable(Map<StockSymbol, Security> securities) {

    System.out.format("%20s | %20s | %20s | %20s | %20s | %20s | %20S | %20s\n", Constants.STOCK_SYMBOL, Constants.TYPE, Constants.LAST_DIVIDEND, Constants.FIXED_DIVIDEND, Constants.PAR_VALUE, Constants.PER_RATIO, Constants.GEOMETRIC_MEAN, Constants.STOCK_PRICE);
    for (Map.Entry<StockSymbol, Security> entry : securities.entrySet()) {
      System.out.format("%20s | %20s | %20d | %20s | %20d | %20s | %20s | %20s\n", entry.getKey(), entry.getValue().getType().toString(), entry.getValue().getLastDividend(), entry.getValue().getFixedDividend() != null ? entry.getValue().getFixedDividend().toString() : "-", entry.getValue().getParValue(), entry.getValue().getPERatio() != null ? entry.getValue().getPERatio().toString() : "-", entry.getValue().getGeometricMean() != null ? entry.getValue().getGeometricMean().toString() : "-", entry.getValue().getStockPrice() != null ? entry.getValue().getStockPrice().toString() : "-");
    }
    System.out.println("All number values in pennies");
    System.out.println();

  }

  public static Integer calculateDividendCommon(int lastDividend, int tickerPrice) {
    if (tickerPrice != 0) {
      return lastDividend / tickerPrice;
    }
    else {
      logger.info("To calculate the dividend Ticker price must not be null");
      return null;
    }
  }

  public static Integer calculateDividendPreferred(Float fixedDividend, Integer parValue, Integer tickerPrice) {
    if (tickerPrice != null && !tickerPrice.equals(0)) {    
      return (int) Math.round(fixedDividend * parValue / tickerPrice);      
    }
    else {
      logger.info("To calculate the dividend Ticker price must not be null or zero");
      return null;
    }
  }

  public static Integer calculatePERatio(Integer tickerPrice, Integer dividend) {
    if (dividend != null && !dividend.equals(0)) {
      return tickerPrice / dividend;
    }
    else {
      logger.info("To calculate the PER Ratio the dividend must not be null or zero");
      return null;
    }
  }

  public static Integer calculateGeometricMean(List<Trade> trades) {
    if (trades == null || trades.isEmpty()) {
      return 0;
    }

    int price = 1;
    for (Trade trade : trades) {
      price *= trade.getPrice();
    }

    return (int) Math.round(Math.pow(Math.E, Math.log(price) / trades.size()));

  }

  public static Integer calculateStockPrice(List<Trade> trades) {
    if (trades == null || trades.isEmpty()) {
      return 0;
    }

    double numerator = 0;
    double denominator = 0;
    DateTime now = new DateTime();
    for (Trade trade : trades) {
      DateTime tradeDate = trade.getDate();
      if (now.getMillis() - tradeDate.getMillis() <= Constants.MINUTES_THRESHOLD) {
        numerator += trade.getPrice() * trade.getQuantity();
        denominator += trade.getQuantity();
      }
    }

    if (denominator == 0) {
      return 0;
    }
    return (int) Math.round(numerator / denominator);

  }

  public static Map<StockSymbol, Security> loadSampleData() {

    //assuming that stock price is the Par value
    Map<StockSymbol, Security> securities = new HashMap<StockSymbol, Security>();

    Security tea = new Security(Constants.TEA_TYPE, Constants.TEA_LAST_DIVIDEND, null, Constants.TEA_PAR_VALUE, null, null, Constants.TEA_PAR_VALUE);
    securities.put(Security.StockSymbol.TEA, tea);

    Security pop = new Security(Constants.POP_TYPE, Constants.POP_LAST_DIVIDEND, null, Constants.POP_PAR_VALUE, null, null, Constants.POP_PAR_VALUE);
    securities.put(Security.StockSymbol.POP, pop);

    Security ale = new Security(Constants.ALE_TYPE, Constants.ALE_LAST_DIVIDEND, null, Constants.ALE_PAR_VALUE, null, null, Constants.ALE_PAR_VALUE);
    securities.put(Security.StockSymbol.ALE, ale);

    Security gin = new Security(Constants.GIN_TYPE, Constants.GIN_LAST_DIVIDEND, Constants.GIN_FIXED_DIVIDEND, Constants.GIN_PAR_VALUE, null, null, Constants.GIN_PAR_VALUE);
    securities.put(Security.StockSymbol.GIN, gin);

    Security joe = new Security(Constants.JOE_TYPE, Constants.JOE_LAST_DIVIDEND, null, Constants.JOE_PAR_VALUE, null, null, Constants.JOE_PAR_VALUE);
    securities.put(Security.StockSymbol.JOE, joe);

    return securities;
  }
}
