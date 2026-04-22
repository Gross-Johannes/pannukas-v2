package net.teymm.pannukas.image;

import jakarta.persistence.*;
import net.teymm.pannukas.common.entity.BaseEntity;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    @Column(nullable = false)
    private String altText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImageType imageType;

    @Column(nullable = false)
    private boolean visible;

    public Image() {
    }

    public Image(String altText, ImageType imageType, boolean visible) {
        this.altText = altText;
        this.imageType = imageType;
        this.visible = visible;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
