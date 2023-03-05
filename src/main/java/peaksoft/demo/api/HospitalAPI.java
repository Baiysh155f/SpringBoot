package peaksoft.demo.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.service.HospitalService;

@Controller
@RequestMapping("/api/mainHospital")
@RequiredArgsConstructor
public class HospitalAPI {
    private final HospitalService hospitalService;


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("hospitals", hospitalService.getAllHospital());
        return "hospitals/mainHospital";
    }

    @GetMapping("/newHospital")
    public String create(Model model) {
        model.addAttribute("hospital", new Hospital());
        return "hospitals/newHospital";
    }

    @PostMapping("/saveHospital")
    public String save(@ModelAttribute("hospital")
                       @Valid Hospital hospital,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospitals/newHospital";
        }
        hospitalService.saveHospital(hospital);
        return "redirect:/api/mainHospital";
    }

    @GetMapping("/{hospitalId}/update")
    public String find(@PathVariable("hospitalId") Long hospitalId, Model model) {
        model.addAttribute("hospital", hospitalService.getHospitalById(hospitalId));
        return "hospitals/updateHospital";
    }

    @PostMapping("/{hospitalId}")
    public String update(
            @PathVariable("hospitalId") Long hospitalId,
            @ModelAttribute("hospital") @Valid Hospital hospital,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospitals/updateHospital";
        }
        hospitalService.updateHospital(hospitalId, hospital);
        return "redirect:/api/mainHospital";
    }

    @GetMapping("/{hospitalId}/delete")
    String delete(@PathVariable("hospitalId") Long hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return "redirect:/api/mainHospital";
    }
}
