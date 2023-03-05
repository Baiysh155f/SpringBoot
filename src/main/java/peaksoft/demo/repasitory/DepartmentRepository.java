package peaksoft.demo.repasitory;

import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.demo.entity.Department;
import peaksoft.demo.entity.Hospital;

import java.util.List;

@Repository
@Transactional
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    @Query(value = "from Department d join d.hospital h where h.id = :id")
    List<Department> getAllDepartmentId(Long id);
}
