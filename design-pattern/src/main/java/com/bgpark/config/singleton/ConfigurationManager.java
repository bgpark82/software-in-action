package com.bgpark.config.singleton;

/**
 * Singleton
 * - Only one instance exist in the system
 * - Class has private constructor, it's not allow to create instance from outside
 * - Only has one static method to create singleton instance
 */
public class ConfigurationManager {

    private static ConfigurationManager configurationManager;
    private String databaseUrl;

    // private constructor
    private ConfigurationManager() {
        this.databaseUrl = "jdbc:mysql://localhost:3306";
    }

    // static getInstance
    public static synchronized ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }
}
