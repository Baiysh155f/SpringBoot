package peaksoft.demo.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Patient;

import java.util.List;

@Service
@Transactional
public interface PatientService {
    List<Patient> getAllPatients();
    List<Patient> getAllPatientsById(Long id);

    void savePatient(Long hospitalId,Patient newPatient);
    Patient getPatientById(Long patientId);

    void updatePatient(Long patientId, Patient patient);

    void deletePatient(Long patientId);

    List<Appointment> getAppointments(Long patientId);
}
