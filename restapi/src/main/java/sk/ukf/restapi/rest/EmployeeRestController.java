package sk.ukf.restapi.rest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ukf.restapi.dto.ApiResponse;
import sk.ukf.restapi.entity.Employee;
import sk.ukf.restapi.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<Employee>>> findAll() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(ApiResponse.success(employees, "Zoznam všetkých zamestnancov"));
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployee(@PathVariable int id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(employee, "Zamestnanec s ID " + id + " nájdený"));
    }

    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<Employee>> addEmployee(@Valid @RequestBody Employee employee) {
        employee.setId(null);
        Employee created = employeeService.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Zamestnanec úspešne pridaný"));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable int id,
            @Valid @RequestBody Employee employee
    ) {
        employeeService.findById(id);
        employee.setId(id);
        Employee updated = employeeService.save(employee);
        return ResponseEntity.ok(ApiResponse.success(updated, "Zamestnanec s ID " + id + " bol aktualizovaný"));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Zamestnanec s ID " + id + " bol odstránený", "Odstránenie úspešné"));
    }
}