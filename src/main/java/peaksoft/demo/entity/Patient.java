package peaksoft.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.demo.enums.Gender;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "patients")
@NoArgsConstructor
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patients_id_gen")
    @SequenceGenerator(name = "patients_id_gen", sequenceName = "patients_id_seq", allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Last Name should not be empty")
    @Size(min = 2,max = 30, message = (" Last Name should be between 2 and 30 characters !!!"))
    @Column(name = "first_name")
    private String firstName;
    @NotEmpty(message = "First Name should not be empty")
    @Size(min = 2,max = 30, message = ("First Name should be between 2 and 30 characters !!!"))
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Phone number should not be empty!")
    @Pattern(regexp = "\\+996\\d{9}", message = "Phone number should start with +996 and consist of 13 characters!")
    @Column(name = "phone_number")
    private String phoneNumber;
    @NotNull(message = "Choice gender!")
    private Gender gender;
    @NotEmpty(message = "Email should not be empty!!!")
    @Email(message = "Email should be valid!!!")
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;
    @OneToMany(mappedBy = "patient", cascade = ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments;
    @Transient
    private Long patientId;

    @Override
    public String toString() {
        return firstName + "\n" + lastName;
    }
}
