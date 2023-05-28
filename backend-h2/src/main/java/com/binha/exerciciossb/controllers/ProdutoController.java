package com.binha.exerciciossb.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.binha.exerciciossb.model.entities.Produto;
import com.binha.exerciciossb.model.repositories.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repoProduto;

	@PostMapping
	public @ResponseBody Produto salvarProduto(@Valid @RequestBody Produto produto) {
		return repoProduto.save(produto);
	}

	@PutMapping
	public ResponseEntity<Void> alterarProduto(@Valid @RequestBody Produto produto) {
		repoProduto.save(produto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public Iterable<Produto> obterProdutos() {
		return repoProduto.findAll();
	}

	@GetMapping(path = "/{id}")
	public Optional<Produto> obterProdutoPorId(@PathVariable int id) {
		return repoProduto.findById(id);
	}

	@GetMapping(path = "/nome/{nome}")
	public Iterable<Produto> obterProdutoPorId(@PathVariable String nome) {
//		return repoProduto.findByNomeContainingIgnoreCase(nome);
		return repoProduto.searchByNameLike(nome);
	}

	@DeleteMapping(path = "/{id}")
	public void excluirProduto(@PathVariable int id) {
		repoProduto.deleteById(id);
	}

	@DeleteMapping(path = "/idMaiorQue/{id}")
	public Iterable<Produto> excluirProdutosPorIdMaior(@PathVariable int id) {
		Iterable<Produto> prods = repoProduto.findByIdGreaterThanEqual(id);
		prods.forEach(p -> repoProduto.delete(p));
		return prods;
	}

	@GetMapping(path = "/idMaiorQue/{id}")
	public Iterable<Produto> obterProdutosPorIdMaior(@PathVariable int id) {
		return repoProduto.findByIdGreaterThanEqual(id);
	}

	@GetMapping(path = "/pag/{p}")
	public Iterable<Produto> obterProdutosByPag(@PathVariable int p) {
		Pageable page = PageRequest.of(p - 1, 3);
		return repoProduto.findAll(page);
	}

}
