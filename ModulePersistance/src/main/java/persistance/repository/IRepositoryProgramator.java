package persistance.repository;

import model.Programator;

public interface IRepositoryProgramator extends Repository<Long, Programator>{

    Programator loginProgramator(String username, String password);

}
