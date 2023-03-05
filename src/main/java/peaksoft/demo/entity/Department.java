package peaksoft.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "department_id_gen"
    )
    @SequenceGenerator(
            name = "department_id_gen",
            sequenceName = "department_seq",
            allocationSize = 1
    )
    private Long id;
    @NotEmpty(message = "Name should should be not empty !!!")
    @Size(min = 2,max = 30,message = "Name should be between 2 and 30 characters !!!")
    private String name;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;
    @ManyToMany(cascade = {REFRESH, DETACH, MERGE, PERSIST}, fetch = FetchType.EAGER)
    private List<Doctor> doctors;
    @Transient
    private Long doctorId;

    @Override
    public String toString() {
        return getName();
    }
}
