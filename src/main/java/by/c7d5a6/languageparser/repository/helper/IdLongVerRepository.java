package by.c7d5a6.languageparser.repository.helper;

import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdLongVerRepository<T extends IdLongVerPossessor> extends JpaRepository<T, Long> {
    default T loadByIdVer(Long id, Long ver) {
        T entity = getById(id);
        if (entity.getVersion() != ver) {
            //TODO: throw exception
//            throw new OptimisticEntityLockException(entity, "version is changed");
        }
        return entity;
    }
}
