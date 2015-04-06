package drill.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
public interface BankingTransactionRepository extends CrudRepository<BankingTransaction,Long> {

}
