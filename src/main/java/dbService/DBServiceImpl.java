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

import java.util.List;

/**
 * Created by Виталий on 02.05.2015.
 */
public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/filler");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
        configuration.setProperty("hibernate.temp.use_jdbc_metadata_defaults","false");

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

    public UserDataSet read(int id) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.read(id);
    }

    public UserDataSet readByName(String name) {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.readByName(name);
    }

    public List<UserDataSet> readAll() {
        Session session = sessionFactory.openSession();
        UserDataSetDAO dao = new UserDataSetDAO(session);
        return dao.readAll();
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
