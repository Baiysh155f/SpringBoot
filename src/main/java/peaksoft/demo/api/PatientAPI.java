package peaksoft.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.entity.Patient;
import peaksoft.demo.service.HospitalService;
import peaksoft.demo.service.PatientService;

@Controller
@RequestMapping("/{hospitalId}/mainPatient")
@RequiredArgsConstructor
public class PatientAPI {
    private final PatientService patientService;
    private final HospitalService hospitalService;
    @GetMapping
    String getAll(@PathVariable("hospitalId") Long hospitalId, Model model){
        model.addAttribute("patients",patientService.getAllPatientsById(hospitalId));
        return "patients/mainPatient";
    }
    @GetMapping("/newPatient")
    String create(@PathVariable Long hospitalId,Model model){
        model.addAttribute("patient",new Patient());
        return "patients/newPatient";
    }
    @PostMapping("/savePatient")
    String save(@PathVariable Long hospitalId, @ModelAttribute("patient")
    @Valid Patient patient,
                BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "patients/newPatient";
        }
        patientService.savePatient(hospitalId,patient);
        return "redirect:/{hospitalId}/mainPatient";
    }
    @GetMapping("/{patientId}/update")
    String find(@PathVariable Long patientId,
                @PathVariable Long hospitalId,
                Model model){
        model.addAttribute("patient",patientService.getPatientById(patientId));
        return "patients/updatePatient";
    }
    @PostMapping("/{patientId}")
    String update(@PathVariable Long patientId,
                  @PathVariable Long hospitalId,
                  @ModelAttribute("patient") Patient patient,
                  BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "patients/updatePatient";
        }
        patientService.updatePatient(patientId,patient);
        return "redirect:/{hospitalId}/mainPatient";
    }
    @GetMapping("/{patientId}/delete")
    String delete(@PathVariable Long patientId,
                  @PathVariable Long hospitalId){
        patientService.deletePatient(patientId);
        return "redirect:/{hospitalId}/mainPatient";
    }
    @GetMapping("/{patientId}/appointments")
    public String getDepartments(@PathVariable Long patientId,
                                 @PathVariable Long hospitalId,
                                 Model model){
        model.addAttribute("appointments",patientService.getAppointments(patientId));
        return "patients/appointments";
    }
}
