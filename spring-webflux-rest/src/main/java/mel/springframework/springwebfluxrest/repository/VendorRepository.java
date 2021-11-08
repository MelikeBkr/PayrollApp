package mel.springframework.springwebfluxrest.repository;

import mel.springframework.springwebfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor,String>
{
}
