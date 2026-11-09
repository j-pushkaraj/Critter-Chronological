package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {


    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet getPetById(Long petId) {
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if (optionalPet.isPresent()) {
            Pet pet = optionalPet.get();
            return pet;
        } else {
            return null;
        }
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetByOwner(Long customerId) {
        List<Pet> pets = petRepository.findAllByCustomer_CustomerId(customerId);
        return pets;
    }

    public Pet savePet(Pet pet, Long ownerId) {
        Customer owner = customerRepository.findById(ownerId).orElse(null);
        if (owner != null) {
            pet.setCustomer(owner);
        }
        Pet savedPet = petRepository.save(pet);
        if (owner != null) {
            List<Pet> pets = owner.getPets();
            if (pets == null) {
                pets = new ArrayList<>();
            }
            pets.add(savedPet);
            owner.setPets(pets);
            customerRepository.save(owner);
        }

        return savedPet;
    }


}



