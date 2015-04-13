/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import drill.Application;
import drill.exception.BankAccountNotFoundException;
import drill.models.Account;
import drill.models.CoreBankService;

/**
 *
 * @author ivan.masli
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@DatabaseSetup("datasetTesting.xml")
public class DatabaseTesting {

	@Autowired
	private CoreBankService coreBankService;

	@Test
	public void testFindUsername() throws BankAccountNotFoundException {
		Account account = coreBankService.findAccount("ivan");
		assertThat(account.getUsername(), is("ivan"));
	}

	@Test(expected = BankAccountNotFoundException.class)
	public void testFindUsernameNotFound() throws BankAccountNotFoundException {
		Account account = coreBankService.findAccount("charles");
	}

	@Test
	public void testFindAcountNumber() throws BankAccountNotFoundException {
		Account acc = coreBankService.findByNumber((long) 12345);
		assertThat(acc.getUsername(), is("ivan"));
	}

	@Test(expected = BankAccountNotFoundException.class)
	public void testFindAcountNumberNotFound()
			throws BankAccountNotFoundException {
		Account acc = coreBankService.findByNumber((long) 12346);

	}
	@Test
	public void testUpdateBalance() throws BankAccountNotFoundException{
//		updateBalance
		Account account = coreBankService.findAccount("ivan");
		account.setBalance((long) 1234);
		coreBankService.updateBalance(account);
		Account accountUpdated = coreBankService.findAccount("ivan");
		
		assertEquals(accountUpdated.getBalance().longValue(), 1234);
	}
	
	
	

}
