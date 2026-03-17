package br.edu.imepac.api_student.controllers;

import br.edu.imepac.api_student.entities.Student;
import br.edu.imepac.api_student.exceptions.StudentNotFoundException;
import br.edu.imepac.api_student.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StudentControllerTest {

    private MockMvc mockMvc;

    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new StudentController(studentService)).build();
    }

    @Test
    void shouldCreateStudentAndReturnSuccessMessage() throws Exception {
        Student student = new Student(1L, "Artur", "artur@email.com");
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Artur",
                                  "email": "artur@email.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Aluno cadastrado com sucesso."))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.nome").value("Artur"))
                .andExpect(jsonPath("$.data.email").value("artur@email.com"));
    }

    @Test
    void shouldListAllStudents() throws Exception {
        List<Student> students = List.of(
                new Student(1L, "Artur", "artur@email.com"),
                new Student(2L, "Maria", "maria@email.com")
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }

    @Test
    void shouldReturnStudentById() throws Exception {
        Student student = new Student(1L, "Artur", "artur@email.com");
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Artur"));
    }

    @Test
    void shouldUpdateStudentAndReturnSuccessMessage() throws Exception {
        Student updatedStudent = new Student(2L, "Artur Monteiro", "artur@email.com");
        when(studentService.updateStudent(eq(2L), any(Student.class)))
                .thenReturn(updatedStudent);

        mockMvc.perform(put("/students/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Artur Monteiro",
                                  "email": "artur@email.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Aluno atualizado com sucesso."))
                .andExpect(jsonPath("$.data.id").value(2))
                .andExpect(jsonPath("$.data.nome").value("Artur Monteiro"));
    }

    @Test
    void shouldDeleteStudentAndReturnSuccessMessage() throws Exception {
        Student student = new Student(1L, "Artur", "artur@email.com");
        when(studentService.getStudentById(1L)).thenReturn(student);
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Aluno Artur foi deletado com sucesso."))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void shouldReturnNotFoundWhenStudentDoesNotExist() throws Exception {
        when(studentService.getStudentById(99L)).thenThrow(new StudentNotFoundException(99L));

        mockMvc.perform(get("/students/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Aluno com id 99 Não foi encontrado."));
    }
}
