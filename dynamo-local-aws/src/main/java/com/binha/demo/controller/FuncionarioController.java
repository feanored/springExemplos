package com.binha.demo.controller;

import com.binha.demo.model.Funcionario;
import com.binha.demo.repository.FuncionarioDynamoRepo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/funcionario")
public class FuncionarioController {

    private FuncionarioDynamoRepo funcionarioRepository;

    @GetMapping
    public List<Funcionario> getFuncionarios() {
        return funcionarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Funcionario getFuncionarioById(@PathVariable("id") String funcionarioId) {
        return funcionarioRepository.getById(funcionarioId);
    }

    @PostMapping
    public Funcionario save(@RequestBody Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String funcionarioId) {
        return funcionarioRepository.delete(funcionarioId);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") String funcionarioId, @RequestBody Funcionario funcionario) {
        return funcionarioRepository.update(funcionarioId, funcionario);
    }
}
