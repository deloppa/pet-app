package com.pet.pet_app.controller;

import com.pet.pet_app.model.Pet;
import com.pet.pet_app.service.PetService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pets")
public class PetRestController {

  @Autowired private PetService petService;

  @GetMapping
  public List<Pet> getAllPets() {
    return petService.getAllPets();
  }

  @GetMapping("/{id}")
  public Optional<Pet> getPetById(@PathVariable String id) {
    return petService.getPetById(id);
  }

  @PostMapping
  public Pet createPet(@RequestBody Pet pet) {
    return petService.createPet(pet);
  }

  @PutMapping("/{id}")
  public Pet updatePet(@PathVariable String id, @RequestBody Pet pet) {
    return petService.updatePet(id, pet);
  }

  @DeleteMapping("/{id}")
  public void deletePet(@PathVariable String id) {
    petService.deletePet(id);
  }
}
