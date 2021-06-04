package persistance.repository;

import model.Bug;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import persistance.repository.Utils.JdbcUtils;

import java.util.List;
import java.util.Properties;

public class RepositoryBugDB implements IRepositoryBug{
    private JdbcUtils dbUtils;


    public RepositoryBugDB(Properties props){
        dbUtils=new JdbcUtils(props);
    }



    @Override
    public void add(Bug elem) {
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
        JdbcUtils.initialize();

        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Bug where id=:idBug", Bug.class);
            query.setParameter("idBug",aLong);
            Bug bug = (Bug) query.setMaxResults(1).uniqueResult();
            session.delete(bug);
            session.getTransaction().commit();
            JdbcUtils.close();
        }
        catch (Exception e){
            JdbcUtils.close();
        }
    }

    @Override
    public void update(Bug elem, Long aLong) {
        JdbcUtils.initialize();
        try(Session session = JdbcUtils.getSessionFactory().openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Bug bug = session.load( Bug.class, aLong );
                bug.setStareBug(elem.getStareBug());
                session.update(bug);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }

    }

    @Override
    public Bug findById(Long aLong) {
        JdbcUtils.initialize();
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
                return (Bug) session.get(Bug.class, aLong);
            } catch (RuntimeException ex) {
                JdbcUtils.close();
            }
        return null;
    }

    @Override
    public Iterable<Bug> findAll() {
        JdbcUtils.initialize();
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Bug> bugs = session.createQuery("from Bug", Bug.class).list();
                tx.commit();
                return bugs;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<Bug> findAllBuguriNerezolvate() {
        JdbcUtils.initialize();
        try(Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Query query = session.createQuery("from Bug where stareBug=:stare", Bug.class);
                query.setParameter("stare","nerezolvat");
                List<Bug> bugs=query.list();
                tx.commit();
                return bugs;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }
}
