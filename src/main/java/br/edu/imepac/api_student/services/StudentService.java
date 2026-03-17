package br.edu.imepac.api_student.services;

import br.edu.imepac.api_student.entities.Student;
import br.edu.imepac.api_student.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import br.edu.imepac.api_student.exceptions.StudentNotFoundException;

import java.util.List;

@Service
public class StudentService {
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student updateStudent(Long id, Student studentUpdated) {
        Student student = getStudentById(id);
        student.setNome(studentUpdated.getNome());
        student.setEmail(studentUpdated.getEmail());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }


}
