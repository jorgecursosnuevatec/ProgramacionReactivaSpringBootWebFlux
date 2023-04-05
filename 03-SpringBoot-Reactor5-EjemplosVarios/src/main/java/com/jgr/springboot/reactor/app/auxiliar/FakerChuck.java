package com.jgr.springboot.reactor.app.auxiliar;

import java.util.Random;

import com.github.javafaker.Faker;


public class FakerChuck {
	
	private Faker faker;
	private String frase;

	public FakerChuck() {		
		this.faker = new Faker(new Random());
		this.frase = faker.chuckNorris().fact();
	}
	
	public FakerChuck(Random r) {		
		this.faker = new Faker(r);
		this.frase = faker.chuckNorris().fact();
	}
		

	public String getFrase() {
		return this.frase;
	}

	public void setFrase(String frase) {
		this.frase = frase;
	}
	
	
	
	

}
