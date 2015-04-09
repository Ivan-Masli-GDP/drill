package drill.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST,
reason="Insufficient fund")
public class InsufficientFundException extends Exception {

}
