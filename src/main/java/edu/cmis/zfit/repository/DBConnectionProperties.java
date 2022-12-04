package edu.cmis.zfit.repository;

public record DBConnectionProperties(String dbUrl,
                                     String dbUser,
                                     String dbPasswd) {
    //TODO find best path for file
    public static final String DEFAULT_DB_URL = "jdbc:h2:file:~/zFit";
    public static final String DEFAULT_DB_USER = "sa";
    public static final String DEFAULT_DB_PASSWD = "";

    public DBConnectionProperties() {
        this(DEFAULT_DB_URL, DEFAULT_DB_USER, DEFAULT_DB_PASSWD);
    }
}
