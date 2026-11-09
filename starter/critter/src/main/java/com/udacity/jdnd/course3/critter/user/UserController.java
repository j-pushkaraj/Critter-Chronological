package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private  EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

        Customer customer = convertDTOToCustomer(customerDTO);
        Customer savedCustomer = customerService.saveCustomer(customer);
        return convertCustomerToDTO(savedCustomer);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customer = customerService.getAllCustomer();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for(Customer customers : customer){
            customerDTOS.add(convertCustomerToDTO(customers));
        }
        return customerDTOS;
    }


    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetId(petId);
        return convertCustomerToDTO(customer);
    }


    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = convertDTOToEmployee(employeeDTO);
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return convertEmployeeToDTO(savedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        return convertEmployeeToDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.setEmployeeAvailability(daysAvailable,employeeId );
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<Employee> employee = employeeService.findEmployeesForService(employeeDTO.getSkills(), employeeDTO.getDate());
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee e : employee){
            employeeDTOS.add(convertEmployeeToDTO(e));
        }
        return employeeDTOS;
    }


    // Helper: Turn an Entity into a DTO
    private CustomerDTO convertCustomerToDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getCustomerId());
        customerDTO.setName(customer.getCustomerName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getCustomerNote());

        // We only want to send the Pet IDs over the internet, not the whole Pet objects!
        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null) {
            for (Pet pet : customer.getPets()) {
                petIds.add(pet.getPetId());
            }
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    // Helper: Turn a DTO into an Entity
    private Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getId());
        customer.setCustomerName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setCustomerNote(customerDTO.getNotes());
        return customer;
    }

    // Helper: Turn an Employee Entity into a DTO
    private EmployeeDTO convertEmployeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Using your specific entity getters!
        employeeDTO.setId(employee.getEmployeeId());
        employeeDTO.setName(employee.getEmployeeName());
        employeeDTO.setSkills(employee.getEmployeeSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());

        return employeeDTO;
    }

    // Helper: Turn a DTO into an Employee Entity
    private Employee convertDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // Using your specific entity setters!
        employee.setEmployeeId(employeeDTO.getId());
        employee.setEmployeeName(employeeDTO.getName());
        employee.setEmployeeSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());

        return employee;
    }



}
