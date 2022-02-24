package by.c7d5a6.languageparser.rest.model;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.function.Function;

@SuppressWarnings("unused")
public class PaginationFilter implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Min(0)
    @Max(10_000)
    @Parameter(description = "page number (starting from 0)")
    @Schema(defaultValue = "0")
    private int page = 0;

    @Min(1)
    @Max(250)
    @Parameter(description = "page size/limit")
    @Schema(defaultValue = "10")
    private int size = 10;

    @Parameter(description = "sort direction")
    private SortDirection dir = SortDirection.asc;

    @Parameter(description = "sort column")
    @Schema(defaultValue = "id")
    private String sort;

    public PaginationFilter() {
    }

    public PaginationFilter(int page, int size, SortDirection dir, String sort) {
        this.page = page;
        this.size = size;
        this.dir = dir;
        this.sort = sort;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setDir(SortDirection dir) {
        this.dir = dir;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int calcOffset() {
        return page * size;
    }

    public SortDirection getDir() {
        return dir;
    }

    public Sort.Direction calcSortDirection() {
        return getDir() == SortDirection.desc ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    public String getSort() {
        return sort;
    }

    public PageRequest toPageable() {
        return toPageable(null);
    }

    public PageRequest toPageable(Function<String, Sort> sorter) {
        logger.info("PaginationFilter.toPageable: page={}, size={}, dir={}, sort={}", page, size, dir, sort);
        PageRequest result = PageRequest.of(getPage(), getSize());
        if (sorter == null) {
            result = result.withSort(Sort.by(Sort.Order.by("id").with(calcSortDirection())));
        } else {
            Sort sortList = sorter.apply(getSort());
            if (calcSortDirection() == Sort.Direction.ASC) {
                sortList = sortList.ascending();
            } else {
                sortList = sortList.descending();
            }
            result = result.withSort(sortList);
        }
        return result;
    }
}
