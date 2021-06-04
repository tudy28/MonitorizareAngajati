package service;

import bugtracing.service.IService;
import bugtracing.service.Observer;
import model.Bug;
import model.Programator;
import model.Solutie;
import model.Verificator;
import persistance.repository.IRepositoryBug;
import persistance.repository.IRepositoryProgramator;
import persistance.repository.IRepositorySolutie;
import persistance.repository.IRepositoryVerificator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IService {
    private IRepositoryProgramator repositoryProgramator;
    private IRepositoryVerificator repositoryVerificator;
    private IRepositoryBug repositoryBug;
    private IRepositorySolutie repositorySolutie;
    private Map<Long,Observer> loggedClients;
    private final int defaultThreadsNumber = 5;


    public Service(IRepositoryProgramator repositoryProgramator, IRepositoryVerificator repositoryVerificator,
                   IRepositoryBug repositoryBug, IRepositorySolutie repositorySolutie)
    {
        this.repositoryProgramator = repositoryProgramator;
        this.repositoryVerificator = repositoryVerificator;
        this.repositoryBug = repositoryBug;
        this.repositorySolutie = repositorySolutie;
        this.loggedClients = new ConcurrentHashMap<>();
    }

    private void notifyModifiedBug(){
        ExecutorService executorService = Executors.newFixedThreadPool(this.defaultThreadsNumber);
        loggedClients.forEach((id,client)->{
            Observer c = loggedClients.get(id);
            executorService.execute(()->{
                try{
                    System.out.println("Notifying [" + id + "]");
                    c.notifyModifiedBug(this.findAllBuguriNerezolvate());
                }
                catch (Exception e){
                    System.out.println("Error notifying volunteer with ID: " + id + " Message: " + e.getMessage());
                }
            });
        });
    }

    private void notifyModifiedSolution(){
        ExecutorService executorService = Executors.newFixedThreadPool(this.defaultThreadsNumber);
        loggedClients.forEach((id,client)->{
            Observer c = loggedClients.get(id);
            executorService.execute(()->{
                try{
                    System.out.println("Notifying [" + id + "]");
                    c.notifyModifiedSolution(this.findAllSolutii());
                }
                catch (Exception e){
                    System.out.println("Error notifying volunteer with ID: " + id + " Message: " + e.getMessage());
                }
            });
        });
    }


    @Override
    public Programator loginProgramator(String username, String password, Observer client) throws Exception {
        Programator programator = repositoryProgramator.loginProgramator(username, password);
        if(programator != null){
            loggedClients.put(programator.getId(), client);
        }
        return programator;
    }

    @Override
    public Verificator loginVerificator(String username, String password, Observer client) throws Exception {
        Verificator verificator = repositoryVerificator.loginVerificator(username, password);
        if(verificator != null){
            loggedClients.put(verificator.getId(), client);
        }
        return verificator;
    }

    @Override
    public void logout(Long id) throws Exception {
        loggedClients.remove(id);
    }

    @Override
    public void adaugaBug(Bug bug) throws Exception {
        repositoryBug.add(bug);
        notifyModifiedBug();
    }

    @Override
    public void adaugaSolutie(Solutie solutie) throws Exception {
        repositorySolutie.add(solutie);
        notifyModifiedBug();
        notifyModifiedSolution();
    }

    @Override
    public void acceptaSolutie(Solutie solutie) throws Exception {
        repositorySolutie.delete(solutie.getId());
        repositoryBug.delete(solutie.getBugRezolvat());
        notifyModifiedSolution();
    }

    @Override
    public void refuzaSolutie(Solutie solutie) throws Exception {
        Bug solutionBug = repositoryBug.findById(solutie.getBugRezolvat());
        solutionBug.setStareBug("nerezolvat");
        repositoryBug.update(solutionBug,solutionBug.getId());
        repositorySolutie.delete(solutie.getId());
        notifyModifiedSolution();
        notifyModifiedBug();
    }


    @Override
    public Bug findBug(Long id) throws Exception {
        return repositoryBug.findById(id);
    }


    @Override
    public Iterable<Bug> findAllBuguriNerezolvate() throws Exception {
        return repositoryBug.findAllBuguriNerezolvate();
    }

    @Override
    public Iterable<Solutie> findAllSolutii() throws Exception {
        return repositorySolutie.findAll();
    }

    @Override
    public void updateBug(Bug bug, Long idBug) throws Exception {
        repositoryBug.update(bug,idBug);
    }
}
