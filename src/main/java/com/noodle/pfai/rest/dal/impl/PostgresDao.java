package com.noodle.pfai.rest.dal.impl;

import com.google.gson.Gson;
import com.noodle.pfai.rest.dal.Dao;
import com.noodle.pfai.rest.service.UserEndpointService;
import org.postgresql.util.PGobject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.Map;

@Component
public class PostgresDao implements Dao {
    private static final Logger log = LoggerFactory.getLogger(PostgresDao.class);
    private static final String DB_JSON_OUT_PARAMETER = "v_result";
    private static final String API_DAO_LOG = "{\"pfai-weather-api-dao\": \"{}\"}";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall simpleJdbcCallFunction;
    private final String dbSchema;
    private String url;
    private String userName;
    private String password;

    public PostgresDao(JdbcTemplate jdbcTemplate,
                       @Value("${spring.flyway.schemas}") String dbSchema,
                       @Value("${spring.datasource.url}") String url,
                       @Value("${spring.datasource.username}") String userName,
                       @Value("${spring.datasource.password}") String password) {
        this.jdbcTemplate = jdbcTemplate;
        this.dbSchema = dbSchema;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    /**
     * This callDBFunction takes in a database function name as input.
     * It is used for calling methods that don't require input parameters.
     * @param funcName
     * @return
     */
    public String callDBFunction(String funcName) {
        SqlParameter[] sqlParameters = new SqlParameter[] {
                new SqlOutParameter(DB_JSON_OUT_PARAMETER, Types.OTHER)};
        return callDBFunction(funcName, sqlParameters, Collections.EMPTY_MAP);
    }

    /**
     * The callDBFunction method is used for queries that defines the SqlParameters
     * both for input and output parameters of the database function invoked.
     * The actual inputs are contained in a Map<String, Object>
     * @param funcName Dstabase function to be called
     * @param parameters SqlParameter that defines the parameter name and parameter type
     * @param dbInput Map<String, Object> that defines paramater name and its value
     * @return
     */
    public String callDBFunction(String funcName, SqlParameter[] parameters, Map<String, Object> dbInput) {
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCallFunction = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName(funcName).withSchemaName(dbSchema)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(parameters);
        PGobject jsonResp;

        try {
            jsonResp = simpleJdbcCallFunction.executeFunction(PGobject.class, dbInput);
        } catch (DataAccessException e) {
            log.error(API_DAO_LOG, e.getMessage());
            return "{\"error\": \"problem with database operation\"}";
        }
        return jsonResp.getValue();
    }

    /**
     * This callDBFunctionWithJSON takes JSON as input instead of Map. This is useful
     * if input has a List<Map> or other complex structure.
     * @param funcName Name of the database function
     * @param parameters SqlParameter definition of input types
     * @param jsonInput input payload
     * @return Will return a JSON string
     */
    public String callDBFunctionWithJSON(String funcName, SqlParameter[] parameters, String jsonInput) {
        jdbcTemplate.setResultsMapCaseInsensitive(true);
        simpleJdbcCallFunction = new SimpleJdbcCall(jdbcTemplate)
                .withFunctionName(funcName).withSchemaName(dbSchema)
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(parameters);
        PGobject jsonResp;
        Gson gson = new Gson();
        try {
            jsonResp = simpleJdbcCallFunction.executeFunction(PGobject.class, jsonInput);
        } catch (DataAccessException e) {
            log.error(API_DAO_LOG, e.getMessage());
            return "{\"error\": \"problem with database operation\"}";
        }
        return jsonResp.getValue();
    }

}
