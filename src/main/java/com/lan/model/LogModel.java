package com.lan.model;

public class LogModel {
  private Long id;
  private Integer status;
  private Long player1;
  private Long player2;
  private Long point1;
  private Long point2;
  private Long cpoint1;
  private Long cpoint2;
  private Long gameID;

  public LogModel(Integer status, Long player1, Long player2, Long point1, Long point2, Long cpoint1, Long cpoint2, Long gameID) {
    super();
    this.status = status;
    this.player1 = player1;
    this.player2 = player2;
    this.point1 = point1;
    this.point2 = point2;
    this.cpoint1 = cpoint1;
    this.cpoint2 = cpoint2;
    this.setGameID(gameID);
  }

  public LogModel() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Long getPlayer1() {
    return player1;
  }

  public void setPlayer1(Long player1) {
    this.player1 = player1;
  }

  public Long getPlayer2() {
    return player2;
  }

  public void setPlayer2(Long player2) {
    this.player2 = player2;
  }

  public Long getPoint1() {
    return point1;
  }

  public void setPoint1(Long point1) {
    this.point1 = point1;
  }

  public Long getPoint2() {
    return point2;
  }

  public void setPoint2(Long point2) {
    this.point2 = point2;
  }

  public Long getCpoint1() {
    return cpoint1;
  }

  public void setCpoint1(Long cpoint1) {
    this.cpoint1 = cpoint1;
  }

  public Long getCpoint2() {
    return cpoint2;
  }

  public void setCpoint2(Long cpoint2) {
    this.cpoint2 = cpoint2;
  }

  public Long getGameID() {
    return gameID;
  }

  public void setGameID(Long gameID) {
    this.gameID = gameID;
  }

}
