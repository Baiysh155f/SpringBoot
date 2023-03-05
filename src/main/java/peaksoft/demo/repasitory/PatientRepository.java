package peaksoft.demo.repasitory;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Patient;

import java.util.List;

@Repository
@Transactional
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Query(value = "from Patient p join p.hospital h where h.id = :id")
    List<Patient> getAllPatientById(Long id);
    @Query("select  a from Patient p join p.appointments a where p.id = :patientId")
    List<Appointment> getAppointments(Long patientId);
}
