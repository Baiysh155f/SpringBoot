package peaksoft.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Doctor;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.repasitory.AppointmentRepository;
import peaksoft.demo.repasitory.DepartmentRepository;
import peaksoft.demo.repasitory.HospitalRepository;
import peaksoft.demo.service.DepartmentService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void saveDepartment(Long id, Department department) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Hospital id not found !!!"));
        hospital.getDepartments().add(department);
        department.setHospital(hospital);
        departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found!!!"));
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public void updateDepartment(Long departmentId, Department newDepartment) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department id not found!!!"));
        department.setName(newDepartment.getName());
        departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        Department department = departmentRepository
                .findById(departmentId).orElseThrow(
                        () -> new NotFoundException("Department id not found!!!"));

        List<Doctor> doctors = department.getDoctors();

        for (int i = 0; i < doctors.size(); i++) {
            doctors.get(i).getDepartments().remove(department);
        }

        Hospital hospital = department.getHospital();
        List<Appointment> appointments = appointmentRepository.getAppointmentsById(hospital.getId());
        List<Appointment> appointmentList = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getDepartment().getId().equals(departmentId)) {
                appointmentList.add(appointment);
            }
        }
        for (int i = 0; i < appointmentList.size(); i++) {
            appointmentList.get(i).getDoctor().getAppointments().clear();
            appointmentList.get(i).getPatient().getAppointments().clear();
        }
        hospital.getAppointments().removeAll(appointmentList);

        appointmentRepository.deleteAll(appointmentList);
        departmentRepository.deleteById(departmentId);
    }

    @Override
    public List<Department> getAllDepartmentById(Long id) {
        return departmentRepository.getAllDepartmentId(id);
    }
}
