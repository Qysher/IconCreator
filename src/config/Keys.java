package config;

import core.Start;

import java.io.File;

public class Keys {

    // Background Path
    public static final StaticKey<String> KEY_BACKGROUND_PATH = new StaticKey<>("KEY_BACKGROUND_PATH", "");

    // Foreground Path
    public static final StaticKey<String> KEY_FOREGROUND_PATH = new StaticKey<>("KEY_FOREGROUND_PATH", "");

    // Icon Color Options
    public static final StaticKey<Boolean> KEY_OVERRIDE_COLOR_ENABLED = new StaticKey<>("KEY_OVERRIDE_COLOR_ENABLED", false);
    public static final StaticKey<String> KEY_OVERRIDE_COLOR_HEXCODE = new StaticKey<>("KEY_OVERRIDE_COLOR_HEXCODE", "000000");

    // Overlay Path
    public static final StaticKey<String> KEY_OVERLAY_PATH = new StaticKey<>("KEY_OVERLAY_PATH", "");

    // Margin Value
    public static final StaticKey<Integer> KEY_MARGIN_VALUE = new StaticKey<>("KEY_MARGIN_VALUE", 0);

    // Background Brightness Value
    public static final StaticKey<Integer> KEY_BACKGROUND_BRIGHTNESS_VALUE = new StaticKey<>("KEY_BACKGROUND_BRIGHTNESS_VALUE", 255);

    // Save Directory last directory
    public static final StaticKey<String> KEY_SAVE_DIRECTORY_LAST_DIRECTORY = new StaticKey<>("KEY_SAVE_DIRECTORY_LAST_DIRECTORY", "");

    // Code
    public static Config config = Start.config;
    public static class StaticKey <T> {
        public String key;
        public T default_value;

        public StaticKey(String key, T default_value) {
            this.key = key;
            this.default_value = default_value;
        }

        public String toString() {
            return key;
        }

        public void setValue(T value) {
            if(config == null) return;
            config.set(key, value);
        }

        public T getValue() {
            if(config == null) return null;
            return config.get(key, default_value);
        }

        public T getValue(T override_default_value) {
            if(config == null) return null;
            return config.get(key, override_default_value);
        }

        public boolean hasValue() {
            return config.contains(key);
        }
    }

}
