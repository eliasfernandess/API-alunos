package br.edu.imepac.biblioteca.services;

import br.edu.imepac.biblioteca.domain.dtos.AlunoDto;
import br.edu.imepac.biblioteca.domain.dtos.AlunoRequestDto;
import br.edu.imepac.biblioteca.domain.entidades.Aluno;
import br.edu.imepac.biblioteca.repositories.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoDto getAlunoById(Long id) throws Exception {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));
        return new AlunoDto(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getMatricula());
    }

    public AlunoDto updateAluno(Long id, AlunoRequestDto alunoUpdateDto) throws Exception {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));

        if (alunoUpdateDto.getNome().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome não pode ser vazio.");
        }

        if (alunoUpdateDto.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não pode ser vazio.");
        }

        if (alunoUpdateDto.getMatricula().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula não pode ser vazia.");
        }

        aluno.setNome(alunoUpdateDto.getNome());
        aluno.setEmail(alunoUpdateDto.getEmail());
        aluno.setMatricula(alunoUpdateDto.getMatricula());

        alunoRepository.save(aluno);

        return new AlunoDto(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getMatricula());
    }

    public void deleteAluno(Long id) throws Exception {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));
        alunoRepository.delete(aluno);
    }

    public AlunoDto saveAluno(AlunoRequestDto alunoRequestDto) {
        if(alunoRequestDto.getNome().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome não pode ser vazio.");
        }

        if(alunoRequestDto.getEmail().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email não pode ser vazio.");
        }

        if(alunoRequestDto.getMatricula().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Matrícula não pode ser vazia.");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(alunoRequestDto.getNome());
        aluno.setEmail(alunoRequestDto.getEmail());
        aluno.setMatricula(alunoRequestDto.getMatricula());

        alunoRepository.save(aluno);

        return new AlunoDto(aluno.getId(), aluno.getNome(), aluno.getEmail(), aluno.getMatricula());
    }
}
