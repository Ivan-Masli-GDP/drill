package drill.models;

public class DummyAccount {
	private Long accountNumber;
	private String username;
	private Long balance;

	public DummyAccount(Account acc) {

		this.accountNumber = acc.getAccountNumber();
		this.username = acc.getUsername();
		this.balance = acc.getBalance();
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
