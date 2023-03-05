package peaksoft.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.demo.entity.*;
import peaksoft.demo.exception.ExistsInDataBase;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.repasitory.*;
import peaksoft.demo.service.AppointmentService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public List<Appointment> getAllAppointments(Long hospitalId) {
        return appointmentRepository.getAppointmentsById(hospitalId);

    }

    @Override
    public void saveAppointment(Long hospitalId, Appointment newAppointment) {
        newAppointment.setDepartment(departmentRepository.findById(newAppointment.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department id not found!!!")));
        newAppointment.setDoctor(doctorRepository.findById(newAppointment.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor id not found!!!")));
        List<Department> departments = newAppointment.getDoctor().getDepartments();
        for (Department department : departments) {
            if (!department.getId().equals(newAppointment.getDepartmentId())){
                throw new ExistsInDataBase("This department isn't connected this Doctor!!!!");
            }
        }
        newAppointment.setPatient(patientRepository.findById(newAppointment.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient id not found !!!")));
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital id not found!!!"));
        hospital.getAppointments().add(newAppointment);
        appointmentRepository.save(newAppointment);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment id not found!!!"));
    }

    @Override
    public void updateAppointmentById(Long appointmentId, Appointment appointment) {
        Appointment appointment1 = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment id not found!!!"));
        appointment1.setDate(appointment.getDate());
        appointment1.setDepartment(departmentRepository.findById(appointment.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department id not found!!!")));
        List<Department> departments = appointment.getDoctor().getDepartments();
        for (Department department : departments) {
            if (!department.getId().equals(appointment.getDepartmentId())){
                throw new ExistsInDataBase("This department isn't connected this Doctor!!!!");
            }
        }
        appointment1.setDoctor(doctorRepository.findById(appointment.getDoctorId())
                .orElseThrow(() -> new NotFoundException("Doctor id not found!!!")));
        appointment1.setPatient(patientRepository.findById(appointment.getPatientId())
                .orElseThrow(() -> new NotFoundException("Patient id not found !!!")));
    }

    @Override
    public void deleteAppointmentById(Long appointmentId, Long hospitalId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NotFoundException("Appointment id not found!!!"));
        hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital id not found!!!"))
                .getAppointments().remove(appointment);
        appointment.getDoctor().getAppointments().clear();
        appointment.getPatient().getAppointments().clear();
        appointmentRepository.delete(appointment);
    }
}
