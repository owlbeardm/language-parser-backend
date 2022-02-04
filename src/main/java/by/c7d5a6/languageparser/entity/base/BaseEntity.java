package by.c7d5a6.languageparser.entity.base;


import by.c7d5a6.languageparser.entity.possessors.IdLongVerPossessor;

import javax.persistence.*;

/**
 * Superclass for long id(autogenerated by sequence), ver, created-when and modi-when fields.
 */
@MappedSuperclass
public abstract class BaseEntity extends BaseModiDatesEntity implements IdLongVerPossessor {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = DB_SEQ_ALLOCATION_SIZE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Version
    @Column
    private Long version = 1L; // Start version counter from 1 for new objects

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseEntity)) {
            return false;
        }
        return getId() != null && getId().equals(((BaseEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}