package bugtracing.service;

import model.Bug;
import model.Programator;
import model.Solutie;
import model.Verificator;

public interface IService {

    Programator loginProgramator(String username, String password, Observer client) throws Exception;

    Verificator loginVerificator(String username, String password, Observer client) throws Exception;

    void logout(Long id) throws Exception;

    void adaugaBug(Bug bug) throws Exception;

    void adaugaSolutie(Solutie solutie) throws Exception;

    Iterable<Bug> findAllBuguriNerezolvate() throws Exception;

    Iterable<Solutie> findAllSolutii() throws Exception;

    void updateBug(Bug bug, Long idBug) throws Exception;


}
