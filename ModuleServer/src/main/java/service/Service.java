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
    }

    @Override
    public void adaugaSolutie(Solutie solutie) throws Exception {
        repositorySolutie.add(solutie);
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
