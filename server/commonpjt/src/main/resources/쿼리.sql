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

insert into user
values ();

insert into user
values ();

insert into user
values ();

insert into user
values ();

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 원평동 1008-1', '경북 구미시 구미중앙로 76', '구미역', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 송정동 50', '경북 구미시 송정대로 55', '구미시청', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 공단동 207', '경북 구미시 수출대로 112', '구미소방서', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경기 성남시 분당구 구미동 227', '경기 성남시 분당구 구미로 130', '구미동행정복지센터', true, true);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '역장실', '동향', '4층', 10, '','100만원','원룸', '10평','월세',1,1,1);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('불가', '시장실', '북향', '4층', 10, '','100만원','원룸', '10평','월세',2,2,2);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '서장실', '남향', '4층', 10, '','100만원','원룸', '10평','월세',3,3,3);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('불가', '동장실', '남향', '4층', 10, '','100만원','원룸', '10평','월세',4,4,4);

insert into product (product_animal, product_detail, product_direction, product_floor, product_manage_cost, product_photo, product_price, product_rooms, product_size, product_type, building_index, option_index, seller_index)
VALUES ('고양이', '사무실', '북향', '4층', 20, '','50만원','원룸', '20평','월세',1,5,1);

