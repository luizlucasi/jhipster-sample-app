package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.domain.Livro;
import io.github.jhipster.sample.repository.LivroRepository;
import io.github.jhipster.sample.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.sample.domain.Livro}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LivroResource {

    private final Logger log = LoggerFactory.getLogger(LivroResource.class);

    private static final String ENTITY_NAME = "livro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LivroRepository livroRepository;

    public LivroResource(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    /**
     * {@code POST  /livros} : Create a new livro.
     *
     * @param livro the livro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new livro, or with status {@code 400 (Bad Request)} if the livro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/livros")
    public ResponseEntity<Livro> createLivro(@Valid @RequestBody Livro livro) throws URISyntaxException {
        log.debug("REST request to save Livro : {}", livro);
        if (livro.getId() != null) {
            throw new BadRequestAlertException("A new livro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Livro result = livroRepository.save(livro);
        return ResponseEntity.created(new URI("/api/livros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /livros} : Updates an existing livro.
     *
     * @param livro the livro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated livro,
     * or with status {@code 400 (Bad Request)} if the livro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the livro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/livros")
    public ResponseEntity<Livro> updateLivro(@Valid @RequestBody Livro livro) throws URISyntaxException {
        log.debug("REST request to update Livro : {}", livro);
        if (livro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Livro result = livroRepository.save(livro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, livro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /livros} : get all the livros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of livros in body.
     */
    @GetMapping("/livros")
    public ResponseEntity<List<Livro>> getAllLivros(Pageable pageable) {
        log.debug("REST request to get a page of Livros");
        Page<Livro> page = livroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /livros/:id} : get the "id" livro.
     *
     * @param id the id of the livro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the livro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/livros/{id}")
    public ResponseEntity<Livro> getLivro(@PathVariable Long id) {
        log.debug("REST request to get Livro : {}", id);
        Optional<Livro> livro = livroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(livro);
    }

    /**
     * {@code DELETE  /livros/:id} : delete the "id" livro.
     *
     * @param id the id of the livro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        log.debug("REST request to delete Livro : {}", id);
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
