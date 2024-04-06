package com.example.classmanager.repository;

import com.example.classmanager.entity.Student;
import com.example.classmanager.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAll();
    @Query(value = "select t from Teacher t where t.fullName = ?1")
    List<Teacher> findTeachersByFullName(String fullName);

    @Query(value = "select t from Teacher t where t.id =?1")
    Optional<Student> findStudentById(Long id);

    @Query(value = "SELECT * FROM teacher t right join user u  on u.id = t.user_id where u.username = ?1", nativeQuery = true)
    Optional<Teacher> findByUsername(String username);
}
