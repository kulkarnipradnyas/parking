CREATE TABLE IF NOT EXISTS parkingLot_db.flyway_schema_history (
  installed_rank INTEGER NOT NULL,
  version varchar(50) DEFAULT NULL,
  description varchar(200) NOT NULL,
  type varchar(20) NOT NULL,
  script varchar(1000) NOT NULL,
  checksum INTEGER DEFAULT NULL,
  installed_by varchar(100) NOT NULL,
  installed_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  execution_time INTEGER NOT NULL,
  success BOOLEAN NOT NULL,
  PRIMARY KEY (installed_rank)
);
commit;

