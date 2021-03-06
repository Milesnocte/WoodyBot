package Main;

import java.sql.*;

public class DataBase{

    private static Connection connect = null;

    //Make sure we can connect to the database
    public void databaseCycle() throws Exception {
        System.out.println("Connecting to database...");
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        System.out.println("Database cycled\nClosing Database");
        connect.close();
    }

    //If the bot joins a guild we add it to the guilds table
    public void addGuild(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("INSERT INTO guilds values(?,?,?,?,?);");
        prepared.setString(1,gID);
        prepared.setString(2,"null");
        prepared.setString(3,"$");
        prepared.setString(4,"null");
        prepared.setString(5,"null");
        prepared.execute();
        connect.close();
    }

    public void dropGuild(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("DELETE FROM guilds WHERE GuildID = " + gID +";");
        prepared.execute();
        connect.close();
    }

    public void updateGuildPrefix(String gID, String prefix) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("UPDATE guilds SET prefix = '" + prefix + "' WHERE GuildID = '" + gID + "';");
        prepared.execute();
        connect.close();
    }

    public void updateGuildRoleID(String gID, String roleID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("UPDATE guilds SET roleId = '" + roleID + "' WHERE GuildID = '" + gID + "';");
        prepared.execute();
        connect.close();
    }

    public void updateGuildeventsId(String gID, String eventsId) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("UPDATE guilds SET eventsId = '" + eventsId + "' WHERE GuildID = '" + gID + "';");
        prepared.execute();
        connect.close();
    }

    public String getPrefix(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Statement prepared;
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        prepared = connect.createStatement();
        ResultSet guildPrefixRS = prepared.executeQuery("SELECT prefix FROM guilds WHERE GuildID = " + gID +";");
        String guildPrefix = guildPrefixRS.getString("prefix");
        connect.close();
        return guildPrefix;
    }

    public static String getReactRoleid(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Statement prepared;
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        prepared = connect.createStatement();
        ResultSet guildPrefix = prepared.executeQuery("SELECT roleId FROM guilds WHERE GuildID = " + gID +";");
        String guildPrefixActual = guildPrefix.getString("roleId");
        guildPrefix.close();
        connect.close();
        return guildPrefixActual;
    }

    public static String getReacteventsId(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Statement prepared;
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        prepared = connect.createStatement();
        ResultSet guildPrefix = prepared.executeQuery("SELECT eventsId FROM guilds WHERE GuildID = " + gID +";");
        String guildPrefixActual = guildPrefix.getString("eventsId");
        guildPrefix.close();
        connect.close();
        return guildPrefixActual;
    }

    public static String getDrawing() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        Statement prepared = connect.createStatement();
        ResultSet guildPrefix = prepared.executeQuery("SELECT names FROM giveaway ORDER BY RANDOM() LIMIT 1;");
        String guildPrefixActual = guildPrefix.getString("names");
        guildPrefix.close();
        connect.close();
        return guildPrefixActual;
    }

    public void updateConcentrationID(String gID, String roleID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        PreparedStatement prepared;
        prepared = connect.prepareStatement("UPDATE guilds SET concentrationId = '" + roleID + "' WHERE GuildID = '" + gID + "';");
        prepared.execute();
        connect.close();
    }

    public static String getConcentrationID(String gID) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Statement prepared;
        connect = DriverManager.getConnection("jdbc:sqlite:VCP.db");
        prepared = connect.createStatement();
        ResultSet guildPrefix = prepared.executeQuery("SELECT concentrationId FROM guilds WHERE GuildID = " + gID +";");
        String guildPrefixActual = guildPrefix.getString("concentrationId");
        guildPrefix.close();
        connect.close();
        return guildPrefixActual;
    }
}