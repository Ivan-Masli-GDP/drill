package drill.controllers;

import drill.exception.InsufficientFundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import drill.models.Account;
import drill.exception.BankAccountNotFoundException;
import drill.models.BankingTransaction;
import drill.models.CoreBankService;
import drill.models.DummyAccount;
import drill.models.DummyTransaction;

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
	@ResponseBody
	public ResponseEntity<DummyAccount> showAccounts()
			throws BankAccountNotFoundException {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Account acc = coreBankService.findAccount(auth.getName());

		return new ResponseEntity<DummyAccount>(new DummyAccount(acc),
				HttpStatus.OK);

	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<DummyTransaction>> getTransactionsHistory()
			throws BankAccountNotFoundException {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Account acc = coreBankService.findAccount(auth.getName());
		List<BankingTransaction> transactions = coreBankService
				.findTransactions(acc.getId());

		return new ResponseEntity<List<DummyTransaction>>(
				convertToDummy(transactions), HttpStatus.OK);
	}

	@RequestMapping(value = "/transfers", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public ResponseEntity<DummyTransaction> cashTransfer(
			@RequestBody Map<String, Object> map)
			throws BankAccountNotFoundException, InsufficientFundException {

		long receiverId = new Long((int) map.get("to"));
		long amount = new Long((int) map.get("amount"));

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Account senderAccount = coreBankService.findAccount(auth.getName());
		Account receiverAccount = coreBankService.findByNumber(receiverId);
		transferMoney(senderAccount, receiverAccount, amount);

		java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
		BankingTransaction transaction = new BankingTransaction.Builder()
				.amount(amount).receiverAccount(receiverAccount)
				.senderAccount(senderAccount)
				.transaction_id(BankingTransaction.generator())
				.transactionDate(sqlDate).build();
		coreBankService.saveTransaction(transaction);
		return new ResponseEntity<DummyTransaction>(new DummyTransaction(
				transaction), HttpStatus.OK);

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

	public static List<DummyTransaction> convertToDummy(
			List<BankingTransaction> transactions) {
		List<DummyTransaction> dummyList = new ArrayList<DummyTransaction>();
		for (BankingTransaction transaction : transactions) {
			dummyList.add(new DummyTransaction(transaction));
		}
		return dummyList;
	}

}
