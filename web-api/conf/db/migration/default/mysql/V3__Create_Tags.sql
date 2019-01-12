CREATE TABLE `tags` (
  `id`                BIGINT       AUTO_INCREMENT,
  `title`             VARCHAR(64)  NOT NULL,
  `create_at`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE = InnoDB;
