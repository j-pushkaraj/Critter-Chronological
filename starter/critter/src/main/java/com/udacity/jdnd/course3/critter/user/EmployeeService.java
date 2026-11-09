package com.udacity.jdnd.course3.critter.user;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long employeeId){
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            return employee;
        }
        else{
            return null;

        }
    }

    public Employee setEmployeeAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }
    public List<Employee> findEmployeesForService(Set<EmployeeSkill> requiredSkills, LocalDate date){

        DayOfWeek day = date.getDayOfWeek();
        List<Employee> employeesWorkingOnDay = employeeRepository.findAllByDaysAvailable(day);
        List<Employee> matchingEmployees = new ArrayList<>();
        for(Employee employee: employeesWorkingOnDay){
            if(employee.getEmployeeSkills().containsAll(requiredSkills)){
                matchingEmployees.add(employee);
            }
        }
        return matchingEmployees;

    }




}
