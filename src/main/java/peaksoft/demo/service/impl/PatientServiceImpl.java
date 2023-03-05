package peaksoft.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.entity.Patient;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.repasitory.AppointmentRepository;
import peaksoft.demo.repasitory.HospitalRepository;
import peaksoft.demo.repasitory.PatientRepository;
import peaksoft.demo.service.PatientService;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> getAllPatientsById(Long id) {
        return patientRepository.getAllPatientById(id);
    }

    @Override
    public void savePatient(Long hospitalId, Patient newPatient) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital id not found !!!"));
        hospital.getPatients().add(newPatient);
        newPatient.setHospital(hospital);
        patientRepository.save(newPatient);
    }

    @Override
    public Patient getPatientById(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient id not found!!!"));
    }

    @Override
    public void updatePatient(Long patientId, Patient patient) {
        Patient patient1 = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient id not found !!!"));
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setEmail(patient.getEmail());
        patient1.setGender(patient.getGender());
        patient1.setPhoneNumber(patient.getPhoneNumber());
        patientRepository.save(patient1);
    }

    @Override
    public void deletePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(
                        ()-> new NotFoundException("Patient id not found!!"));
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();

        appointments.forEach(a-> a.getPatient().setAppointments(null));
        appointments.forEach(a-> a.getDoctor().setAppointments(null));

        appointments.forEach(a-> a.setPatient(null));
        appointments.forEach(a-> a.setDoctor(null));
        appointments.forEach(a-> a.setDepartment(null));
        hospital.getAppointments().removeAll(appointments);
        appointmentRepository.deleteAll(appointments);
        patientRepository.deleteById(patientId);
    }

    @Override
    public List<Appointment> getAppointments(Long patientId) {
        return patientRepository.getAppointments(patientId);
    }
}
