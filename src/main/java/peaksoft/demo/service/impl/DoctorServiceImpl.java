package peaksoft.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Doctor;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.exception.ExistsInDataBase;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.repasitory.AppointmentRepository;
import peaksoft.demo.repasitory.DepartmentRepository;
import peaksoft.demo.repasitory.DoctorRepository;
import peaksoft.demo.repasitory.HospitalRepository;
import peaksoft.demo.service.DoctorService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public void assigningToById(Long doctorId, Long departmentId) {
        Department department = departmentRepository.findById(departmentId).get();
        Doctor doctor1 = doctorRepository.findById(doctorId).get();
        department.getDoctors().add(doctor1);
//        doctor1.getDepartments().add(department);
        doctorRepository.save(doctor1);
    }

    @Override
    public void saveDoctor(Long hospitalId, Doctor doctor) {
        Hospital hospital = hospitalRepository
                .findById(hospitalId).orElseThrow(
                        () -> new NotFoundException("Hospital id is not found!!!"));
        for (Doctor d : doctorRepository.getAllDoctorId(hospitalId)) {
            if (d.getEmail().equals(doctor.getEmail())) {
                throw new ExistsInDataBase("exists!!!");
            }
        }
        doctor.setHospital(hospital);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).get();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public void updateDoctors(Long doctorId, Doctor newDoctor) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor id not fount !!!"));
        List<Doctor> doctors = doctorRepository.getAllDoctorId(doctor.getHospital().getId());
        for (Doctor doctorFor : doctors) {
            if (!doctorFor.getId().equals(doctorId) && doctorFor.getEmail().equals(newDoctor.getEmail())){
                throw new ExistsInDataBase("exists");
            }
        }
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setImages(newDoctor.getImages());
        doctor.setEmail(newDoctor.getEmail());
        doctor.setPosition(newDoctor.getPosition());
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        () -> new NotFoundException("Doctor id not found"));
        Hospital hospital = doctor.getHospital();
        List<Department> departments = doctor.getDepartments();
        for (int i = 0; i < departments.size(); i++) {
            departments.get(i).getDoctors().remove(doctor);
        }
        List<Appointment> appointments = doctor.getAppointments();

        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        appointments.forEach(a -> a.getPatient().setAppointments(null));

        appointments.forEach(a -> a.setDoctor(null));
        appointments.forEach(a -> a.setDepartment(null));
        appointments.forEach(a -> a.setPatient(null));
        hospital.getAppointments().removeAll(appointments);
        appointmentRepository.deleteAll(appointments);
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public List<Doctor> getAllDoctorById(Long id) {
        return doctorRepository.getAllDoctorId(id);
    }

    @Override
    public List<Department> getDepartments(Long doctorId) {
        return doctorRepository.getDepartments(doctorId);
    }

    @Override
    public List<Department> getDepartmentToSelect(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor id not found !!!"));
        List<Department> departments = departmentRepository.getAllDepartmentId(doctor.getHospital().getId());
        if (!doctor.getDepartments().isEmpty()) {
            departments.removeAll(doctor.getDepartments());
        }
        return departments;
    }

    @Override
    public List<Appointment> getAppointments(Long doctorId) {
        return doctorRepository.getAppointments(doctorId);
    }
}
