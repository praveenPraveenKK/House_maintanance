package com.project.DAO;

import org.hibernate.Session;

import com.project.model.Users;
import com.project.util.ProjectUtill;

import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;

public class UserDAO {
    public void saveUser(Users user) throws IllegalStateException, SystemException {
        org.hibernate.Transaction transaction = null;
        try (Session session =ProjectUtill.getSessionFactory().openSession()) {
            transaction =  session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Users getUserByUsername(String username) {
        try (Session session = ProjectUtill.getSessionFactory().openSession()) {
            return session.createQuery("from User where username = :username", Users.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}