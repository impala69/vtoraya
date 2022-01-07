package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        Session session;
        String s = "CREATE TABLE IF NOT EXISTS table_us" +
                "(ID BIGINT NOT NULL AUTO_INCREMENT," +
                " NAME VARCHAR(45) NOT NULL ," +
                " LAST_NAME VARCHAR(45) NOT NULL, " +
                " AGE TINYINT NOT NULL," +
                "PRIMARY KEY (ID))";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(s);
            query.executeUpdate();
            transaction.commit();
            System.out.println("таблица вошла в чат!");

        } catch (Exception e){
            if(transaction!=null){
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {Transaction transaction = null;
        Session session = null;
        String s = "DROP TABLE IF EXISTS table_us";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(s);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Дело сделано! Таблицы больше нет :( ");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            System.out.println("Юзер успешно создан и его имя " + name + " !!!");
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }


    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        Session session = null;
        String s = "DELETE User where ID = :id";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(s).setParameter("ID", id);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Юзер под id: " + id + "ремувнулся успешно!");

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        Transaction transaction=null;
        List<User> userList = new ArrayList<>();
        try{
            session = Util.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            userList = session.createQuery("FROM User ").list();
            transaction.commit();
            System.out.println("Юзеры получены");

        } catch (Exception e) {
            if(transaction!=null){
                transaction.rollback();
            }
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userList;

    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String s = "DELETE FROM table_us";
        try {
            session = Util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(s);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (transaction != null) {
                    transaction.rollback();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    }

