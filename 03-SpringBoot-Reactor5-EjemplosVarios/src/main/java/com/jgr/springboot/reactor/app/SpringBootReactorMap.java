package com.jgr.springboot.reactor.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.jgr.springboot.reactor.app.auxiliar.FakerChuck;
import com.jgr.springboot.reactor.app.auxiliar.FakerGameOfThrones;
import com.jgr.springboot.reactor.app.auxiliar.FakerYoda;
import com.jgr.springboot.reactor.app.models.Usuario;



// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 * 
 * Creamos un Flux a partir de un iterable
 * Usando FlatMap verificamos si cumple la condicion devolvemos un objeto Flux,en este caso MONO porque seria un unico elemento
 */
@SpringBootApplication
public class SpringBootReactorMap implements CommandLineRunner{

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SpringBootReactorMap.class);
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorMap.class, args);
	}

	
	/**
	 * Run.
	 *
	 * @param args the args
	 * @throws Exception the exception
	 */
	@Override
	public void run(String... args) throws Exception {

//			ejemploIterable();
//			ejemploFlatMap();
			ejemploToString();
		
	}
	
	/**
	 * Creamos un Flux a partir de un iterable.
	 *
	 * @throws Exception the exception
	 */
	
	public void ejemploIterable() throws Exception {
		
		
		List<String> nombresList = new ArrayList<>();
		
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		
		
		Flux<String> fluxIterable = Flux.fromIterable(nombresList);
		
		Flux<Usuario> datosPrueba3 = fluxIterable
				.map(usu-> new Usuario(usu.split("-")[0],usu.split("-")[1]))			
				
				.doOnNext(elem->{
					if(elem==null) {
						throw new RuntimeException("datosPrueba3Vacio");
					}
					System.out.println("elemento->"+elem.getNombre().concat("/").concat(elem.getApellido()));
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
	
	/**
	 * Ejemplo flat map.
	 * usando un FlatMap filtramos, como se hace de manera individual devolvemos un mono
	 * con o sin datos, que se iran añadiendo al flux inicial
	 *
	 * @throws Exception the exception
	 */
	public void ejemploFlatMap() throws Exception {
		
		
		List<String> nombresList = new ArrayList<>();
		
		nombresList.add(new FakerChuck(new Random()).getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda(new Random()).getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck(new Random()).getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck().getFrase().concat("-").concat(new FakerYoda(new Random()).getFraseYoda().toUpperCase()));
		nombresList.add(new FakerChuck(new Random()).getFrase().concat("-").concat(new FakerYoda().getFraseYoda().toUpperCase()));
		nombresList.add(nombresList.get(0));
		
	
		//si la entrada contiene una "c" devolvemos un unico elemento,por eso usamos Mono
		Flux.fromIterable(nombresList)
				.map(usu-> new Usuario(usu.split("-")[0],usu.split("-")[1]))
				.flatMap(entrada->{
					if(entrada.getApellido().contains("D")) {
						return Mono.just(entrada);
					}else {
						return Mono.empty();
					}
				})
				.map(usuario->{				
					usuario.setNombre(usuario.getNombre().toLowerCase());
					usuario.setApellido(usuario.getApellido().toUpperCase());
					return usuario;
				})
				.subscribe(e->log.info(e.toString()))
				;
		
		
	}
	
	/**
	 * Ejemplo to string.
	 * convertimos una lista de flux usuarios a un unico string
	 *
	 * @throws Exception the exception
	 */
	public void ejemploToString() throws Exception {
		
		
		List<Usuario> usuariosList = new ArrayList<>();
		
		usuariosList.add(new Usuario(new FakerChuck().getFrase(),new FakerYoda(new Random()).getFraseYoda().toUpperCase()));
		usuariosList.add(new Usuario(new FakerChuck(new Random()).getFrase(),new FakerYoda(new Random()).getFraseYoda().toUpperCase()));
		usuariosList.add(new Usuario(new FakerChuck(new Random()).getFrase(),new FakerYoda(new Random()).getFraseYoda().toUpperCase()));
		usuariosList.add(usuariosList.get(0));
		usuariosList.add(usuariosList.get(1));
		
		
		//si la entrada contiene una "c" devolvemos un unico elemento,por eso usamos Mono
		Flux.fromIterable(usuariosList)
		.map(usu->usu.getNombre().concat(" ").concat(usu.getApellido().toUpperCase()))//unimos nombre y apelllido
		.flatMap(entrada->{
			if(entrada.contains("B")) {
				return Mono.just(entrada);
			}else {
				return Mono.empty();
			}
		})
		.map(cadena->{			
			return cadena;
		})
		.subscribe(e->log.info(e.toString()))
		;
		
		
	}

}
