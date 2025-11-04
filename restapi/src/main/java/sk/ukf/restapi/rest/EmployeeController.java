package sk.ukf.restapi.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import sk.ukf.restapi.entity.Employee;
import sk.ukf.restapi.exception.EmailAlreadyExistsException;
import sk.ukf.restapi.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Value("${job.titles}")
    private String[] jobTitles;

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String listEmployees(Model model) {
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "list";
    }

    @GetMapping("/{id}")
    public String viewEmployee(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("jobTitles", jobTitles);
        return "form";
    }

    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult, Model model) throws JsonProcessingException {

        model.addAttribute("jobTitles", jobTitles);

        if (bindingResult.hasErrors()) {
            return "form";
        }

        try {
            employeeService.save(employee);
            return "redirect:/employees";
        } catch (EmailAlreadyExistsException ex) {
            bindingResult.rejectValue("email", "email.exists", ex.getMessage());
            return "form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("jobTitles", jobTitles);
        return "form";
    }


    @PostMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable int id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }
}
