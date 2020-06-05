package br.pitagoras.gestaoalunos.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "professor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Professor.findAll", query = "SELECT p FROM Professor p where p.loginProfessor <> :loginProfessor")
    , @NamedQuery(name = "Professor.findByIdProfessor", query = "SELECT p FROM Professor p WHERE p.idProfessor = :idProfessor")
    , @NamedQuery(name = "Professor.findByNomeProfessor", query = "SELECT p FROM Professor p WHERE p.nomeProfessor = :nomeProfessor")
    , @NamedQuery(name = "Professor.findByCpfProfessor", query = "SELECT p FROM Professor p WHERE p.cpfProfessor = :cpfProfessor")
    , @NamedQuery(name = "Professor.findByEnderecoProfessor", query = "SELECT p FROM Professor p WHERE p.enderecoProfessor = :enderecoProfessor")
    , @NamedQuery(name = "Professor.findByRAProfessor", query = "SELECT p FROM Professor p WHERE p.rAProfessor = :rAProfessor")
    , @NamedQuery(name = "Professor.findByLoginProfessor", query = "SELECT p FROM Professor p WHERE p.loginProfessor = :loginProfessor")
    , @NamedQuery(name = "Professor.findBySenhaProfessor", query = "SELECT p FROM Professor p WHERE p.senhaProfessor = :senhaProfessor")})

public class Professor implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdProfessor")
    private Collection<Curso> cursoCollection;

    @Column(name = "CpfProfessor")
    private String cpfProfessor;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProfessor")
    private Integer idProfessor;
    @Column(name = "NomeProfessor")
    private String nomeProfessor;
    @Column(name = "EnderecoProfessor")
    private String enderecoProfessor;
    @Column(name = "RAProfessor")
    private Integer rAProfessor;
    @Column(name = "LoginProfessor")
    private String loginProfessor;
    @Column(name = "SenhaProfessor")
    private String senhaProfessor;

    public Professor() {
    }

    public Professor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }


    public String getEnderecoProfessor() {
        return enderecoProfessor;
    }

    public void setEnderecoProfessor(String enderecoProfessor) {
        this.enderecoProfessor = enderecoProfessor;
    }

    public Integer getRAProfessor() {
        return rAProfessor;
    }

    public void setRAProfessor(Integer rAProfessor) {
        this.rAProfessor = rAProfessor;
    }

    public String getLoginProfessor() {
        return loginProfessor;
    }

    public void setLoginProfessor(String loginProfessor) {
        this.loginProfessor = loginProfessor;
    }

    public String getSenhaProfessor() {
        return senhaProfessor;
    }

    public void setSenhaProfessor(String senhaProfessor) {
        this.senhaProfessor = senhaProfessor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfessor != null ? idProfessor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        if ((this.idProfessor == null && other.idProfessor != null) || (this.idProfessor != null && !this.idProfessor.equals(other.idProfessor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNomeProfessor();
    }

    public String getCpfProfessor() {
        return cpfProfessor;
    }

    public void setCpfProfessor(String cpfProfessor) {
        this.cpfProfessor = cpfProfessor;
    }


    @XmlTransient
    public Collection<Curso> getCursoCollection() {
        return cursoCollection;
    }

    public void setCursoCollection(Collection<Curso> cursoCollection) {
        this.cursoCollection = cursoCollection;
    }

}
