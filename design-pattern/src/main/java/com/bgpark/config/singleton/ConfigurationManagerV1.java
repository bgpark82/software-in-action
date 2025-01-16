package com.bgpark.config.singleton;

public class ConfigurationManagerV1 {

    private static ConfigurationManagerV1 managerV1;

    public ConfigurationManagerV1 getInstance() {
        if (managerV1 == null) {
            return new ConfigurationManagerV1();
        }
        return managerV1;
    }

    private ConfigurationManagerV1() {
        this.managerV1 = new ConfigurationManagerV1();
    }
}
