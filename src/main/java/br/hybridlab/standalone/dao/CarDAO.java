package br.hybridlab.standalone.dao;

import br.hybridlab.standalone.model.Car;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by alysson on 21/11/14.
 */
public class CarDAO extends GenericDAO<Car> {

    public Car getByModel(String model) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Car result = (Car) session.createCriteria(Car.class).add(Restrictions.eq("model",model)).uniqueResult();
        transaction.commit();
        return result;
    }
}
