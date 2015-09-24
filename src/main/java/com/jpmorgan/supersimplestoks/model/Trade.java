package com.jpmorgan.supersimplestoks.model;

import lombok.Data;

import org.joda.time.DateTime;

@Data
public class Trade {

  public enum Type {
    SELL, BUY
  };

  private Type type; 
  private int price;
  private DateTime date;
  private int quantity;

}
