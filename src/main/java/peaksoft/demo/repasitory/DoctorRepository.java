package peaksoft.demo.repasitory;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Doctor;

import java.util.List;


@Repository
@Transactional
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    @Query(value = "from Doctor d join d.hospital h where h.id = :id")
    List<Doctor> getAllDoctorId(Long id);
    @Query("select dep from Doctor doc join doc.departments dep where doc.id = :doctorId")
    List<Department> getDepartments(Long doctorId);
    @Query("select  a from Doctor p join p.appointments a where p.id = :patientId")
    List<Appointment> getAppointments(Long patientId);

    Doctor[] getAllByHospitalId(Long hospitalId);
}
