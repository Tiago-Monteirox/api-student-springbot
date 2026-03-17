package br.edu.imepac.api_student.controllers;

import br.edu.imepac.api_student.dto.ApiResponse;
import br.edu.imepac.api_student.entities.Student;
import br.edu.imepac.api_student.exceptions.StudentNotFoundException;
import br.edu.imepac.api_student.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Student>> cadastrar(@RequestBody Student student) {
        Student studentSaved = studentService.createStudent(student);
        return ResponseEntity.ok(new ApiResponse<>("Aluno cadastrado com sucesso.", studentSaved));
    }

    @GetMapping
    public ResponseEntity<List<Student>> listarTodos() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> buscarPorId(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> atualizar(@PathVariable Long id, @RequestBody Student student) {
        Student studentUpdated = studentService.updateStudent(id, student);
        return ResponseEntity.ok(new ApiResponse<>("Aluno atualizado com sucesso.", studentUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletar(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        studentService.deleteStudent(id);
        return ResponseEntity.ok(new ApiResponse<>("Aluno " + student.getNome() + " foi deletado com sucesso.", null));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleStudentNotFound(StudentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(ex.getMessage(), null));
    }
}
