package persistance.repository;

import model.Programator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import persistance.repository.Utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RepositoryProgramatorDB implements IRepositoryProgramator{
    private JdbcUtils dbUtils;


    public RepositoryProgramatorDB(Properties props){
        dbUtils=new JdbcUtils(props);
    }



        @Override
    public Programator loginProgramator(String username, String password) {
        JdbcUtils.initialize();
        Programator programator=null;
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Programator where username=:username and password =:password");
            query.setParameter("username",username);
            query.setParameter("password",password.hashCode());
            session.getTransaction().commit();
            programator = (Programator) query.uniqueResult();
            JdbcUtils.close();
        }
        catch (Exception e){
            JdbcUtils.close();
        }
        return programator;
    }

    @Override
    public void add(Programator elem) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Programator elem, Long aLong) {

    }

    @Override
    public Programator findById(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Programator> findAll() {
        return null;
    }
}
