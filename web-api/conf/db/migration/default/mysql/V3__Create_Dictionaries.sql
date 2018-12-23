CREATE TABLE `dictionaries` (
  `id`        BIGINT       AUTO_INCREMENT,
  `user_id`   BIGINT       NOT NULL,
  `title`     VARCHAR(64)  NOT NULL,
  `content`   VARCHAR(255) NOT NULL,
  `create_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB;

CREATE INDEX `dictionaries_user_id_idx` ON `users` (`id`)
