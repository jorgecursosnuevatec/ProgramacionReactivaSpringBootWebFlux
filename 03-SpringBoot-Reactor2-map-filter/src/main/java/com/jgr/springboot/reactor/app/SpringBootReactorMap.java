package com.jgr.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import com.jgr.springboot.reactor.app.models.Usuario;


/**
 * The Class Application.
 * 
 * Ejecucion por consola, se indica con el CommandLineRunner
 */
@SpringBootApplication
public class SpringBootReactorMap implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorMap.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorMap.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {

/*
		//Hacemos un map,lo convertimos a mayuscula		
		Flux<String> datosPrueba = Flux.just("nombreErrorRunnable1","nombreErrorRunnable2",
				//"",  //quitar esto para provocar error,no se ejecutaria el runnable porque no acaba ok
				"nombreErrorRunnable3")

				.doOnNext(elem->{
					if(elem.isEmpty()) {
						throw new RuntimeException("NombreErrorRunnableVacio");
					}
					System.out.println("elemento->"+elem);
				})
				.map(nombre-> {return nombre.toUpperCase();})
				;		
		//nos suscribimos para tratarlo
		datosPrueba.subscribe(e->log.info(e),
				error->log.error(error.getMessage()),new Runnable() {					
					@Override
					public void run() {
						log.info("Ha terminado la ejecucion del flux datosPrueba");

					}
				});
*/
		
/*		
		//Lo convertimos a objeto Usuario
		//con el primer map lo pasamos a mayusculas
		//con el segundo map lo pasamos a minusculas
		Flux<Usuario> datosPrueba2 = Flux.just("nombreErrorRunnable1","nombreErrorRunnable2",
				"",  //quitar esto para provocar error,no se ejecutaria el runnable porque no acaba ok
				"nombreErrorRunnable3")
				.map(nombre-> {return new Usuario(nombre.toUpperCase(),nombre.toUpperCase());})
				.doOnNext(elem->{
					if(elem==null) {
						throw new RuntimeException("NombreErrorRunnableVacio");
					}
					System.out.println("elemento->"+elem.getNombre());
				})
				.map(usuario->{
					String nombre = usuario.getNombre().toLowerCase();
					usuario.setNombre(nombre);
					return usuario;
				})				
				;
		//nos suscribimos y sacamos los datos
		datosPrueba2.subscribe(e->log.info(e.toString()+ e.hashCode()),
				error->log.error(error.getMessage()),new Runnable() {					
					@Override
					public void run() {
						log.info("Ha terminado la ejecucion del flux  datosPrueba2");

					}
				});
*/
		
		
		//Filter
		Flux<Usuario> datosPrueba3 = Flux.just("nombre1 apellido1","nombre2 apellido2",				
				"nombre3 apellido3")
				.map(usu-> new Usuario(usu.split(" ")[0],usu.split(" ")[1]))				
				.filter(usu->usu.getNombre().equalsIgnoreCase("nombre1"))
				.doOnNext(elem->{
					if(elem==null) {
						throw new RuntimeException("datosPrueba3Vacio");
					}
					System.out.println("elemento->"+elem.getNombre().concat(" ").concat(elem.getApellido()));
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
