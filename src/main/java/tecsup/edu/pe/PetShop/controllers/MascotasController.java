package tecsup.edu.pe.PetShop.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tecsup.edu.pe.PetShop.entities.Mascotas;
import tecsup.edu.pe.PetShop.services.MascotasService;
import tecsup.edu.pe.PetShop.services.MascotasServiceImpl;

@RestController
public class MascotasController {

private static final Logger logger = LoggerFactory.getLogger(MascotasController.class);
	
	@Value("${app.storage.path}")
	private String STORAGEPATH;
	
	@Autowired
	private MascotasService mascotaService;
	
	@GetMapping("/mascotas")
	public List<Mascotas> mascotas() {
		logger.info("call mascotas");
		
		List<Mascotas> mascotas = mascotaService.findAll();
		logger.info("mascotas: " + mascotas);
		
		return mascotas;
	}
	
	@GetMapping("/mascotas/images/{filename:.+}")
	public ResponseEntity<Resource> files(@PathVariable String filename) throws Exception{
		logger.info("call images: " + filename);
		
		Path path = Paths.get(STORAGEPATH).resolve(filename);
		logger.info("Path: " + path);
		
		if(!Files.exists(path)) {
			return ResponseEntity.notFound().build();
		}
		
		Resource resource = new UrlResource(path.toUri());
		logger.info("Resource: " + resource);
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(STORAGEPATH).resolve(filename)))
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
				.body(resource);
	}
	
	@PostMapping("/mascotas")
	public Mascotas crear(@RequestParam(name="imagen", required=false) MultipartFile imagen, @RequestParam("nombre") String nombre, @RequestParam("edad") String edad,@RequestParam("raza") String raza
			,@RequestParam("dueño") String dueño,@RequestParam("correo") String correo) throws Exception {
		logger.info("call crear(" + nombre + ", " + edad + ", " + raza + ", " + imagen + ","+dueño+","+correo +")");
		
		Mascotas mascota = new Mascotas();
		mascota.setNombre(nombre);
		mascota.setEdad(edad);
		mascota.setRaza(raza);
		mascota.setDueño(dueño);
		mascota.setCorreo(correo);
		
		if (imagen != null && !imagen.isEmpty()) {
			String filename = System.currentTimeMillis() + imagen.getOriginalFilename().substring(imagen.getOriginalFilename().lastIndexOf("."));
			mascota.setImagen(filename);
			if(Files.notExists(Paths.get(STORAGEPATH))){
		        Files.createDirectories(Paths.get(STORAGEPATH));
		    }
			Files.copy(imagen.getInputStream(), Paths.get(STORAGEPATH).resolve(filename));
		}
		
		mascotaService.save(mascota);
		
		return mascota;
	}

	
}
