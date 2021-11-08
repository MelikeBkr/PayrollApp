package mel.springframework.restdocs.repositories;

import mel.springframework.restdocs.domain.Coffee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CoffeeRepository extends PagingAndSortingRepository<Coffee, UUID> {
}
