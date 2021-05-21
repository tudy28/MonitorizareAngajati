package persistance.repository;

import model.Programator;
import model.Verificator;

public interface IRepositoryVerificator extends Repository<Long, Verificator> {

    Verificator loginVerificator(String username, String password);

}
