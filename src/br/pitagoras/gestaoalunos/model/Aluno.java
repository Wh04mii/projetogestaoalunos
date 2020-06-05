/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pitagoras.gestaoalunos.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lucas
 */
@Entity
@Table(name = "aluno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a")
    , @NamedQuery(name = "Aluno.findByIdAluno", query = "SELECT a FROM Aluno a WHERE a.idAluno = :idAluno")
    , @NamedQuery(name = "Aluno.findByNomeAluno", query = "SELECT a FROM Aluno a WHERE a.nomeAluno = :nomeAluno")
    , @NamedQuery(name = "Aluno.findByCpfAluno", query = "SELECT a FROM Aluno a WHERE a.cpfAluno = :cpfAluno")
    , @NamedQuery(name = "Aluno.findByEnderecoAluno", query = "SELECT a FROM Aluno a WHERE a.enderecoAluno = :enderecoAluno")
    , @NamedQuery(name = "Aluno.findByTelefoneAluno", query = "SELECT a FROM Aluno a WHERE a.telefoneAluno = :telefoneAluno")
    , @NamedQuery(name = "Aluno.findByEmailAluno", query = "SELECT a FROM Aluno a WHERE a.emailAluno = :emailAluno")
    , @NamedQuery(name = "Aluno.findByDataNascAluno", query = "SELECT a FROM Aluno a WHERE a.dataNascAluno = :dataNascAluno")})
public class Aluno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdAluno")
    private Integer idAluno;
    @Column(name = "NomeAluno")
    private String nomeAluno;
    @Column(name = "CpfAluno")
    private String cpfAluno;
    @Column(name = "EnderecoAluno")
    private String enderecoAluno;
    @Column(name = "TelefoneAluno")
    private String telefoneAluno;
    @Column(name = "EmailAluno")
    private String emailAluno;
    @Column(name = "DataNascAluno")
    @Temporal(TemporalType.DATE)
    private Date dataNascAluno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdAluno")
    private Collection<Matricula> matriculaCollection;

    public Aluno() {
    }

    public Aluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getCpfAluno() {
        return cpfAluno;
    }

    public void setCpfAluno(String cpfAluno) {
        this.cpfAluno = cpfAluno;
    }

    public String getEnderecoAluno() {
        return enderecoAluno;
    }

    public void setEnderecoAluno(String enderecoAluno) {
        this.enderecoAluno = enderecoAluno;
    }

    public String getTelefoneAluno() {
        return telefoneAluno;
    }

    public void setTelefoneAluno(String telefoneAluno) {
        this.telefoneAluno = telefoneAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public Date getDataNascAluno() {
        return dataNascAluno;
    }

    public void setDataNascAluno(Date dataNascAluno) {
        this.dataNascAluno = dataNascAluno;
    }

    @XmlTransient
    public Collection<Matricula> getMatriculaCollection() {
        return matriculaCollection;
    }

    public void setMatriculaCollection(Collection<Matricula> matriculaCollection) {
        this.matriculaCollection = matriculaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAluno != null ? idAluno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aluno)) {
            return false;
        }
        Aluno other = (Aluno) object;
        if ((this.idAluno == null && other.idAluno != null) || (this.idAluno != null && !this.idAluno.equals(other.idAluno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.pitagoras.gestaoalunos.model.Aluno[ idAluno=" + idAluno + " ]";
    }
    
}
