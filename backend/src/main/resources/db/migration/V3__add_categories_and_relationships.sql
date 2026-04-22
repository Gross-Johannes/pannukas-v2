CREATE TYPE category_type_enum AS ENUM ('MAIN_MENU', 'PANCAKE_MENU');

CREATE TABLE categories
(
    id             UUID                        NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    title          VARCHAR(255)                NOT NULL,
    category_type  category_type_enum          NOT NULL,
    cover_image_id UUID,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT uk_categories_title UNIQUE (title),
    CONSTRAINT fk_categories_cover_image FOREIGN KEY (cover_image_id) REFERENCES images (id)
);

ALTER TABLE dishes
    ADD COLUMN category_id UUID NOT NULL;

ALTER TABLE dishes
    ADD CONSTRAINT fk_dishes_category FOREIGN KEY (category_id) REFERENCES categories (id);