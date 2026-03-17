package br.edu.imepac.api_student.services;

import br.edu.imepac.api_student.entities.Student;
import br.edu.imepac.api_student.exceptions.StudentNotFoundException;
import br.edu.imepac.api_student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Artur", "artur@email.com");
    }

    @Test
    void shouldCreateStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student created = studentService.createStudent(student);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals("Artur", created.getNome());
        verify(studentRepository).save(student);
    }

    @Test
    void shouldListAllStudents() {
        List<Student> students = List.of(
                student,
                new Student(2L, "Maria", "maria@email.com")
        );
        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertEquals(2, result.size());
        verify(studentRepository).findAll();
    }

    @Test
    void shouldFindStudentById() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student found = studentService.getStudentById(1L);

        assertEquals("Artur", found.getNome());
        verify(studentRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenStudentByIdDoesNotExist() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        StudentNotFoundException exception = assertThrows(
                StudentNotFoundException.class,
                () -> studentService.getStudentById(99L)
        );

        assertEquals("Aluno com id 99 Não foi encontrado.", exception.getMessage());
    }

    @Test
    void shouldUpdateStudent() {
        Student updateData = new Student(null, "Artur Monteiro", "artur.monteiro@email.com");
        Student updatedStudent = new Student(1L, "Artur Monteiro", "artur.monteiro@email.com");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(updatedStudent);

        Student result = studentService.updateStudent(1L, updateData);

        assertEquals("Artur Monteiro", result.getNome());
        assertEquals("artur.monteiro@email.com", result.getEmail());
        verify(studentRepository).findById(1L);
        verify(studentRepository).save(student);
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingStudent() {
        Student updateData = new Student(null, "Novo Nome", "novo@email.com");
        when(studentRepository.findById(50L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(50L, updateData));
    }

    @Test
    void shouldDeleteStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository).findById(1L);
        verify(studentRepository).delete(student);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingStudent() {
        when(studentRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(10L));
        verify(studentRepository, times(0)).delete(student);
    }
}
