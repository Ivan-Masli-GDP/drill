package drill.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(unique = true)
	private Long accountNumber;
	private String username;
	private Long balance;
	@OneToMany(targetEntity = BankingTransaction.class, mappedBy = "senderAccount")
	private List<BankingTransaction> transactionHistory;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public List<BankingTransaction> getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(
			List<BankingTransaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public static Account newAccount() {
		Account acc = new Account();
		acc.setAccountNumber(generator());
		acc.setBalance((long) 0);
		acc.setTransactionHistory(new ArrayList<BankingTransaction>());
		return acc;
	}

	public static long generator() {
		Random r = new Random();
		int Low = 100000;
		int High = 999999;
		int R = r.nextInt(High - Low) + Low;
		return R;
	}
}
