package com.lan.viewmodel;

public class PlayerInfoModel {
  private Long id;
  private String fullname;
  private Integer winscount;
  private Integer losecount;
  private Long point;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public Integer getWinscount() {
    return winscount;
  }

  public void setWinscount(Integer winscount) {
    this.winscount = winscount;
  }

  public Integer getLosecount() {
    return losecount;
  }

  public void setLosecount(Integer losecount) {
    this.losecount = losecount;
  }

  public Long getPoint() {
    return point;
  }

  public void setPoint(Long point) {
    this.point = point;
  }

}
