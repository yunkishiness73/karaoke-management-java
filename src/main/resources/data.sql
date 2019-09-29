/*import seed data*/

INSERT INTO karaoke_management.event (type, base_price) VALUES ('Normal Days', 80000), ('Weekend', 100000);

INSERT INTO karaoke_management.room_type(type, base_rate, event_id) VALUES ('Phòng Nhỏ', 1.0, 1), ('Phòng Lớn', 1.5, 1), ('Phòng VIP', 2, 1);

INSERT INTO karaoke_management.period(name, base_rate, start_period, end_period) VALUES ('Morning', 1.0, '10:00', '17:59'), ('Evening', 1.5, '18:00', '23:59'), ('Midnight', 2.0, '00:00', '05:59');
--, 
INSERT INTO karaoke_management.item(name, unit, price) VALUES ('Nước suối các loại','chai',10000), ('Bia các loại','chai',25000), ('Snack các loại','bịch',10000), ('Trái cây dĩa','dĩa',120000), ('Khô bò','bịch',80000), ('Mực khô','con',100000), ('Khăn lạnh','miếng',10000), ('Bia tươi','thùng lớn',400000), ('Thuốc 555','gói',25000), ('Thuốc Hero','gói ',30000), ('Bia Corona','chai',200000), ('Khô gà','hộp',200000), ('Rượu vang Đà Lạt','chai',300000), ('Nước ngọt các loại','lon',15000);
--,
INSERT INTO karaoke_management.room(id, name, type_id, is_booking) VALUES (1,'N001',1,0),(2,'N002',1,0),(3,'N003',1,0),(4,'N004',1,0),(5,'N005',1,0),(6,'N006',1,0),(7,'N007',1,0),(8,'N008',1,0),(9,'L001',2,0),(10,'L002',2,0),(11,'L003',2,0),(12,'L004',2,0),(13,'V001',3,0),(14,'V002',3,0),(15,'V003',3,0),(17,'L005',2,0),(18,'L006',2,0),(19,'V004',3,0),(20,'V005',3,0),(21,'V006',3,0);

INSERT INTO karaoke_management.user(user_name, password, email, first_name, last_name) values ('kietnguyen7398@gmail.com','$2b$10$XRVLeCLpOWlWspAisTSx4ezcGnl8fS8l/JD4AWZeePXwRzR8bTHvm', 'yunkishine.ss@gmail.com','Kiệt', 'Nguyễn'), ('thachthaotran1997@gmail.com','$2b$10$3NGxMZ.k/wW7vQF3lvf6x.wRLlv/JYkkcfytsx/yimt7roeQZBEQ6', 'thachthaotran1997@gmail.com','Thảo Trân', 'Thạch');