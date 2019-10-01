package com.wetcoding.configuration;

/**
 * Класс исключения при работе к конфигурацией
 */
public class ConfigurationException extends Throwable {
    public ConfigurationException(String message){
        super(message);
    }
}
