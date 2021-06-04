package bugtracing.service;

import model.Bug;
import model.Solutie;

import java.rmi.Remote;

public interface Observer extends Remote {

    void notifyModifiedBug(Iterable<Bug> buguri) throws Exception;


    void notifyModifiedSolution(Iterable<Solutie> solutii) throws Exception;

}
