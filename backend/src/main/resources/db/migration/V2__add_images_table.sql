CREATE TYPE image_type_enum AS ENUM ('GALLERY', 'EVENT', 'CATEGORY');

CREATE TABLE images
(
    id         UUID            NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    alt_text   VARCHAR(255)    NOT NULL,
    image_type image_type_enum NOT NULL,
    visible    BOOLEAN         NOT NULL,
    CONSTRAINT pk_images PRIMARY KEY (id)
);