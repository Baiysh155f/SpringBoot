package peaksoft.demo.service;

import org.springframework.stereotype.Service;
import peaksoft.demo.entity.Appointment;

import java.util.List;
@Service
public interface AppointmentService {
    List<Appointment> getAllAppointments(Long hospitalId);
    void saveAppointment(Long hospitalId,Appointment newAppointment);
    Appointment getAppointmentById(Long id);


    void updateAppointmentById(Long appointmentId, Appointment appointment);

    void deleteAppointmentById(Long appointmentId, Long hospitalId);
}
