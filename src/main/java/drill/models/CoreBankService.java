package drill.models;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

@Repository
public class CoreBankService {
	@Resource
	private AccountRepository repoAccount;
	@Resource
	private BankingTransactionRepository repoTransaction;

	

	public Account findByNumber(Long id) throws BankAccountNotFoundException {
		Account acc = repoAccount.findByAccountNumber(id);
		if(acc == null){
			throw new BankAccountNotFoundException();
		}
		return repoAccount.findByAccountNumber(id);
	}

	@Transactional
	public Account updateBalance(Account updated) {
		Account toBeUpdated = repoAccount.findById(updated.getId());
		toBeUpdated.setBalance(updated.getBalance());
		return toBeUpdated;
	}

	public Account saveAccount() {
		Account acc = Account.newAccount();
		System.out.println("<--------->");
		repoAccount.save(acc);
		return acc;
	}

	public List<Account> findAll() {
		return repoAccount.findAll();
	}
	public Account findAccount(String username) throws BankAccountNotFoundException{
		Account acc = repoAccount.findByUsername(username);
		if(acc == null){
			throw new BankAccountNotFoundException();
		}
		return acc;
	}

}
