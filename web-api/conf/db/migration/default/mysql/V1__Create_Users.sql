CREATE TABLE `users` (
  `id`        BIGINT                AUTO_INCREMENT,
  `name`      VARCHAR(64)  NOT NULL,
  `email`     VARCHAR(255) NOT NULL,
  `password`  VARCHAR(64)  NOT NULL,
  `admin`  BOOLEAN,
  `create_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE (`email` ASC)
) ENGINE = InnoDB;
