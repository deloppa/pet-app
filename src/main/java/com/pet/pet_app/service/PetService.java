package com.pet.pet_app.service;

import com.pet.pet_app.model.Pet;
import com.pet.pet_app.repository.PetRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {

    @Autowired private PetRepository petRepository;

    public List<Pet> getAllPets() { return petRepository.findAll(); }

    public Optional<Pet> getPetById(String id) {
        return petRepository.findById(id);
    }

    public Pet createPet(Pet pet) { return petRepository.save(pet); }

    public Pet updatePet(String id, Pet updatedPet) {
        return petRepository.findById(id)
            .map(pet -> {
                pet.setAnimal(updatedPet.getAnimal());
                pet.setName(updatedPet.getName());
                pet.setOwnerName(updatedPet.getOwnerName());
                return petRepository.save(pet);
            })
            .orElse(null);
    }

    public void deletePet(String id) { petRepository.deleteById(id); }
}
