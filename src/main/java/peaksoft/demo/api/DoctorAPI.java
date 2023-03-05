package peaksoft.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.demo.entity.Doctor;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.service.DepartmentService;
import peaksoft.demo.service.DoctorService;
import peaksoft.demo.service.HospitalService;

@Controller
@RequestMapping("/{hospitalId}/mainDoctor")
@RequiredArgsConstructor
public class DoctorAPI {
    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    @GetMapping
    String getAll(Model model, @PathVariable("hospitalId")Long hospitalId){
        model.addAttribute("doctors",doctorService.getAllDoctorById(hospitalId));
        model.addAttribute("hospital",hospitalService.getHospitalById(hospitalId));
        return "doctors/mainDoctor";
    }
    @GetMapping("/newDoctor")
    String create(@PathVariable Long hospitalId,Model model){
        model.addAttribute("doctor",new Doctor());
        model.addAttribute("hospital",hospitalService.getHospitalById(hospitalId));
        model.addAttribute("department",departmentService.getAllDepartmentById(hospitalId));
        model.addAttribute("doctors",doctorService.getAllDoctorById(hospitalId));
        return "doctors/newDoctor";
    }
    @PostMapping("/saveDoctor")
    String save(@PathVariable("hospitalId")Long hospitalId,
                @ModelAttribute("doctor")
                @Valid Doctor doctor,
                BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "doctors/newDoctor";
        }
        doctorService.saveDoctor(hospitalId,doctor);
        return "redirect:/{hospitalId}/mainDoctor";
    }
    @GetMapping("/{doctorId}/departments")
    public String getDepartments(@PathVariable Long doctorId,
                   @PathVariable Long hospitalId,
                   Model model){
        model.addAttribute("departments", doctorService.getDepartments(doctorId));
        return "doctors/departments";
    }
    @GetMapping("/{doctorId}/select")
    String select(@PathVariable("hospitalId")Long hospitalId,
                  @PathVariable("doctorId")Long doctorId,
                  Model model){
        model.addAttribute("departments",doctorService.getDepartmentToSelect(doctorId));
        model.addAttribute("doctor", new Doctor());
        return "doctors/select";
    }
    @PostMapping("/{doctorId}/assigning")
        String assigning(@PathVariable Long doctorId,
                         @PathVariable Long hospitalId,
                         @ModelAttribute("doctor")Doctor doctor){
        doctorService.assigningToById(doctorId,doctor.getDepartmentId());
        return "redirect:/{hospitalId}/mainDoctor";
    }
    @GetMapping("/{doctorId}/update")
    String find(@PathVariable Long doctorId,
                @PathVariable Long hospitalId,
                Model model){
       model.addAttribute("doctor",doctorService.getDoctorById(doctorId));
       return "doctors/updateDoctor";
    }
    @PostMapping("/{doctorId}")
    String update(@PathVariable Long doctorId,
                  @PathVariable Long hospitalId,
                  @ModelAttribute("doctor") Doctor doctor,
                  BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "doctors/updateDoctor";
        }
        doctorService.updateDoctors(doctorId,doctor);
        return "redirect:/{hospitalId}/mainDoctor";
    }
    @GetMapping("/{doctorId}/delete")
    String delete(@PathVariable Long doctorId,
                  @PathVariable Long hospitalId){
        doctorService.deleteDoctor(doctorId);
        return "redirect:/{hospitalId}/mainDoctor";
    }
    @GetMapping("/{doctorId}/appointments")
    public String getAppointments(@PathVariable Long doctorId,
                                 @PathVariable Long hospitalId,
                                 Model model){
        model.addAttribute("appointments",doctorService.getAppointments(doctorId));
        return "doctors/appointments";
    }
}
