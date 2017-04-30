/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ghada
 */
@Entity
@Table(name = "STUDENTINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Studentinfo.findAll", query = "SELECT s FROM Studentinfo s")
    , @NamedQuery(name = "Studentinfo.findById", query = "SELECT s FROM Studentinfo s WHERE s.id = :id")})
public class Studentinfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "NAME")
    private byte[] name;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private Boolean id;
    @Lob
    @Column(name = "COURSE")
    private byte[] course;

    public Studentinfo() {
    }

    public Studentinfo(Boolean id) {
        this.id = id;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
    }

    public Boolean getId() {
        return id;
    }

    public void setId(Boolean id) {
        this.id = id;
    }

    public byte[] getCourse() {
        return course;
    }

    public void setCourse(byte[] course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studentinfo)) {
            return false;
        }
        Studentinfo other = (Studentinfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Studentinfo[ id=" + id + " ]";
    }
    
}
