package com.lan.model;

public class User {
	private Long id;
	private String name;
	private int wins_count, loses_count;

	public User() {
	}

	public User(Long id, String name, int wins_count, int loses_count) {
		this.id = id;
		this.name = name;
		this.wins_count = wins_count;
		this.loses_count = loses_count;
	}

	
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWins_count() {
		return wins_count;
	}

	public void setWins_count(int wins_count) {
		this.wins_count = wins_count;
	}

	public int getLoses_count() {
		return loses_count;
	}

	public void setLoses_count(int loses_count) {
		this.loses_count = loses_count;
	}
}
