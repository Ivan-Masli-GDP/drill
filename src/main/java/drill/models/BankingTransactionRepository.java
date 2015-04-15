package drill.models;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
public interface BankingTransactionRepository extends CrudRepository<BankingTransaction,Long> {

	//@Query("SELECT c FROM Company c INNER JOIN FETCH c.employees WHERE c.id = :senderId")
	List<BankingTransaction> findBySenderAccount_Id(long senderId);
	BankingTransaction findById(long id);
}
