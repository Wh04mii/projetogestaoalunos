/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.pitagoras.gestaoalunos.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lucas
 */
@Entity
@Table(name = "matricula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Matricula.findAll", query = "SELECT m FROM Matricula m")
    , @NamedQuery(name = "Matricula.findByIdMatricula", query = "SELECT m FROM Matricula m WHERE m.idMatricula = :idMatricula")
    , @NamedQuery(name = "Matricula.findByOpcaoMatricula", query = "SELECT m FROM Matricula m WHERE m.opcaoMatricula = :opcaoMatricula")
    , @NamedQuery(name = "Matricula.findByDataMatricula", query = "SELECT m FROM Matricula m WHERE m.dataMatricula = :dataMatricula")})
public class Matricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdMatricula")
    private Integer idMatricula;
    @Column(name = "OpcaoMatricula")
    private Short opcaoMatricula;
    @Column(name = "DataMatricula")
    @Temporal(TemporalType.DATE)
    private Date dataMatricula;
    @JoinColumn(name = "FkIdAluno", referencedColumnName = "IdAluno")
    @ManyToOne(optional = false)
    private Aluno fkIdAluno;
    @JoinColumn(name = "FkIdCurso", referencedColumnName = "IdCurso")
    @ManyToOne(optional = false)
    private Curso fkIdCurso;

    public Matricula() {
    }

    public Matricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Integer getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Integer idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Short getOpcaoMatricula() {
        return opcaoMatricula;
    }

    public void setOpcaoMatricula(Short opcaoMatricula) {
        this.opcaoMatricula = opcaoMatricula;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Aluno getFkIdAluno() {
        return fkIdAluno;
    }

    public void setFkIdAluno(Aluno fkIdAluno) {
        this.fkIdAluno = fkIdAluno;
    }

    public Curso getFkIdCurso() {
        return fkIdCurso;
    }

    public void setFkIdCurso(Curso fkIdCurso) {
        this.fkIdCurso = fkIdCurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMatricula != null ? idMatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Matricula)) {
            return false;
        }
        Matricula other = (Matricula) object;
        if ((this.idMatricula == null && other.idMatricula != null) || (this.idMatricula != null && !this.idMatricula.equals(other.idMatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.pitagoras.gestaoalunos.model.Matricula[ idMatricula=" + idMatricula + " ]";
    }
    
}
