package quiz.apple.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import quiz.apple.model.Retailer;

@Component
public interface LocRepo extends CrudRepository<Retailer, Integer> {
	public List<Retailer> findAllRetailersByAddress(@Param("address") String address);
}
