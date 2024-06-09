
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entities.Authorities;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class UsersJpaController implements Serializable {

    public UsersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User users) throws PreexistingEntityException, Exception {
        if (users.getAuthoritiesList() == null) {
            users.setAuthoritiesList(new ArrayList<Authorities>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Authorities> attachedAuthoritiesList = new ArrayList<Authorities>();
            for (Authorities authoritiesListAuthoritiesToAttach : users.getAuthoritiesList()) {
                authoritiesListAuthoritiesToAttach = em.getReference(authoritiesListAuthoritiesToAttach.getClass(), authoritiesListAuthoritiesToAttach.getAuthoritiesPK());
                attachedAuthoritiesList.add(authoritiesListAuthoritiesToAttach);
            }
            users.setAuthoritiesList(attachedAuthoritiesList);
            em.persist(users);
            for (Authorities authoritiesListAuthorities : users.getAuthoritiesList()) {
                User oldUsersOfAuthoritiesListAuthorities = authoritiesListAuthorities.getUser1();
                authoritiesListAuthorities.setUser1(users);
                authoritiesListAuthorities = em.merge(authoritiesListAuthorities);
                if (oldUsersOfAuthoritiesListAuthorities != null) {
                    oldUsersOfAuthoritiesListAuthorities.getAuthoritiesList().remove(authoritiesListAuthorities);
                    oldUsersOfAuthoritiesListAuthorities = em.merge(oldUsersOfAuthoritiesListAuthorities);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsers(users.getUsername()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUsers = em.find(User.class, user.getUsername());
            List<Authorities> authoritiesListOld = persistentUsers.getAuthoritiesList();
            List<Authorities> authoritiesListNew = user.getAuthoritiesList();
            List<String> illegalOrphanMessages = null;
            for (Authorities authoritiesListOldAuthorities : authoritiesListOld) {
                if (!authoritiesListNew.contains(authoritiesListOldAuthorities)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Authorities " + authoritiesListOldAuthorities + " since its users field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Authorities> attachedAuthoritiesListNew = new ArrayList<Authorities>();
            for (Authorities authoritiesListNewAuthoritiesToAttach : authoritiesListNew) {
                authoritiesListNewAuthoritiesToAttach = em.getReference(authoritiesListNewAuthoritiesToAttach.getClass(), authoritiesListNewAuthoritiesToAttach.getAuthoritiesPK());
                attachedAuthoritiesListNew.add(authoritiesListNewAuthoritiesToAttach);
            }
            authoritiesListNew = attachedAuthoritiesListNew;
            user.setAuthoritiesList(authoritiesListNew);
            user = em.merge(user);
            for (Authorities authoritiesListNewAuthorities : authoritiesListNew) {
                if (!authoritiesListOld.contains(authoritiesListNewAuthorities)) {
                    User oldUsersOfAuthoritiesListNewAuthorities = authoritiesListNewAuthorities.getUser1();
                    authoritiesListNewAuthorities.setUser1(user);
                    authoritiesListNewAuthorities = em.merge(authoritiesListNewAuthorities);
                    if (oldUsersOfAuthoritiesListNewAuthorities != null && !oldUsersOfAuthoritiesListNewAuthorities.equals(user)) {
                        oldUsersOfAuthoritiesListNewAuthorities.getAuthoritiesList().remove(authoritiesListNewAuthorities);
                        oldUsersOfAuthoritiesListNewAuthorities = em.merge(oldUsersOfAuthoritiesListNewAuthorities);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getUsername();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Authorities> authoritiesListOrphanCheck = user.getAuthoritiesList();
            for (Authorities authoritiesListOrphanCheckAuthorities : authoritiesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + user + ") cannot be destroyed since the Authorities " + authoritiesListOrphanCheckAuthorities + " in its authoritiesList field has a non-nullable users field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<User> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<User> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
