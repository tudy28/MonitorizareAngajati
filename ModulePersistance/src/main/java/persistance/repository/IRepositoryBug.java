package persistance.repository;

import model.Bug;

public interface IRepositoryBug extends Repository<Long, Bug>{
    Iterable<Bug> findAllBuguriNerezolvate();
}
