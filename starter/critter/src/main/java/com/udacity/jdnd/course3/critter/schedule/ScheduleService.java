package com.udacity.jdnd.course3.critter.schedule;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public  List<Schedule> getSchedulesForPet(Long petId){
        return scheduleRepository.findAllByPets_PetId(petId);
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId){
        return  scheduleRepository.findAllByEmployees_EmployeeId(employeeId);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId){
        return scheduleRepository.findAllByPets_Customer_CustomerId(customerId);

    }

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds ){
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);


    }
}
