package com.jgr.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class SpringBootReactor implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(SpringBootReactor.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactor.class, args);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
		Flux<String> nombres = Flux.just("nombre1","nombre2","nombre3")
				.doOnNext(elemento->System.out.println(elemento))//obtenemos un elemento nombre,lo imprimimos				
				//.doOnNext(System.out::println) //igual, pero como referencia a metodo
				; 		
		nombres.subscribe(log::info);//nos suscribimos al elemento para que lo invoque
		
		
		//emulamos error		
		Flux<String> nombreError = Flux.just("nombre1Error","nombre2Error",
				//"", //descomentar para que provoque error
				"nombre3Error")
		
				.doOnNext(elem->{
					if(elem.isEmpty()) {
						throw new RuntimeException("NombreErrorVacio");
					}
					System.out.println("elemento->"+elem);
				});		
		//manejamos el error
		nombreError.subscribe(e->log.info(e),
				error->log.error(error.getMessage()));
		
		
		//en el doOnComplete ejecuta un runnable como un hilo,lo creamos como clase anonima		
		Flux<String> nombreErrorRunnable = Flux.just("nombreErrorRunnable1","nombreErrorRunnable2",
				//"",  //quitar esto para provocar error,no se ejecutaria el runnable porque no acaba ok
				"nombreErrorRunnable3")
				
				.doOnNext(elem->{
					if(elem.isEmpty()) {
						throw new RuntimeException("NombreErrorRunnableVacio");
					}
					System.out.println("elemento->"+elem);
				});		
		//nos suscribimos para tratarlo
		nombreErrorRunnable.subscribe(e->log.info(e),
				error->log.error(error.getMessage()),new Runnable() {
					
					@Override
					public void run() {
						log.info("Ha terminado la ejecucion del flux");
						
					}
				});
	}

}
