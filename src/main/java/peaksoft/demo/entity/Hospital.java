package peaksoft.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hospitals")
@Getter
@Setter
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "hospital_id_gen"
    )
    @SequenceGenerator(
            name = "hospital_id_gen",
            sequenceName = "hospital_seq",
            allocationSize = 1
    )
    private Long id;
    @NotEmpty(message = "Name should not be empty !")
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters !!!")
    private String name;
    @NotEmpty(message = "Address should not be empty !")
    @Size(min = 2,max = 100,message = "Address should be between 2 and 100 characters !!!")
    private String address;
    @NotEmpty(message = "Images should not be empty !!!")
    private String images;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Doctor> doctors = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Patient> patients = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Department> departments = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @Transient
    private Long hospitalId;

    public Hospital(String name, String address, String images) {
        this.name = name;
        this.address = address;
        this.images = images;
    }

}
