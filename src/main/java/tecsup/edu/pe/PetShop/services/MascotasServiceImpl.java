package tecsup.edu.pe.PetShop.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tecsup.edu.pe.PetShop.entities.Mascotas;
import tecsup.edu.pe.PetShop.repositories.MascotasRepository;

@Service
@Transactional
public class MascotasServiceImpl implements MascotasService {

	@Autowired
	private MascotasRepository  mascotasRepository;
	
	@Override
	public List<Mascotas> findAll() {
		return mascotasRepository.findAll();
	}

	@Override
	public Mascotas findById(Long id) {
		return mascotasRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe registro"));
	}

	@Override
	public void save(Mascotas mascota) {
		mascotasRepository.save(mascota);
	}

	@Override
	public void deleteById(Long id) {
		mascotasRepository.deleteById(id);
	}

}

