//package drill.controllers;
//
//import drill.exception.InsufficientFundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import drill.exception.BankAccountNotFoundException;
//
//@ControllerAdvice
//public class AdviserController {
////	catch (BankAccountNotFoundException e) {
////		return new ResponseEntity<DummyTransaction>(HttpStatus.BAD_GATEWAY);
////	} catch (InsufficientFundException e) {
////		return new ResponseEntity<DummyTransaction>(HttpStatus.BAD_REQUEST);
////	}
////	catch (BankAccountNotFoundException e) {
////		return new ResponseEntity<DummyAccount>(HttpStatus.BAD_REQUEST);
////	}
////	InsufficientFundException
//	
//	@ExceptionHandler(BankAccountNotFoundException.class)
//	public ResponseEntity accountNotFound(){
//		return new ResponseEntity(HttpStatus.BAD_REQUEST);
//	}
//	@ExceptionHandler(InsufficientFundException.class)
//	public ResponseEntity notEnoughMoney(){
//		return new ResponseEntity(HttpStatus.BAD_REQUEST);
//	}
//}
