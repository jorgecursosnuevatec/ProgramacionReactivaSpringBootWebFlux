package com.jgr.springboot.reactor.app;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

import com.jgr.springboot.reactor.app.auxiliar.FakerGameOfThrones;
import com.jgr.springboot.reactor.app.models.Usuario;


/**
 * The Class Application.
 * 
 * En el MAP cambiamos de minusculas a mayuscular, pero el flux es INMUTABLE.
 */
@SpringBootApplication
public class SpringBootReactorMap implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorMap.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorMap.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {

		
		Flux<String> datosPrueba3 = Flux.just(
				new FakerGameOfThrones().getCasa().concat(" ").concat(new FakerGameOfThrones().getDragon()),
				new FakerGameOfThrones(new Random()).getCasa().concat(" ").concat(new FakerGameOfThrones(new Random()).getDragon())
				);
		
		//aunque cambiemos a mayusculas y le concatenemos algo el flux original ES INMUTABLE	
		datosPrueba3 
				.map(usu-> new Usuario(usu.split(" ")[0],usu.split(" ")[1]))				
//				.filter(usu->usu.getNombre().equalsIgnoreCase("nombre1"))
				.doOnNext(elem->{
					if(elem==null) {
						throw new RuntimeException("datosPrueba3Vacio");
					}
					System.out.println("elemento->"+elem.getNombre().concat(" ").concat(elem.getApellido().toUpperCase()));
				})
				.map(usuario->{				
					usuario.setNombre(usuario.getNombre().toLowerCase());
					usuario.setApellido(usuario.getApellido().toUpperCase());
					return usuario;
				})				
				;
		//nos suscribimos y sacamos los datos
		datosPrueba3.subscribe(e->log.info(e.toString()),
				error->log.error(error.getMessage()),new Runnable() {					
					@Override
					public void run() {
						log.info("Ha terminado la ejecucion del flux datosPrueba3");
						
					}
				});
				
				
				
		
	}

}
