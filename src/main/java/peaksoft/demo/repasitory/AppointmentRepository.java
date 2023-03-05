package peaksoft.demo.repasitory;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Doctor;
import peaksoft.demo.entity.Patient;

import java.util.List;

@Repository
@Transactional
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query(value = " select a from Hospital h join h.appointments a where h.id = :hospitalId")
    List<Appointment> getAppointmentsById(Long hospitalId);

}
