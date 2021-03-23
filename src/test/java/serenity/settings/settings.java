package serenity.settings;

import org.openqa.selenium.Dimension;

public class settings {
    // Screen size for headless mode run
    public static final Dimension screenSize = new Dimension(1920, 1080);

    // PG Auth data
    public static final String adminLoginName = "admin";
    public static final String adminPassword = "pgadmin";

    // PG_agent settings
    public static final String pg_host = "192.168.45.10";
    public static final String pg_user = "pg\\printserver-admin";
    public static final String pg_password = "123qweASD";
}
