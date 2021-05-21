package persistance.repository;

import model.Programator;
import model.Verificator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import persistance.repository.Utils.JdbcUtils;

import java.util.Properties;

public class RepositoryVerificatorDB implements IRepositoryVerificator{
    private JdbcUtils dbUtils;

    public RepositoryVerificatorDB(Properties props){
        dbUtils=new JdbcUtils(props);
    }



    @Override
    public Verificator loginVerificator(String username, String password) {
        JdbcUtils.initialize();
        Verificator verificator=null;
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Verificator where username=:username and password =:password");
            query.setParameter("username",username);
            query.setParameter("password",password.hashCode());
            session.getTransaction().commit();
            verificator = (Verificator) query.uniqueResult();
            JdbcUtils.close();
        }
        catch (Exception e){
            JdbcUtils.close();
        }
        return verificator;
    }

    @Override
    public void add(Verificator elem) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Verificator elem, Long aLong) {

    }

    @Override
    public Verificator findById(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Verificator> findAll() {
        return null;
    }
}
