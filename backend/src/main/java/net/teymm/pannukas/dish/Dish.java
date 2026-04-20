package net.teymm.pannukas.dish;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import net.teymm.pannukas.common.entity.BaseEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "dishes")
public class Dish extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    public Dish() {
    }

    public Dish(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price.doubleValue();
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
