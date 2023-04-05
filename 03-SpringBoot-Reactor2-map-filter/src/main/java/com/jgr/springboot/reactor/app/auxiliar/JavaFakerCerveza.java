package com.jgr.springboot.reactor.app.auxiliar;

import java.util.Random;

import com.github.javafaker.Faker;

public class JavaFakerCerveza {

	private Faker faker;

	private String nombre;

	public JavaFakerCerveza() {

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
