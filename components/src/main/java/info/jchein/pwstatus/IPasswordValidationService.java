package info.jchein.pwstatus;

import info.jchein.pwstatus.model.PasswordValidity;

public interface IPasswordValidationService {
    PasswordValidity checkValidity( char[] pwString );
}
