package sk.ukf.restapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Meno nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Meno musí mať aspoň 2 znaky a nesmie byť dlhšie ako 50 znakov")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Priezvisko nesmie byť prázdne")
    @Size(min = 2, max = 50, message = "Priezvisko musí mať aspoň 2 znaky a nesmie byť dlhšie ako 50 znakov")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull(message = "Dátum narodenia je povinný")
    @Past(message = "Dátum narodenia musí byť v minulosti")
    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank(message = "Email nesmie byť prázdny")
    @Size(max = 254, message = "Email nesmie byť dlhší ako 254 znakov")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "Neplatný formát emailovej adresy"
    )
    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Telefón nesmie byť prázdny")
    @Pattern(
            regexp= "^\\+421\\d{9}$",
            message = "Neplatné telefónne číslo"
    )
    @Column(name = "phone",nullable = false)
    private String phone;


    @NotBlank(message = "Pracovná pozícia nesmie byť prázdna")
    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @NotNull(message = "Plat nesmie byť null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Plat nemôže byť záporný")
    @Column(name = "salary", nullable = false)
    private Double salary;

    @NotNull(message = "Typ zamestnania je povinný")
    @Column(name = "full_time", nullable = false)
    private String fullTime;

    public Employee() { }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public String getFullTime() { return fullTime; }
    public void setFullTime(String fullTime) { this.fullTime = fullTime; }


}