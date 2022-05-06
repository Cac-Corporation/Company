package com.cactt4ck.company;

import org.bukkit.Bukkit;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static File configFile;
    private Connection connection;
    public Logger log = this.getLogger();
    private List<Company> companies;

    @Override
    public void onEnable() {
        log.info("§6   ____    ___    __  __   ____       _      _   _  __   __");
        log.info("§6  / ___|  / _ \\  |  \\/  | |  _ \\     / \\    | \\ | | \\ \\ / /");
        log.info("§6 | |     | | | | | |\\/| | | |_) |   / _ \\   |  \\| |  \\ V / ");
        log.info("§6 | |___  | |_| | | |  | | |  __/   / ___ \\  | |\\  |   | |  ");
        log.info("§6  \\____|  \\___/  |_|  |_| |_|     /_/   \\_\\ |_| \\_|   |_|  ");
        log.info("");

        config = getConfig();
        config.options().copyDefaults(true);
        configFile = new File(this.getDataFolder(), "config.yml");
        this.saveDefaultConfig();

        config = YamlConfiguration.loadConfiguration(configFile);

        log.info("Company plugin enabled!");
        this.companies = new ArrayList<Company>();
        this.initDatabase();

        TabExecutor company = new CompanyCommands(this);
        this.getCommand("company").setExecutor(company);
        this.getCommand("company").setTabCompleter(company);
        /*Bukkit.getPluginManager().registerEvents(new Listener(), this);*/

    }

    @Override
    public void onDisable() {
        this.saveDefaultConfig();
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        log.info("Disabling Company plugin");
    }

    private void initDatabase() {
        String uri = this.getDataFolder() + "\\database.db";
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + uri);
            if (connection != null) {
                this.connection = connection;
                DatabaseMetaData metaData = connection.getMetaData();
                ResultSet resultSet = metaData.getTables(null, null, "companies", null);
                if (!resultSet.next()) {
                    String createTableQuery = "create table companies (companies_id integer constraint companies_pk primary key autoincrement, name TEXT  not null, funds FLOAT not null, ceo TEXT not null, members TEXT); create unique index companies_companies_id_uindex on companies (companies_id);";
                    Statement statement = connection.createStatement();
                    statement.execute(createTableQuery);
                    log.info("§aDatabase not found! Creating a new one!");
                } else {
                    log.info("§aDatabase found! Loading it");
                    String loadCompanies = "select name, funds, ceo from companies";
                    String loadMembers = "select members from companies";
                    Statement statement = connection.createStatement();
                    ResultSet result = statement.executeQuery(loadCompanies);
                    while (result.next()) {
                        String name = result.getString("name");
                        double funds = result.getFloat("funds");
                        String ceo = result.getString("ceo");
                        this.companies.add(new Company(name, funds, UUID.fromString(ceo)));
                        log.info("§cCompany Found: " + ceo);

                        /*Statement mStatement = connection.createStatement();
                        ResultSet set = mStatement.executeQuery(loadMembers);
                        if (set.next()) {
                            Array a = set.getArray(0);
                            String[] members = (String[]) a.getArray();
                            if (members.length != 0)
                                for (String s : members)
                                    company.addMember(Bukkit.getPlayer(s));
                            this.companies.add(company);
                        }*/
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public static void saveFile() {
        /*try{
            jobs.save(jobsFile);
        }catch (IOException e){
            e.printStackTrace();
        }*/
    }
}
