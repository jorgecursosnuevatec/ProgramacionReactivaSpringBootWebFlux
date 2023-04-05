package com.jgr.springboot.reactor.app.auxiliar;

import java.util.Random;

import com.github.javafaker.Faker;

public class FakerGameOfThrones {

	private Faker faker;

	private String dragon;
	private String casa;

	public FakerGameOfThrones() {

		faker = new Faker(new Random(24));
		this.dragon = faker.gameOfThrones().dragon();
		this.casa = faker.gameOfThrones().house();
	}
	public FakerGameOfThrones(Random r) {
		
		faker = new Faker(r);
		this.dragon = faker.gameOfThrones().dragon();
		this.casa = faker.gameOfThrones().house();
	}

	public String getDragon() {
		return this.dragon;
	}

	public void setDragon(String dragon) {
		this.dragon = dragon;
	}

	public String getCasa() {
		return this.casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

}
