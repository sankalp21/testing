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

    //custom native sql query with index params
    @Query(value = "select * from Employee e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    Optional<Employee> findByInitialViaNative(String firstName, String lastName);

    //custom native sql query with named params
    @Query(value = "select * from Employee e where e.first_name =:firstName and e.last_name =:lastName", nativeQuery = true)
    Optional<Employee> findByInitialsViaNativeNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
