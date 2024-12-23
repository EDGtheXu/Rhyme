package rhymestudio.rhyme.config;
import rhymestudio.rhyme.config.ExtensionConfig.Element;
public class MainConfig {
    public static final ExtensionConfig.ConfigBuilder CONFIG = ExtensionConfig.Builder("test_config");

    public static Element bgm = CONFIG.add("IfOpenBGM", true, "If open BGM");



    public static ExtensionConfig cfg = CONFIG.build();
}
