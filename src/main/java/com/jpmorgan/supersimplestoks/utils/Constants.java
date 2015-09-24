package com.jpmorgan.supersimplestoks.utils;

import com.jpmorgan.supersimplestoks.model.Security;
import com.jpmorgan.supersimplestoks.model.Security.Type;

public class Constants {

  //sample values

  public static final Type TEA_TYPE = Security.Type.COMMON;
  public static final Type POP_TYPE = Security.Type.COMMON;
  public static final Type ALE_TYPE = Security.Type.COMMON;
  public static final Type GIN_TYPE = Security.Type.PREFERRED;
  public static final Type JOE_TYPE = Security.Type.COMMON;

  public static final int TEA_LAST_DIVIDEND = 0;
  public static final int POP_LAST_DIVIDEND = 8;
  public static final int ALE_LAST_DIVIDEND = 23;
  public static final int GIN_LAST_DIVIDEND = 8;
  public static final int JOE_LAST_DIVIDEND = 13;

  public static final float GIN_FIXED_DIVIDEND = 2f;

  public static final int TEA_PAR_VALUE = 100;
  public static final int POP_PAR_VALUE = 100;
  public static final int ALE_PAR_VALUE = 60;
  public static final int GIN_PAR_VALUE = 100;
  public static final int JOE_PAR_VALUE = 250;

  //columns name  
  public static final String STOCK_SYMBOL = "Stock Symbol";
  public static final String TYPE = "Type";
  public static final String LAST_DIVIDEND = "Last Dividend";
  public static final String FIXED_DIVIDEND = "Fixed Dividend";
  public static final String PAR_VALUE = "Par value";

  public static final String PER_RATIO = "P/E Ratio";
  public static final String GEOMETRIC_MEAN = "Geometric Mean";
  public static final String STOCK_PRICE = "Stock price";

  // menu  
  public static final String BUY_OR_SELL = "Do you want to buy ? (otherwise you want to sell) ";
  public static final String ERROR_BUY_OR_SELL = "Type 'y' to buy and 'n' to sell";
  public static final String CHOOSE_SECURITY = "Type one of the stock Symbol";
  public static final String ERROR_CHOOSE_SECURITY = "Enter one among 'TEA', 'POP', 'ALE', 'GIN' or 'JOE' ";
  public static final String SHARE_NUMBER = "Type how many shares";
  public static final String ERROR_SHARE_NUMBER = "Enter an integer positive number ";
  public static final String BYE = "Bye guys!!!";

  //other  
  public static final int MINUTES_THRESHOLD = 1000 * 60 * 15;

}
