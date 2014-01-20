package info.jchein.pwstatus.spi;

import java.util.List;


public interface IPasswordConstraintSpi {
    List<String> returnErrorStrings( String pwText );
}
