package com.cactt4ck.company;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class Company {

    private List<Player> members;
    private String name;
    private UUID ceo;
    private double accountAmount;

    public Company(String name, double accountAmount, UUID ceo) {
        this.name = name;
        this.accountAmount = accountAmount;
        this.ceo = ceo;
    }

    public Player getCeo() {
        return (Player) Bukkit.getOfflinePlayer(ceo);
    }

    public List<Player> getMembers() {
        return members;
    }

    public void setMembers(List<Player> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(double accountAmount) {
        this.accountAmount = accountAmount;
    }

    public void addMember(Player member){
        this.members.add(member);
    }
}
