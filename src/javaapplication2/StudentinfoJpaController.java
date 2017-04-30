/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.Serializable;
import java.util.List;
import javaapplication2.exceptions.NonexistentEntityException;
import javaapplication2.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author ghada
 */
public class StudentinfoJpaController implements Serializable {

    public StudentinfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Studentinfo studentinfo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(studentinfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStudentinfo(studentinfo.getId()) != null) {
                throw new PreexistingEntityException("Studentinfo " + studentinfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Studentinfo studentinfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            studentinfo = em.merge(studentinfo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Boolean id = studentinfo.getId();
                if (findStudentinfo(id) == null) {
                    throw new NonexistentEntityException("The studentinfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Boolean id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Studentinfo studentinfo;
            try {
                studentinfo = em.getReference(Studentinfo.class, id);
                studentinfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The studentinfo with id " + id + " no longer exists.", enfe);
            }
            em.remove(studentinfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Studentinfo> findStudentinfoEntities() {
        return findStudentinfoEntities(true, -1, -1);
    }

    public List<Studentinfo> findStudentinfoEntities(int maxResults, int firstResult) {
        return findStudentinfoEntities(false, maxResults, firstResult);
    }

    private List<Studentinfo> findStudentinfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Studentinfo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Studentinfo findStudentinfo(Boolean id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Studentinfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentinfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Studentinfo> rt = cq.from(Studentinfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
