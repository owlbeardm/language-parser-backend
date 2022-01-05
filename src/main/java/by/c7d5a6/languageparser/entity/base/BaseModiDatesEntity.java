package by.c7d5a6.languageparser.entity.base;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class BaseModiDatesEntity extends RootEntity implements IModiDatesEntity {

    @CreatedDate
    @Column(name = "createdwhen", updatable = false)
    private Instant createdWhen;

    @LastModifiedDate
    @Column(name = "modiwhen")
    private Instant modiWhen;

    @Override
    public Instant getCreatedWhen() {
        return createdWhen;
    }

    @Override
    public void setCreatedWhen(Instant createdWhen) {
        this.createdWhen = createdWhen;
    }

    @Override
    public Instant getModiWhen() {
        return modiWhen;
    }

    @Override
    public void setModiWhen(Instant modiWhen) {
        this.modiWhen = modiWhen;
    }

    public void touch() {
        final Instant now = Instant.now();
        if (this.modiWhen != null) {
            if (!now.isAfter(this.modiWhen)) {
                this.modiWhen = this.modiWhen.plusMillis(1);
            } else {
                this.modiWhen = now;
            }
        } else {
            this.modiWhen = now;
        }
    }
}
