package br.edu.imepac.api_student.repositories;

import br.edu.imepac.api_student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository <Student, Long> {

}

