package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.ScheduleService;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import com.udacity.jdnd.course3.critter.user.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {


    @Autowired
    private  PetService petService;

    @Autowired
    private CustomerService customerService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = convertDTOToPet(petDTO);
        Pet savedPet = petService.savePet(pet, petDTO.getOwnerId());
        return convertPetToDTO(savedPet);
    }
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return  convertPetToDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<Pet> petList = petService.getAllPets();
        List<PetDTO> petDTOList = new ArrayList<>();
        for(Pet p : petList){
            petDTOList.add(convertPetToDTO(p));
        }
        return  petDTOList;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<Pet> petList = petService.getPetByOwner(ownerId);
        List<PetDTO> petDTOList = new ArrayList<>();
        for(Pet p: petList){
            petDTOList.add(convertPetToDTO(p));
        }
        return  petDTOList;
    }



    // Helper: Turn a Pet Entity into a DTO
    private PetDTO convertPetToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();

        // 1. Basic details
        petDTO.setId(pet.getPetId());
        petDTO.setName(pet.getPetName());
        petDTO.setType(pet.getPetType());
        petDTO.setBirthDate(pet.getPetDob());
        petDTO.setNotes(pet.getPetNote());

        // 2. The Link: Only grab the ID of the customer if the customer exists
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getCustomerId());
        }

        return petDTO;
    }

    // Helper: Turn a DTO into a Pet Entity
    private Pet convertDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();

        // 1. Basic details
        pet.setPetId(petDTO.getId());
        pet.setPetName(petDTO.getName());
        pet.setPetType(petDTO.getType());
        pet.setPetDob(petDTO.getBirthDate());
        pet.setPetNote(petDTO.getNotes());

        // 2. Fetch the actual Customer object using the ID from the DTO
        if (petDTO.getOwnerId() != 0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        }

        return pet;
    }
}
