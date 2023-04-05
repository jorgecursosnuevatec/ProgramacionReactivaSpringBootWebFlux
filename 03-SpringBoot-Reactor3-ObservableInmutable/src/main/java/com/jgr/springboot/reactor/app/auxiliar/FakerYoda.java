package com.jgr.springboot.reactor.app.auxiliar;

import java.util.Random;

import com.github.javafaker.Faker;

public class FakerYoda {

	private Faker faker;

	private String fraseYoda;

	public FakerYoda() {

		this.faker = new Faker(new Random(24));
		this.fraseYoda = faker.yoda().quote();

	}

	public String getFraseYoda() {
		return this.fraseYoda;
	}

	public void setFraseYoda(String fraseYoda) {
		this.fraseYoda = fraseYoda;
	}

}
