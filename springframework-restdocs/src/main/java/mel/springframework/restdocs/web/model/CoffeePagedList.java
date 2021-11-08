package mel.springframework.restdocs.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CoffeePagedList extends PageImpl<CoffeeDto> {

    public CoffeePagedList(List<CoffeeDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public CoffeePagedList(List<CoffeeDto> content) {
        super(content);
    }
}