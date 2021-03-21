package io.github.jhipster.sample.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Livro.
 */
@Entity
@Table(name = "livro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 5, max = 13)
    @Column(name = "isbn", length = 13, nullable = false, unique = true)
    private String isbn;

    @NotNull
    @Size(max = 100)
    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "assunto")
    private String assunto;

    @NotNull
    @Size(min = 4, max = 100)
    @Column(name = "editora", length = 100, nullable = false)
    private String editora;

    @Lob
    @Column(name = "capa")
    private byte[] capa;

    @Column(name = "capa_content_type")
    private String capaContentType;

    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    @Column(name = "tombo")
    private Integer tombo;

    @NotNull
    @Column(name = "copies", nullable = false)
    private Integer copies;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public Livro isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public Livro titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAssunto() {
        return assunto;
    }

    public Livro assunto(String assunto) {
        this.assunto = assunto;
        return this;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getEditora() {
        return editora;
    }

    public Livro editora(String editora) {
        this.editora = editora;
        return this;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public byte[] getCapa() {
        return capa;
    }

    public Livro capa(byte[] capa) {
        this.capa = capa;
        return this;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getCapaContentType() {
        return capaContentType;
    }

    public Livro capaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
        return this;
    }

    public void setCapaContentType(String capaContentType) {
        this.capaContentType = capaContentType;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public Livro numeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
        return this;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getTombo() {
        return tombo;
    }

    public Livro tombo(Integer tombo) {
        this.tombo = tombo;
        return this;
    }

    public void setTombo(Integer tombo) {
        this.tombo = tombo;
    }

    public Integer getCopies() {
        return copies;
    }

    public Livro copies(Integer copies) {
        this.copies = copies;
        return this;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Livro)) {
            return false;
        }
        return id != null && id.equals(((Livro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Livro{" +
            "id=" + getId() +
            ", isbn='" + getIsbn() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", assunto='" + getAssunto() + "'" +
            ", editora='" + getEditora() + "'" +
            ", capa='" + getCapa() + "'" +
            ", capaContentType='" + getCapaContentType() + "'" +
            ", numeroPaginas=" + getNumeroPaginas() +
            ", tombo=" + getTombo() +
            ", copies=" + getCopies() +
            "}";
    }
}
