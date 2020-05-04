CREATE TABLE `dictionary_tags` (
  `id`                BIGINT       AUTO_INCREMENT,
  `dictionary_id`     BIGINT       NOT NULL,
  `tag_id`            BIGINT       NOT NULL,
  `create_at`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at`         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`),
  FOREIGN KEY (`dictionary_id`) REFERENCES `dictionaries` (`id`),
  UNIQUE (`tag_id`, `dictionary_id`)
) ENGINE = InnoDB;
