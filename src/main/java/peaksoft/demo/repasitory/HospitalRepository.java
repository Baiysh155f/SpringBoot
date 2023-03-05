package peaksoft.demo.repasitory;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Hospital;
import peaksoft.demo.entity.Patient;

import java.util.List;

@Repository
@Transactional
public interface HospitalRepository extends JpaRepository<Hospital,Long> {

}
