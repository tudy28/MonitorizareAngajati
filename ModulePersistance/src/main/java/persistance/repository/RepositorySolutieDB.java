package persistance.repository;

import model.Bug;
import model.Solutie;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistance.repository.Utils.JdbcUtils;

import java.util.List;
import java.util.Properties;

public class RepositorySolutieDB implements IRepositorySolutie{
    private JdbcUtils dbUtils;


    public RepositorySolutieDB(Properties props){
        dbUtils=new JdbcUtils(props);
    }


    @Override
    public void add(Solutie elem) {
        JdbcUtils.initialize();

        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(elem);
            session.getTransaction().commit();
            JdbcUtils.close();
        }
        catch (Exception e){
            JdbcUtils.close();
        }
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Solutie elem, Long aLong) {

    }

    @Override
    public Solutie findById(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Solutie> findAll() {
        JdbcUtils.initialize();
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Solutie> solutions = session.createQuery("from Solutie", Solutie.class).list();
                tx.commit();
                return solutions;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
