package com.jgr.springboot.reactor.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;


/**
 * The Class Application.
 * 
 * Ejecucion por consola, se indica con el CommandLineRunner
 */
@SpringBootApplication
public class Application implements CommandLineRunner{

	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		Flux<String> nombres = Flux.just("nombre1","nombre2","nombre3")
				.doOnNext(elemento->System.out.println(elemento))//obtenemos un elemento nombre,lo imprimimos				
				//.doOnNext(System.out::println) //igual, pero como referencia a metodo

				; 
		
		nombres.subscribe();//nos suscribimos al elemento para que lo invoque
		
		
	}

}
