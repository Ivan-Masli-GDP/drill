package drill.models;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import drill.exception.BankAccountNotFoundException;


@Repository
public class CoreBankService {

    @Resource
    private AccountRepository repoAccount;
    @Resource
    private BankingTransactionRepository repoTransaction;
    @Cacheable("cacheBank")
    public Account findByNumber(Long id) throws BankAccountNotFoundException {
        Account acc = repoAccount.findByAccountNumber(id);
        if (acc == null) {
            throw new BankAccountNotFoundException();
        }
        return repoAccount.findByAccountNumber(id);
    }

    @Transactional
    @CachePut(value="cacheBank", key = "#result.id" )
    public Account updateBalance(Account updated) {
        Account toBeUpdated = repoAccount.findById(updated.getId());
        toBeUpdated.setBalance(updated.getBalance());
        return toBeUpdated;
    }
    @CachePut(value="cacheBank", key = "#result.id" )
    public Account saveAccount(String username) {
        Account acc = Account.newAccount(username);
        
        repoAccount.save(acc);
        return acc;
    }

    public void saveTransaction(BankingTransaction transaction) {
        repoTransaction.save(transaction);
    }

    public List<BankingTransaction> findTransactions(long senderId) {
        return repoTransaction.findBySenderAccount_Id(senderId);
    }

    @Cacheable(value="cacheBank", key="#result.id")
    public Account findAccount(String username) throws BankAccountNotFoundException {
        Account acc = repoAccount.findByUsername(username);
        if (acc == null) {
            throw new BankAccountNotFoundException();
        }
        return acc;
    }
    
    public BankingTransaction findTransactionById(long id){
    	return repoTransaction.findById(id);
    }
}
