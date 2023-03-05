package peaksoft.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.service.DepartmentService;
import peaksoft.demo.service.HospitalService;

@Controller
@RequestMapping("{hospitalId}/mainDepartment")
@RequiredArgsConstructor
public class DepartmentAPI {
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    @GetMapping
    String getAll(Model model, @PathVariable Long hospitalId) {
        model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
        model.addAttribute("hospital", hospitalService.getHospitalById(hospitalId));
        return "departments/mainDepartment";
    }

    @GetMapping("/newDepartment")
    String create(Model model, @PathVariable("hospitalId") Long hospitalId) {
        model.addAttribute("department", new Department());
        model.addAttribute("hospital", hospitalService.getHospitalById(hospitalId));
        model.addAttribute("departments", departmentService.getAllDepartmentById(hospitalId));
        return "departments/newDepartment";
    }

    @PostMapping("/saveDepartment")
    String save(@ModelAttribute("department")@Valid Department department,
                BindingResult bindingResult,
                @PathVariable("hospitalId") Long hospitalId) {
        if (bindingResult.hasErrors()){
            return "departments/newDepartment";
        }
        departmentService.saveDepartment(hospitalId, department);
        return "redirect:/{hospitalId}/mainDepartment";
    }

    @GetMapping("/{departmentId}/update")
    String find(Model model,@PathVariable Long hospitalId,
                @PathVariable("departmentId") Long departmentId) {
        model.addAttribute("department", departmentService.getDepartmentById(departmentId));
        return "departments/updateDepartment";
    }

    @PostMapping("/{departmentId}")
    String update(
            @PathVariable("hospitalId") Long hospitalId,
            @PathVariable("departmentId") Long departmentId,
            @ModelAttribute("department") Department department,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "departments/updateDepartment";
        }
        departmentService.updateDepartment(departmentId, department);
        return "redirect:/{hospitalId}/mainDepartment";
    }
    @GetMapping("/{departmentId}/delete")
    String delete(@PathVariable Long hospitalId,@PathVariable Long departmentId){
        departmentService.deleteDepartment(departmentId);
        return "redirect:/{hospitalId}/mainDepartment";
    }
}
