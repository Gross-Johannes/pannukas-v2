CREATE TABLE dishes
(
    id          UUID         NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price       DECIMAL      NOT NULL,
    CONSTRAINT pk_dishes PRIMARY KEY (id)
);