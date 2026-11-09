package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setScheduleDate(scheduleDTO.getDate());
        schedule.setActivities(scheduleDTO.getActivities());
        Schedule saveschedule = scheduleService.createSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return convertScheduleToDTO(saveschedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return convertScheduleListToDTOList(scheduleService.getAllSchedules());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return convertScheduleListToDTOList(scheduleService.getSchedulesForPet(petId));
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return  convertScheduleListToDTOList(scheduleService.getSchedulesForEmployee(employeeId));
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return convertScheduleListToDTOList((scheduleService.getSchedulesForCustomer(customerId)));
    }

    // Helper: Turn a Schedule Entity into a DTO
    private ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        // 1. Basic details
        scheduleDTO.setId(schedule.getScheduleId());
        scheduleDTO.setDate(schedule.getScheduleDate());
        scheduleDTO.setActivities(schedule.getActivities());

        // 2. Extract list of Pet IDs
        List<Long> petIds = new ArrayList<>();
        if (schedule.getPets() != null) {
            for (Pet p : schedule.getPets()) {
                petIds.add(p.getPetId());
            }
        }
        scheduleDTO.setPetIds(petIds);

        // 3. Extract list of Employee IDs
        List<Long> employeeIds = new ArrayList<>();
        if (schedule.getEmployees() != null) {
            for (Employee e : schedule.getEmployees()) {
                employeeIds.add(e.getEmployeeId());
            }
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        return scheduleDTO;
    }

    // Helper: Translate a whole list at once
    private List<ScheduleDTO> convertScheduleListToDTOList(List<Schedule> schedules) {
        List<ScheduleDTO> dtos = new ArrayList<>();
        for (Schedule s : schedules) {
            dtos.add(convertScheduleToDTO(s));
        }
        return dtos;
    }
}
