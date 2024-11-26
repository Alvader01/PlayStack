package com.github.alvader01.Model.Entity;

import java.util.Objects;

public class Game {

    private int id;
    private String name;
    private String platform;
    private String description;

    public Game(int id, String name, String platform, String description) {
        this.id = id;
        this.name = name;
        this.platform = platform;
        this.description = description;
    }

    public Game(String name, String platform, String description) {
        this.name = name;
        this.platform = platform;
        this.description = description;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", platform='" + platform + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
