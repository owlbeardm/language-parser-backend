package by.c7d5a6.languageparser.entity.base;

import com.vladmihalcea.hibernate.type.array.EnumArrayType;
import com.vladmihalcea.hibernate.type.array.LongArrayType;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.MappedSuperclass;

/**
 * Superclass for global annotations
 */
@MappedSuperclass
@TypeDefs({
        @TypeDef(
                name = "pgsql_enum_array",
                typeClass = EnumArrayType.class
        ),
        @TypeDef(
                name = "pgsql_long_array",
                typeClass = LongArrayType.class
        ),
        @TypeDef(
                name = "pgsql_enum",
                typeClass = PostgreSQLEnumType.class
        )
})
public class RootEntity {
    protected static final String SEQ_NAME = "pk_sequence";
    protected static final int DB_SEQ_ALLOCATION_SIZE = 20;
}
