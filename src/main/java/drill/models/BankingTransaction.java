package drill.models;

import java.sql.Date;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BankingTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long transaction_id;
	private long amount;
	@ManyToOne(targetEntity = Account.class)
	private Account senderAccount;
	@ManyToOne(targetEntity = Account.class)
	private Account receiverAccount;

	private Date transactionDate;
	public BankingTransaction(){
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Account getSenderAccount() {
		return senderAccount;
	}

	public void setSenderAccount(Account senderAccount) {
		this.senderAccount = senderAccount;
	}

	public Account getReceiverAccount() {
		return receiverAccount;
	}

	public void setReceiverAccount(Account receiverAccount) {
		this.receiverAccount = receiverAccount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public static class Builder {

		private long amount;
		private long transaction_id;
		private Account senderAccount;
		private Account receiverAccount;
		private Date transactionDate;

		public Builder amount(long value) {
			this.amount = value;
			return this;
		}

		public Builder transaction_id(long id) {
			this.transaction_id = id;
			return this;
		}

		public Builder transactionDate(Date date) {
			this.transactionDate = date;
			return this;
		}

		public Builder senderAccount(Account acc) {
			this.senderAccount = acc;
			return this;
		}

		public Builder receiverAccount(Account acc) {
			this.receiverAccount = acc;
			return this;
		}

		public BankingTransaction build() {
			return new BankingTransaction(this);
		}
	}

	public BankingTransaction(Builder builder) {
		amount = builder.amount;
		transactionDate = builder.transactionDate;
		senderAccount = builder.senderAccount;
		receiverAccount = builder.receiverAccount;
		transaction_id = builder.transaction_id;
	}

	public static long generator() {
		Random r = new Random();
		int Low = 100000;
		int High = 999999;
		int R = r.nextInt(High - Low) + Low;
		return R;
	}

}
