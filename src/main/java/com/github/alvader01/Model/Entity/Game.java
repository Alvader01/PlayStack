package com.github.alvader01.Model.Entity;

import java.util.Objects;

public class Game {

    private int id;
    private String name;
    private String platform;

    public Game(int id, String name, String platform) {
        this.id = id;
        this.name = name;
        this.platform = platform;
    }

    public Game(String name, String platform) {
        this.name = name;
        this.platform = platform;

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
                '}';
    }

}
