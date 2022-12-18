package com.example.sb_rest.dao;

import com.example.sb_rest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO {
    private final EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Set<Role> getRoles() {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList());
    }

    @Override
    public Role getByName(String name) {
        return entityManager.createQuery("SELECT r FROM Role r WHERE r.name=:role", Role.class)
                .setParameter("role", name)
                .getSingleResult();
    }


    @Override
    public void addRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role findById(Long id) {
        return entityManager.find(Role.class, id);
    }
}
