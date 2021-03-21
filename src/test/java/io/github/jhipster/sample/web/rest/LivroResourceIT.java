package io.github.jhipster.sample.web.rest;

import io.github.jhipster.sample.JhipsterSampleApplicationApp;
import io.github.jhipster.sample.domain.Livro;
import io.github.jhipster.sample.repository.LivroRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LivroResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LivroResourceIT {

    private static final String DEFAULT_ISBN = "AAAAAAAAAA";
    private static final String UPDATED_ISBN = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASSUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_EDITORA = "AAAAAAAAAA";
    private static final String UPDATED_EDITORA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CAPA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CAPA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CAPA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CAPA_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_NUMERO_PAGINAS = 1;
    private static final Integer UPDATED_NUMERO_PAGINAS = 2;

    private static final Integer DEFAULT_TOMBO = 1;
    private static final Integer UPDATED_TOMBO = 2;

    private static final Integer DEFAULT_COPIES = 1;
    private static final Integer UPDATED_COPIES = 2;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLivroMockMvc;

    private Livro livro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livro createEntity(EntityManager em) {
        Livro livro = new Livro()
            .isbn(DEFAULT_ISBN)
            .titulo(DEFAULT_TITULO)
            .assunto(DEFAULT_ASSUNTO)
            .editora(DEFAULT_EDITORA)
            .capa(DEFAULT_CAPA)
            .capaContentType(DEFAULT_CAPA_CONTENT_TYPE)
            .numeroPaginas(DEFAULT_NUMERO_PAGINAS)
            .tombo(DEFAULT_TOMBO)
            .copies(DEFAULT_COPIES);
        return livro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Livro createUpdatedEntity(EntityManager em) {
        Livro livro = new Livro()
            .isbn(UPDATED_ISBN)
            .titulo(UPDATED_TITULO)
            .assunto(UPDATED_ASSUNTO)
            .editora(UPDATED_EDITORA)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .numeroPaginas(UPDATED_NUMERO_PAGINAS)
            .tombo(UPDATED_TOMBO)
            .copies(UPDATED_COPIES);
        return livro;
    }

    @BeforeEach
    public void initTest() {
        livro = createEntity(em);
    }

    @Test
    @Transactional
    public void createLivro() throws Exception {
        int databaseSizeBeforeCreate = livroRepository.findAll().size();
        // Create the Livro
        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isCreated());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate + 1);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getIsbn()).isEqualTo(DEFAULT_ISBN);
        assertThat(testLivro.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testLivro.getAssunto()).isEqualTo(DEFAULT_ASSUNTO);
        assertThat(testLivro.getEditora()).isEqualTo(DEFAULT_EDITORA);
        assertThat(testLivro.getCapa()).isEqualTo(DEFAULT_CAPA);
        assertThat(testLivro.getCapaContentType()).isEqualTo(DEFAULT_CAPA_CONTENT_TYPE);
        assertThat(testLivro.getNumeroPaginas()).isEqualTo(DEFAULT_NUMERO_PAGINAS);
        assertThat(testLivro.getTombo()).isEqualTo(DEFAULT_TOMBO);
        assertThat(testLivro.getCopies()).isEqualTo(DEFAULT_COPIES);
    }

    @Test
    @Transactional
    public void createLivroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = livroRepository.findAll().size();

        // Create the Livro with an existing ID
        livro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIsbnIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setIsbn(null);

        // Create the Livro, which fails.


        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setTitulo(null);

        // Create the Livro, which fails.


        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setEditora(null);

        // Create the Livro, which fails.


        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCopiesIsRequired() throws Exception {
        int databaseSizeBeforeTest = livroRepository.findAll().size();
        // set the field null
        livro.setCopies(null);

        // Create the Livro, which fails.


        restLivroMockMvc.perform(post("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLivros() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get all the livroList
        restLivroMockMvc.perform(get("/api/livros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(livro.getId().intValue())))
            .andExpect(jsonPath("$.[*].isbn").value(hasItem(DEFAULT_ISBN)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].assunto").value(hasItem(DEFAULT_ASSUNTO)))
            .andExpect(jsonPath("$.[*].editora").value(hasItem(DEFAULT_EDITORA)))
            .andExpect(jsonPath("$.[*].capaContentType").value(hasItem(DEFAULT_CAPA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].capa").value(hasItem(Base64Utils.encodeToString(DEFAULT_CAPA))))
            .andExpect(jsonPath("$.[*].numeroPaginas").value(hasItem(DEFAULT_NUMERO_PAGINAS)))
            .andExpect(jsonPath("$.[*].tombo").value(hasItem(DEFAULT_TOMBO)))
            .andExpect(jsonPath("$.[*].copies").value(hasItem(DEFAULT_COPIES)));
    }
    
    @Test
    @Transactional
    public void getLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        // Get the livro
        restLivroMockMvc.perform(get("/api/livros/{id}", livro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(livro.getId().intValue()))
            .andExpect(jsonPath("$.isbn").value(DEFAULT_ISBN))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.assunto").value(DEFAULT_ASSUNTO))
            .andExpect(jsonPath("$.editora").value(DEFAULT_EDITORA))
            .andExpect(jsonPath("$.capaContentType").value(DEFAULT_CAPA_CONTENT_TYPE))
            .andExpect(jsonPath("$.capa").value(Base64Utils.encodeToString(DEFAULT_CAPA)))
            .andExpect(jsonPath("$.numeroPaginas").value(DEFAULT_NUMERO_PAGINAS))
            .andExpect(jsonPath("$.tombo").value(DEFAULT_TOMBO))
            .andExpect(jsonPath("$.copies").value(DEFAULT_COPIES));
    }
    @Test
    @Transactional
    public void getNonExistingLivro() throws Exception {
        // Get the livro
        restLivroMockMvc.perform(get("/api/livros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // Update the livro
        Livro updatedLivro = livroRepository.findById(livro.getId()).get();
        // Disconnect from session so that the updates on updatedLivro are not directly saved in db
        em.detach(updatedLivro);
        updatedLivro
            .isbn(UPDATED_ISBN)
            .titulo(UPDATED_TITULO)
            .assunto(UPDATED_ASSUNTO)
            .editora(UPDATED_EDITORA)
            .capa(UPDATED_CAPA)
            .capaContentType(UPDATED_CAPA_CONTENT_TYPE)
            .numeroPaginas(UPDATED_NUMERO_PAGINAS)
            .tombo(UPDATED_TOMBO)
            .copies(UPDATED_COPIES);

        restLivroMockMvc.perform(put("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLivro)))
            .andExpect(status().isOk());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
        Livro testLivro = livroList.get(livroList.size() - 1);
        assertThat(testLivro.getIsbn()).isEqualTo(UPDATED_ISBN);
        assertThat(testLivro.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testLivro.getAssunto()).isEqualTo(UPDATED_ASSUNTO);
        assertThat(testLivro.getEditora()).isEqualTo(UPDATED_EDITORA);
        assertThat(testLivro.getCapa()).isEqualTo(UPDATED_CAPA);
        assertThat(testLivro.getCapaContentType()).isEqualTo(UPDATED_CAPA_CONTENT_TYPE);
        assertThat(testLivro.getNumeroPaginas()).isEqualTo(UPDATED_NUMERO_PAGINAS);
        assertThat(testLivro.getTombo()).isEqualTo(UPDATED_TOMBO);
        assertThat(testLivro.getCopies()).isEqualTo(UPDATED_COPIES);
    }

    @Test
    @Transactional
    public void updateNonExistingLivro() throws Exception {
        int databaseSizeBeforeUpdate = livroRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLivroMockMvc.perform(put("/api/livros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(livro)))
            .andExpect(status().isBadRequest());

        // Validate the Livro in the database
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLivro() throws Exception {
        // Initialize the database
        livroRepository.saveAndFlush(livro);

        int databaseSizeBeforeDelete = livroRepository.findAll().size();

        // Delete the livro
        restLivroMockMvc.perform(delete("/api/livros/{id}", livro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Livro> livroList = livroRepository.findAll();
        assertThat(livroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
