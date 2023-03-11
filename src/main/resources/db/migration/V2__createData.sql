CREATE TABLE IF NOT EXISTS parkingLot_db.vehicle (
  id INT NOT NULL,
  vehicle_type VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parkingLot_db.fee_org (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parkingLot_db.organisation (
   id BIGINT NOT NULL AUTO_INCREMENT,
     name VARCHAR(255),
     motor_cycle_seat INT,
     car_seat INT,
     bus_seat INT,
     fee_org_id BIGINT,
       PRIMARY KEY (id),
       FOREIGN KEY (fee_org_id) REFERENCES parkingLot_db.fee_org(id)
);
CREATE TABLE IF NOT EXISTS parkingLot_db.fee_schema (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  fee_org_id BIGINT NOT NULL,
  `interval` VARCHAR(255) NOT NULL,
  fees INT NOT NULL,
  vehicle_type BIGINT,
    FOREIGN KEY (fee_org_id) REFERENCES parkingLot_db.fee_org(id)
);



INSERT IGNORE INTO parkingLot_db.vehicle(id,vehicle_type) VALUES (1,'MOTORCYCLE');
INSERT IGNORE INTO parkingLot_db.vehicle(id,vehicle_type) VALUES (2,'BUS');
INSERT IGNORE INTO parkingLot_db.vehicle(id,vehicle_type) VALUES (3,'CAR');

-- fee model by category
INSERT IGNORE INTO parkingLot_db.fee_org (id, name) VALUES (1,"Mall");
INSERT  IGNORE INTO parkingLot_db.fee_org (id, name) VALUES (2,"Stadium");
INSERT IGNORE INTO parkingLot_db.fee_org (id, name) VALUES (3,"Airport");



INSERT IGNORE INTO parkingLot_db.fee_schema (id, fee_org_id, `interval`, fees, vehicle_type)
VALUES
    (1,1, '[0, 1]', 10,1 ),
    (2,1,'[0, 1]', 20,3),
    (3,1, '[0, 1]', 50, 2),
    (4,2, '[0, 4]', 30,1 ),
    (5,2,'[4, 12]', 60,1),
    (6,2, '[12, Infinity]', 100, 1),
    (7,2, '[0, 4]', 60,3),
    (8,2, '[4, 12]', 120, 3),
    (9,2, '[12, Infinity]', 200, 3),
    (10,3, '[0, 1]', 0,1 ),
    (11,3,'[1, 8]', 40,1),
    (12,3,'[1, 8]', 80,1),
    (13,3, '[8, 24]', 60, 1),
    (14,3, '[0, 12]', 60,3),
    (15,3, '[12, 24]', 80, 3),
    (16,3, '[0, 12]', 100,3);




INSERT IGNORE INTO parkingLot_db.organisation (id,name, motor_cycle_seat, car_seat, bus_seat, fee_org_id)
VALUES (1,'Small motorcycle/scooter parking lot', 2, 0, 0, 1);

INSERT IGNORE INTO parkingLot_db.organisation (id,name, motor_cycle_seat, car_seat, bus_seat, fee_org_id)
VALUES (2,'Mall parking lot', 100, 80, 10, 1);

INSERT IGNORE INTO parkingLot_db.organisation (id,name, motor_cycle_seat, car_seat, bus_seat, fee_org_id)
VALUES (3,'Stadium Parking Lot', 1000, 1500, 0, 2);

INSERT IGNORE INTO parkingLot_db.organisation (id,name, motor_cycle_seat, car_seat, bus_seat, fee_org_id)
VALUES (4,'Airport Parking Lot', 200, 500, 100, 3);

CREATE TABLE IF NOT EXISTS parkinglot_db.parking_lot (
    id BIGINT NOT NULL AUTO_INCREMENT,
    organisation_id BIGINT,
    vehicle_type VARCHAR(255),
    fee_schema_id BIGINT,
    exit_time DATETIME,
    entry_time DATETIME,
    parking_spot_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (organisation_id) REFERENCES parkinglot_db.organisation(id),
    FOREIGN KEY (fee_schema_id) REFERENCES parkinglot_db.fee_schema(id)
);

commit;
