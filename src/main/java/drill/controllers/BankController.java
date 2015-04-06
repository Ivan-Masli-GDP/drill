package drill.controllers;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import drill.models.Account;
import drill.models.BankAccountNotFoundException;
import drill.models.BankingTransaction;
import drill.models.CoreBankService;

@ComponentScan("drill.models")
@RestController
public class BankController {
	@Resource
	private CoreBankService coreBankService;

	@Autowired
	public BankController(CoreBankService coreBankService) {
		this.coreBankService = coreBankService;
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public Account saveNewAccount() {
		return coreBankService.saveAccount();
	}

	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public Account showAllAccounts() {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();

			return coreBankService.findAccount(auth.getName());
		} catch (BankAccountNotFoundException e) {
			return null;
		}
	}
	@RequestMapping(value = "/transfers", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<BankingTransaction> cashTransfer(
			@RequestBody Map<String, Object> map) {

		long senderId = new Long((int) map.get("from"));
		long receiverId = new Long((int) map.get("to"));
		long amount = new Long((int) map.get("amount"));

		try {
			Account senderAccount = coreBankService.findByNumber(senderId);
			Account receiverAccount = coreBankService.findByNumber(receiverId);
			transferMoney(senderAccount, receiverAccount, amount);

			java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
			BankingTransaction transaction = new BankingTransaction.Builder()
					.amount(amount).receiverAccount(senderAccount)
					.senderAccount(senderAccount)
					.transaction_id(BankingTransaction.generator())
					.transactionDate(sqlDate).build();
			return new ResponseEntity<BankingTransaction>(transaction,
					HttpStatus.OK);
		} catch (BankAccountNotFoundException e) {
			return new ResponseEntity<BankingTransaction>(
					HttpStatus.BAD_REQUEST);
		} catch (InsufficientFundException e) {
			return new ResponseEntity<BankingTransaction>(
					HttpStatus.BAD_REQUEST);
		}
	}

	@Transactional
	private void transferMoney(Account sender, Account receiver, long amount)
			throws InsufficientFundException {
		if (sender.getBalance() < amount) {
			throw new InsufficientFundException();
		} else {
			sender.setBalance(sender.getBalance() - amount);
			receiver.setBalance(receiver.getBalance() + amount);
		}
	}

}
