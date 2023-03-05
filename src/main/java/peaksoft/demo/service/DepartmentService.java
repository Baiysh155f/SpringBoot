package peaksoft.demo.service;

import peaksoft.demo.entity.Department;

import java.util.List;

public interface DepartmentService {
    void saveDepartment(Long id, Department department);
    Department getDepartmentById(Long departmentId);
    List<Department> getAllDepartments();
    void updateDepartment(Long departmentId,Department newDepartment);
    void deleteDepartment(Long departmentId);
    List<Department> getAllDepartmentById(Long id);
}
