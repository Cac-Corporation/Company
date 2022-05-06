package com.cactt4ck.company;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class CompanyCommands implements TabExecutor {

    private Main main;

    public CompanyCommands(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {


        if (!(sender instanceof Player)) {
            sender.sendMessage("Only player can use this command!");
            return true;
        }

        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("company")) {
            if (args.length > 1)
                return false;
            if (args[0].equalsIgnoreCase("info")) {
                p.sendMessage("Infos");
                return true;

            } else if (args[0].equalsIgnoreCase("create")) {
                p.sendMessage("Create");
                return true;

            } else if (args[0].equalsIgnoreCase("leave")) {
                p.sendMessage("Leave");
                return true;

            } else if (args[0].equalsIgnoreCase("invite")) {
                p.sendMessage("Invite");
                return true;

            } else if (args[0].equalsIgnoreCase("kick")) {
                p.sendMessage("Kick");
                return true;

            } else if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage("List");
                String template = "Name: {0}\n" +
                        "CEO: {1}\n" +
                        "Amount: {2}";
                for (Company company : main.getCompanies()) {
                    p.sendMessage(MessageFormat.format(template, company.getName(), company.getCeo().getName(), String.valueOf(company.getAccountAmount())));
                }
                /*String query = "select * from companies";
                try {
                    Statement statement = this.main.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    while (resultSet.next()) {
                        p.sendMessage(resultSet.getString("name"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }*/
                return true;

            } else if (args[0].equalsIgnoreCase("member")) {
                p.sendMessage("Member");
                return true;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("company")) {
            if (args.length == 1)
                return Arrays.asList("info", "create", "leave", "invite", "kick", "list", "member");
        }
        return null;
    }
}
