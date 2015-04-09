package drill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,
reason="Bank Account Not Found")
public class BankAccountNotFoundException extends Exception {

}
