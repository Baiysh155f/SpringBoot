package peaksoft.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen", sequenceName = "doctor_id_seq", allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2,max = 30, message = (" Last Name should be between 2 and 30 characters !!!"))
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2,max = 30, message = ("First Name should be between 2 and 30 characters !!!"))
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Position should not be empty")
    @Size(min = 2,max = 30, message = ("Position should be between 2 and 30 characters !!!"))
    private String position;
    @NotEmpty(message = "Images should not be empty!!!")
    private String images;
    @NotEmpty(message = "Email should not be empty!!!")
    @Email(message = "Email should be valid!!!")
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;
    @ManyToMany(mappedBy = "doctors", cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private List<Department> departments;
    @OneToMany(mappedBy = "doctor", cascade = ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments;
    @Transient
    private Long departmentId;

    @Override
    public String toString() {
        return firstName + "\n" + lastName;
    }
}
