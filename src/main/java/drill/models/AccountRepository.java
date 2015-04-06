package drill.models;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository  extends CrudRepository<Account,Long>{
	public Account findById(Long id);
	public Account findByAccountNumber(Long number);
	public List<Account> findAll();
	public Account findByUsername(String username);
}
