package com.jpmorgan.supersimplestoks.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.EnumUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.jpmorgan.supersimplestoks.model.Security;
import com.jpmorgan.supersimplestoks.model.Security.StockSymbol;
import com.jpmorgan.supersimplestoks.model.Trade;
import com.jpmorgan.supersimplestoks.model.Trade.Type;
import com.jpmorgan.supersimplestoks.utils.Constants;
import com.jpmorgan.supersimplestoks.utils.Util;

public class Main {

  private static Logger logger = Logger.getLogger(Main.class);
  private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  private static Map<StockSymbol, Security> securities = new HashMap<StockSymbol, Security>();
  private static Map<StockSymbol, List<Trade>> trades = new HashMap<StockSymbol, List<Trade>>();

  public static void main(String[] args) {

    securities = Util.loadSampleData();
    Util.printTable(securities);

    while (true) {
      try {

        System.out.println(Constants.BUY_OR_SELL);
        String buyOrSell = br.readLine();
        if (buyOrSell.equalsIgnoreCase("q")) {
          System.out.println(Constants.BYE);
          System.exit(0);
        }

        // cicle if the user wrong to type
        while (!buyOrSell.equalsIgnoreCase("y") && !buyOrSell.equalsIgnoreCase("n")) {
          System.out.println(Constants.ERROR_BUY_OR_SELL);
          System.out.println(Constants.BUY_OR_SELL);
          buyOrSell = br.readLine();
        }

        System.out.println(Constants.CHOOSE_SECURITY);
        String stockSymbol = br.readLine();
        if (stockSymbol.equalsIgnoreCase("q")) {
          System.out.println(Constants.BYE);
          System.exit(0);
        }

        // cicle if the user wrong to type
        while (!EnumUtils.isValidEnum(StockSymbol.class, stockSymbol)) {
          System.out.println(Constants.ERROR_CHOOSE_SECURITY);
          System.out.println(Constants.CHOOSE_SECURITY);
          stockSymbol = br.readLine();
        }

        System.out.println(Constants.SHARE_NUMBER);
        String quantity = br.readLine();
        if (quantity.equalsIgnoreCase("q")) {
          System.out.println(Constants.BYE);
          System.exit(0);
        }

        // cicle if the user wrong to type
        while (!quantity.matches(".*\\d+.*") || Integer.valueOf(quantity) <= 0) {
          System.out.println(Constants.ERROR_SHARE_NUMBER);
          System.out.println(Constants.SHARE_NUMBER);
          quantity = br.readLine();
        }

        StockSymbol symbol = Security.StockSymbol.valueOf(stockSymbol);
        int q = Integer.valueOf(quantity);
        Type type = buyOrSell.equalsIgnoreCase("y") ? Trade.Type.BUY : Trade.Type.SELL;

        performTrade(type, symbol, q);
        updateTable(type, symbol, q);
        Util.printTable(securities);

      }
      catch (IOException e) {
        System.err.println("something went wrong");
        e.printStackTrace();
      }
    }
  }

  private static void updateTable(Type type, StockSymbol symbol, int q) {
    Security security = securities.get(symbol);
    Integer dividend;
    Integer geometricMean;
    Integer pERatio;
    Integer stockPrice;

    if (symbol.equals(Security.StockSymbol.GIN)) {
      dividend = Util.calculateDividendPreferred(security.getFixedDividend(), security.getParValue(), security.getStockPrice());
    }
    else {
      dividend = Util.calculateDividendCommon(security.getLastDividend(), security.getStockPrice());
    }

    geometricMean = Util.calculateGeometricMean(trades.get(symbol));
    pERatio = Util.calculatePERatio(security.getStockPrice() == null || security.getStockPrice().equals(0) ? security.getParValue() : security.getStockPrice(), security.getLastDividend());
    stockPrice = Util.calculateStockPrice(trades.get(symbol));

    security.setLastDividend(dividend);
    security.setGeometricMean(geometricMean);
    security.setPERatio(pERatio);
    security.setStockPrice(stockPrice);
  }

  private static void performTrade(Type type, StockSymbol symbol, int quantity) {
    Trade trade = new Trade();
    DateTime date = new DateTime();
    trade.setType(type);
    trade.setPrice(securities.get(symbol).getStockPrice());
    trade.setDate(date);
    trade.setQuantity(quantity);
    logger.info("Added trade: " + trade.toString() + " for security " + symbol);
    List<Trade> tradeList = trades.get(symbol);
    if (tradeList == null || tradeList.isEmpty()) {
      tradeList = new ArrayList<Trade>();
      tradeList.add(trade);
      trades.put(symbol, tradeList);
    }
    else {
      tradeList.add(trade);
    }

    logger.info("--ALL TRADES--");
    for (Map.Entry<StockSymbol, List<Trade>> entry : trades.entrySet()) {
      logger.info("for the security: " + entry.getKey() + ":");
      logger.info(entry.getValue());
    }
    logger.info("");

  }

}
