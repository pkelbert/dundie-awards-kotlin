package com.ninjaone.dundie_awards.controller

import com.ninjaone.dundie_awards.AwardsCache
import com.ninjaone.dundie_awards.MessageBroker
import com.ninjaone.dundie_awards.model.Employee
import com.ninjaone.dundie_awards.repository.ActivityRepository
import com.ninjaone.dundie_awards.repository.EmployeeRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.lang.Boolean.TRUE

@Controller
@RequestMapping
class EmployeeController(
    private val employeeRepository: EmployeeRepository,
    private val activityRepository: ActivityRepository,
    private val messageBroker: MessageBroker,
    private val awardsCache: AwardsCache
) {

    // get all employees
    @GetMapping("/employees")
    @ResponseBody
    fun getAllEmployees(): List<Employee> {
        return employeeRepository.findAll()
    }

    // create employee rest api
    // todo requires id to be null
    // todo requires organization to exist
    // todo organization name is useless
    @PostMapping("/employees")
    @ResponseBody
    fun createEmployee(@RequestBody employee: Employee): Employee {
        return employeeRepository.save(employee)
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    @ResponseBody
    fun getEmployeeById(@PathVariable id: Long): ResponseEntity<Employee> {
        val optionalEmployee = employeeRepository.findById(id)
        return if (optionalEmployee.isPresent) {
            ResponseEntity.ok(optionalEmployee.get())
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // update employee rest api
    // todo actually performs a patch
    @PutMapping("/employees/{id}")
    @ResponseBody
    fun updateEmployee(@PathVariable id: Long, @RequestBody employeeDetails: Employee): ResponseEntity<Employee> {
        val optionalEmployee = employeeRepository.findById(id)
        if (!optionalEmployee.isPresent) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val employee = optionalEmployee.get()
        employee.firstName = employeeDetails.firstName
        employee.lastName = employeeDetails.lastName

        val updatedEmployee = employeeRepository.save(employee)
        return ResponseEntity.ok(updatedEmployee)
    }

    // delete employee rest api
    @DeleteMapping("/employees/{id}")
    @ResponseBody
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<Map<String, Boolean>> {
        val optionalEmployee = employeeRepository.findById(id)
        if (!optionalEmployee.isPresent) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val employee = optionalEmployee.get()
        employeeRepository.delete(employee)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = TRUE
        return ResponseEntity.ok(response)
    }

}
