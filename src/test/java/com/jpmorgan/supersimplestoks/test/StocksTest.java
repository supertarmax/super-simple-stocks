package com.jpmorgan.supersimplestoks.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmorgan.supersimplestoks.model.Trade;
import com.jpmorgan.supersimplestoks.utils.Util;

public class StocksTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @Test
  public void calculateDividendCommonTest() {
    int lastDividend = 4;
    int tickerPrice = 2;

    assertEquals(Util.calculateDividendCommon(lastDividend, tickerPrice), new Integer(2));

    lastDividend = 4;
    tickerPrice = 0;
    assertEquals(Util.calculateDividendCommon(lastDividend, tickerPrice), null);
  }

  @Test
  public void calculateDividendPreferredTest() {
    float fixedDividend = 4f;
    int parValue = 3;
    int tickerPrice = 2;

    assertEquals(Util.calculateDividendPreferred(fixedDividend, parValue, tickerPrice), new Integer(6));

    fixedDividend = 4f;
    parValue = 2;
    tickerPrice = 0;
    assertEquals(Util.calculateDividendPreferred(fixedDividend, parValue, tickerPrice), null);
  }

  @Test
  public void calculateGeometricMeanTest() {
    List<Trade> trades = new ArrayList<Trade>();

    Trade trade = new Trade();
    trade.setDate(new DateTime());
    trade.setPrice(100);
    trade.setQuantity(2);
    trade.setType(Trade.Type.BUY);

    Trade trade2 = new Trade();
    trade2.setDate(new DateTime());
    trade2.setPrice(25);
    trade2.setQuantity(5);
    trade2.setType(Trade.Type.BUY);

    trades.add(trade);
    trades.add(trade2);

    assertEquals(Util.calculateGeometricMean(trades), new Integer(50));

  }

  @Test
  public void calculatePERationTest() {
    int tickerPrice = 10;
    int dividend = 2;

    assertEquals(Util.calculatePERatio(tickerPrice, dividend), new Integer(5));

    tickerPrice = 10;
    dividend = 0;

    assertEquals(Util.calculatePERatio(tickerPrice, dividend), null);

  }

  @Test
  public void calculateStockPriceTest() {
    List<Trade> trades = new ArrayList<Trade>();

    Trade trade = new Trade();
    trade.setDate(new DateTime());
    trade.setPrice(100);
    trade.setQuantity(2);
    trade.setType(Trade.Type.BUY);

    Trade trade2 = new Trade();
    trade2.setDate(new DateTime());
    trade2.setPrice(55);
    trade2.setQuantity(5);
    trade2.setType(Trade.Type.BUY);

    trades.add(trade);
    trades.add(trade2);

    // (100*2 + 55*5)/(5+2)
    assertEquals(Util.calculateStockPrice(trades), new Integer(68));

  }
}
