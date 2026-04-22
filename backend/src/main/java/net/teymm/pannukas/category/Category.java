package net.teymm.pannukas.category;

import jakarta.persistence.*;
import net.teymm.pannukas.common.entity.BaseEntity;
import net.teymm.pannukas.dish.Dish;
import net.teymm.pannukas.image.Image;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "category_type_enum")
    private CategoryType categoryType;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cover_image_id")
    private Image coverImage;

    @OneToMany(mappedBy = "category")
    private List<Dish> dishes = new ArrayList<>();

    public Category() {
    }

    public Category(String title, CategoryType categoryType, Image coverImage) {
        this.title = title;
        this.categoryType = categoryType;
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
