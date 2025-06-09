CREATE TABLE dish
(
    id            INT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255)       NULL,
    code          VARCHAR(255)       NULL,
    `description` VARCHAR(255)       NULL,
    image_url     VARCHAR(255)       NULL,
    CONSTRAINT pk_dish PRIMARY KEY (id)
);

CREATE TABLE dish_dish_hint
(
    dish_hint_id INT NOT NULL,
    dish_id      INT NOT NULL,
    CONSTRAINT pk_dish_dish_hint PRIMARY KEY (dish_hint_id, dish_id)
);

ALTER TABLE dish_dish_hint
    ADD CONSTRAINT fk_disdishin_on_dish FOREIGN KEY (dish_id) REFERENCES dish (id);

ALTER TABLE dish_dish_hint
    ADD CONSTRAINT fk_disdishin_on_dish_hint FOREIGN KEY (dish_hint_id) REFERENCES dish_hint (id);