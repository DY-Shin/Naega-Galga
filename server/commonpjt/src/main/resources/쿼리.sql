select * from building;

select * from product;

select * from options;

select * from user;

insert into options(option_air_conditioner)
VALUES (true);

insert into options(option_closet)
VALUES (true);

insert into options(option_fridge)
VALUES (true);

insert into options(option_bed)
VALUES (true);

insert into options(option_bed, option_fridge)
VALUES (true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 진평동 303', '경북 구미시 진평4길 18', '성민에코빌', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 진평동 333-3', '경북 구미시 진평5길 27-1', '명성에버빌2', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 진평동 279-10', '경북 구미시 진평2길 9-10', '대영빌라', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 송정동 474-5', '경북 구미시 송정대로6길 28', '송정파크빌', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 옥계동 741-6', '경북 구미시 산호대로31길 12-67', '그린로즈', true, true);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '302호', '동향', '3층', 10, '','100/20','원룸', '10평','월세',1,1,1);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('불가', '202호', '북향', '2층', 10, '','200/28','원룸', '10평','월세',2,2,1);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '106호', '남향', '1층', 10, '','200/25','원룸', '10평','월세',3,3,1);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('불가', '402호', '남향', '4층', 10, '','300/38','투룸', '15평','월세',4,4,1);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '203호', '북향', '2층', 20, '','5000','쓰리룸', '24평','전세',5,5,1);

select * from meeting;

insert into meeting(meeting_url, reserve_at, owner_index, product_index)
values ('www.naver.com', now(), 1,2);

select * from explanation;

select * from product;

select * from building;