package com.sankalp.testing.repository;

import com.sankalp.testing.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // custom query with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Optional<Employee> findByInitials(String firstName, String lastName);

    //custom query with named params
    @Query("select e from Employee e where e.firstName =:firstName and e.lastName =:lastName")
    Optional<Employee> findByInitialsViaNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
