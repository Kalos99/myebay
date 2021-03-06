package it.prova.myebay.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria implements Comparable<Categoria>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "codice")
	private String codice;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categorie")
	private Set<Annuncio> annunci = new HashSet<Annuncio>();

	public Categoria() {
	}

	public Categoria(Long id, String descrizione, String codice, Set<Annuncio> annunci) {
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
		this.annunci = annunci;
	}

	public Categoria(Long id, String descrizione, String codice) {
		this.id = id;
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public Categoria(String descrizione, String codice, Set<Annuncio> annunci) {
		this.descrizione = descrizione;
		this.codice = codice;
		this.annunci = annunci;
	}

	public Categoria(String descrizione, String codice) {
		this.descrizione = descrizione;
		this.codice = codice;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public Set<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(Set<Annuncio> annunci) {
		this.annunci = annunci;
	}

	@Override
	public int compareTo(Categoria o) {
		if(this.id < o.getId()) {
			return -1;
		}
		if(this.id > o.getId()) {
			return 1;
		}
		return 0;
	}
}