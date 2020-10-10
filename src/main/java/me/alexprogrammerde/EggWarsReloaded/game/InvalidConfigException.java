package me.alexprogrammerde.EggWarsReloaded.game;

public class InvalidConfigException extends Exception {
    public InvalidConfigException(String property) {
        super("Invalid config! Failed to read: " + property);
    }
}
