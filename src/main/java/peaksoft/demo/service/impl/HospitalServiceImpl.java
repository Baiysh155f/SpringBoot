package peaksoft.demo.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.entity.Patient;
import peaksoft.demo.exception.NotFoundException;
import peaksoft.demo.repasitory.HospitalRepository;
import peaksoft.demo.service.HospitalService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Override
    public void saveHospital(Hospital hospital) {
        hospitalRepository.save(hospital);
    }

    @Override
    public Hospital getHospitalById(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new NotFoundException("Hospital id not found!!!"));
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalRepository.findAll();
    }

    @Override
    public void updateHospital(Long hospitalId, Hospital newHospital) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(
                () -> new NotFoundException("Hospital id not found!!!"));
        hospital.setName(newHospital.getName());
        hospital.setImages(newHospital.getImages());
        hospital.setAddress(newHospital.getAddress());
        hospitalRepository.save(hospital);
    }

    @Override
    public void deleteHospital(Long hospitalId) {
        hospitalRepository.deleteById(hospitalId);
    }

}
