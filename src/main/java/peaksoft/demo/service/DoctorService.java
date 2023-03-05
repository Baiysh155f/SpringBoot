package peaksoft.demo.service;

import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Doctor;

import java.util.List;

public interface DoctorService {
    void assigningToById(Long doctorId, Long departmentId);
    void saveDoctor(Long hospitalId, Doctor doctor);
    Doctor getDoctorById(Long doctorId);
    List<Doctor> getAllDoctors();
    void updateDoctors(Long doctorId,Doctor newDoctor);
    void deleteDoctor(Long doctorId);
    List<Doctor> getAllDoctorById(Long id);

    List<Department> getDepartments(Long doctorId);
    List<Department> getDepartmentToSelect(Long doctorId);

    List<Appointment> getAppointments(Long doctorId);
}
