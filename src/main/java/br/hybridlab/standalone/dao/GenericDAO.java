package br.hybridlab.standalone.dao;

import br.hybridlab.standalone.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by alysson on 21/11/14.
 */
public abstract class GenericDAO<T> {

    protected SessionFactory sessionFactory;

    private Class<T> type;

    public GenericDAO() {
        Type t = getClass().getGenericSuperclass();
        if(t instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) t;
            type = (Class) pt.getActualTypeArguments()[0];
        }
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void save(T object) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(object);
        transaction.commit();
    }

    public void update(T object) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(object);
        transaction.commit();
    }

    public void delete(Object id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Object obj = session.get(type, (Serializable) id);
        session.delete(obj);
        transaction.commit();
    }

    public T getBy(Object id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Object obj = session.get(type, (Serializable) id);
        transaction.commit();
        return (T) obj;
    }

    public List<T> get() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<T> objs = session.createCriteria(type).list();
        transaction.commit();
        return objs;
    }
}
