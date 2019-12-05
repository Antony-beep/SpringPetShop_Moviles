package tecsup.edu.pe.PetShop.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import tecsup.edu.pe.PetShop.entities.Mascotas;

public interface MascotasRepository extends CrudRepository<Mascotas, Long> {

	@Override
	List<Mascotas> findAll();
	
}

