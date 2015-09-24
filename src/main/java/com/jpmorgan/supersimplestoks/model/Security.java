package com.jpmorgan.supersimplestoks.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Security {

  public enum Type {
    COMMON, PREFERRED
  };

  public enum StockSymbol {
    TEA, POP, ALE, GIN, JOE
  };

  
  private Type type;
  private Integer lastDividend;
  private Float fixedDividend;
  private Integer parValue;
   
  private Integer PERatio;
  private Integer geometricMean;
  private Integer stockPrice;

}
