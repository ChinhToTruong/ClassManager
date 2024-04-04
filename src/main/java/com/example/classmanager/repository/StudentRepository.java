package com.example.classmanager.repository;

import com.example.classmanager.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query(value = "select st from Student st where st.fullName = ?1")
    List<Student> findStudentsByFullName(String name);

    @Query(value = "select st from Student st where st.id =?1")
    Optional<Student> findStudentById(Long id);
}
