package drill.models;

import java.sql.Date;

public class DummyTransaction {
	private long transaction_id;
	private Date date;
	private long amount;
	private long from;
	private long to;

	public DummyTransaction(BankingTransaction trans) {
		this.transaction_id = trans.getTransaction_id();
		this.date = trans.getTransactionDate();
		this.amount = trans.getAmount();
		this.from = trans.getSenderAccount().getAccountNumber();
		this.to = trans.getReceiverAccount().getAccountNumber();
	}
	
	public long getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getFrom() {
		return from;
	}

	public void setFrom(long from) {
		this.from = from;
	}

	public long getTo() {
		return to;
	}

	public void setTo(long to) {
		this.to = to;
	}

}
