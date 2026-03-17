package br.edu.imepac.api_student.exceptions;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long id) {
        super("Aluno com id " + id + " Não foi encontrado.");
    }
}
