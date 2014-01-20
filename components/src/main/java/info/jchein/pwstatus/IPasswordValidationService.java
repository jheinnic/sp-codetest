package info.jchein.pwstatus;

import info.jchein.pwstatus.model.Password;
import info.jchein.pwstatus.model.PasswordValidity;

public interface IPasswordValidationService {
    PasswordValidity checkValidity( Password pwString );
}
