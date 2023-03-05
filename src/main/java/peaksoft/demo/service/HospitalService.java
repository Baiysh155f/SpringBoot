package peaksoft.demo.service;


import peaksoft.demo.entity.Hospital;
import peaksoft.demo.entity.Patient;

import java.util.List;

public interface HospitalService {
    void saveHospital(Hospital hospital);
    Hospital getHospitalById(Long hospitalId);
    List<Hospital> getAllHospital();
    void updateHospital(Long hospitalId,Hospital newHospital);
    void deleteHospital(Long hospitalId);
}
