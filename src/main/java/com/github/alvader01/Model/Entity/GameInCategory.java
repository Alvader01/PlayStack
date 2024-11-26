package com.github.alvader01.Model.Entity;

import java.util.Objects;

public class GameInCategory {
    private int categoryId;
    private int gameId;

    public GameInCategory(int categoryId, int gameId) {
        this.categoryId = categoryId;
        this.gameId = gameId;
    }

    public GameInCategory() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInCategory that = (GameInCategory) o;
        return categoryId == that.categoryId && gameId == that.gameId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, gameId);
    }

    @Override
    public String toString() {
        return "GameInCategory{" +
                "categoryId=" + categoryId +
                ", gameId=" + gameId +
                '}';
    }
}
