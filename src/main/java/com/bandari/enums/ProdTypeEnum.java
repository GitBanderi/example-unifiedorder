package com.bandari.enums;

public enum ProdTypeEnum {

  ORDINARY(1, "ORDINARY"),

  NEW_PEOPLE(2, "NEW_PEOPLE"),

  NEW_MEMBER(3, "NEW_MEMBER"),

  ;

  private int code;
  private String name;

  ProdTypeEnum(int seq, String name) {
    this.code = seq;
    this.name = name;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int seq) {
    this.code = seq;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
