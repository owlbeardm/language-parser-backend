package by.c7d5a6.languageparser.rest.model.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class PageResult<T> implements Serializable {

    private final List<T> data;
    private final int page;
    private final long total;
    private final int totalPages;

    public PageResult(List<T> data, int page, long total, int totalPages) {
        this.data = data != null ? data : Collections.emptyList();
        this.page = page;
        this.total = total;
        this.totalPages = totalPages;
    }

    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getNumberOfElements(),
                page.getTotalPages()
        );
    }

    public static <S, R> PageResult<R> from(Page<S> page, Function<S, R> mapper) {
        return new PageResult<>(
                page.map(mapper).getContent(),
                page.getNumber(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<T> getData() {
        return data;
    }

    public int getPage() {
        return page;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
