package dbService;

import base.DBService;
import base.dataSets.UserDataSet;
import dbService.dao.UserDataSetDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import xpath.xpathAdapter;

import java.util.List;

/**
 * Created by Виталий on 02.05.2015.
 */
public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.temp.use_jdbc_metadata_defaults"));
        configuration.setProperty("hibernate.dialect", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.dialect" ));
        configuration.setProperty("hibernate.connection.driver_class", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.connection.driver_class" ));
        configuration.setProperty("hibernate.connection.url", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.connection.url" ));
        configuration.setProperty("hibernate.connection.username", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.connection.username" ));
        configuration.setProperty("hibernate.connection.password", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.connection.password" )); //test
        configuration.setProperty("hibernate.show_sql", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.show_sql" ));
        configuration.setProperty("hibernate.hbm2ddl.auto", xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.hbm2ddl.auto" )); //create
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults",xpathAdapter.getValue("resources/DB.xml", "/class/hibernate.temp.use_jdbc_metadata_defaults" ));

        sessionFactory = createSessionFactory(configuration);
    }

    public String getLocalStatus() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String status = transaction.getLocalStatus().toString();
        session.close();
        return status;
    }

    public void save(UserDataSet dataSet) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
            transaction.commit();
    }

    public void updateScore(UserDataSet dataSet) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        dao.updateScore(dataSet);
        transaction.commit();

    }

    public UserDataSet read(int id) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.read(id);
    }

    public UserDataSet readByEmail(String email) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.readByEmail(email);
    }

    public UserDataSet readByName(String name) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.readByName(name);
    }

    public int getUsersSize() {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.getUsersSize();
    }

    public List<UserDataSet> readAll() {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.readAll();
    }

    public List<UserDataSet> getScoreBoard() {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.getScoreBoard();
    }

    public void shutdown(){
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
