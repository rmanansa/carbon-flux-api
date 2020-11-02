package org.carbonflux.rest.dal;

import org.springframework.jdbc.core.SqlParameter;

import java.util.Map;

public interface Dao {
    String callDBFunction(String funcName);
    String callDBFunction(String funcName, SqlParameter[] parameters, Map<String, Object> dbInput);
    String callDBFunctionWithJSON(String funcName, SqlParameter[] parameters, String jsonInput);
}
