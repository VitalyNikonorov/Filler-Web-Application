package dbService.dao;

import base.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by Виталий on 02.05.2015.
 */
public class UserDataSetDAO {
    private Session session;

    public UserDataSetDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
        session.close();
    }

    public UserDataSet read(int id) {
        return (UserDataSet) session.load(UserDataSet.class, id);
    }

    public UserDataSet readByEmail(String email) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserDataSet> readAll() {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (List<UserDataSet>) criteria.list();
    }

    public List<UserDataSet> getScoreBoard() {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        criteria.addOrder(Order.desc("score"));
        criteria.setMaxResults(10);
        return (List<UserDataSet>) criteria.list();
    }

}