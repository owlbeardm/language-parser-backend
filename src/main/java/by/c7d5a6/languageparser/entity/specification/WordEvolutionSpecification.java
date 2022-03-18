package by.c7d5a6.languageparser.entity.specification;

import com.owlbeardm.languageparser.entity.EWord;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class WordEvolutionSpecification implements Specification<EWord> {

    private SearchCriteria criteria;

    public WordEvolutionSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<EWord> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    EWord_.
        root.join("wordEvolution", JoinType.LEFT);

        return null;
    }
}
