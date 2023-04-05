package com.jgr.springboot.reactor.app.auxiliar;

import java.util.Random;

import com.github.javafaker.Faker;

public class FakerCerveza {

	private Faker faker;

	private String nombre;

	public FakerCerveza() {

		this.faker = new Faker(new Random(24));
		this.nombre = this.faker.beer().name();

	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
