package tecsup.edu.pe.PetShop.services;

import java.util.List;

import tecsup.edu.pe.PetShop.entities.Mascotas;

public interface MascotasService{

	public List<Mascotas> findAll();
	
	public Mascotas findById(Long id);
	
	//posible errors
	public void save(Mascotas mascota);
	
	public void deleteById(Long id);
	
} 

