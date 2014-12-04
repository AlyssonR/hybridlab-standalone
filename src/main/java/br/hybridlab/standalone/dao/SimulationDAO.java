package br.hybridlab.standalone.dao;

import br.hybridlab.standalone.model.Simulation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Created by alysson on 03/12/14.
 */
public class SimulationDAO extends GenericDAO<Simulation> {

    public Simulation getById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Simulation result = (Simulation) session.createCriteria(Simulation.class).add(Restrictions.eq("id", id)).uniqueResult();
        transaction.commit();
        return result;
    }
}
