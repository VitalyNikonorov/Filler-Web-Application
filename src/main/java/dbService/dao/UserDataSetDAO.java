package dbService.dao;

import base.dataSets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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

    public void updateScore(UserDataSet dataSet) {
        /*session.saveOrUpdate(dataSet);*/
        String hqlUpdate = "update UserDataSet u set u.score = :newScore where u.email = :email";
        int updatedEntities = session.createQuery( hqlUpdate )
                .setInteger("newScore", dataSet.getScore())
                .setString( "email", dataSet.getEmail() )
                .executeUpdate();
        session.close();
    }

    public UserDataSet read(int id) {
        return (UserDataSet) session.load(UserDataSet.class, id);
    }

    public UserDataSet readByEmail(String email) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }

    public UserDataSet readByName(String name) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("name", name)).uniqueResult();
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
        //criteria.setProjection(Projections.property("name"));

        criteria.setProjection(Projections.projectionList().add(Projections.property("name"), "name").add(Projections.property("score"), "score"));

        return (List<UserDataSet>) criteria.list();
    }

    public int getUsersSize() {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        Object result = criteria.setProjection(Projections.rowCount()).uniqueResult();
        return new Integer(result.toString());
    }

}