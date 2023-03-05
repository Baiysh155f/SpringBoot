package peaksoft.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.demo.entity.Appointment;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.service.*;

@Controller
@RequestMapping("/{hospitalId}/mainAppointment")
@RequiredArgsConstructor
public class AppointmentAPI {
    private final AppointmentService appointmentService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    @GetMapping
    String getAll(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments(hospitalId));
        return "appointments/mainAppointment";
    }

    @GetMapping("/select")
    String select(@PathVariable("hospitalId") Long hospitalId, Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
        model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
        model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
        return "appointments/selectApp";
    }

    @PostMapping("saveAppointment")
    String save(@PathVariable Long hospitalId,
                @ModelAttribute("appointment")
                @Valid Appointment appointment,
                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
            model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
            model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
        }
        try {
            model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
            model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
            model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
            appointmentService.saveAppointment(hospitalId, appointment);
            return "redirect:/{hospitalId}/mainAppointment";
        } catch (NotFoundException e) {
            model.addAttribute("saveError", "Doctor doesn't assign this department!!!");
            return "appointments/selectApp";
        }
    }

    @GetMapping("/{appointmentId}/update")
    String find(@PathVariable Long appointmentId,
                @PathVariable Long hospitalId,
                Model model) {
        try {
            model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentId));
            model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
            model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
            model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return "appointments/updateAppointment";
    }

    @PostMapping("/{appointmentId}")
    String update(@PathVariable Long appointmentId,
                  @PathVariable Long hospitalId,
                  @ModelAttribute("appointment")
                  @Valid Appointment appointment,
                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
            model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
            model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
            return "appointments/updateAppointment";
        }
        try {
            model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
            model.addAttribute("doctors", doctorService.getAllDoctorById(hospitalId));
            model.addAttribute("patients", patientService.getAllPatientsById(hospitalId));
            appointmentService.updateAppointmentById(appointmentId, appointment);
            return "redirect:/{hospitalId}/mainAppointment";
        } catch (NotFoundException e) {
            model.addAttribute("saveError", "Doctor doesn't assign this department!!!");
            return "appointments/updateAppointment";
        }

    }

    @GetMapping("/{appointmentId}/delete")
    String delete(@PathVariable Long appointmentId,
                  @PathVariable Long hospitalId) {
        appointmentService.deleteAppointmentById(appointmentId, hospitalId);
        return "redirect:/{hospitalId}/mainAppointment";
    }
}
