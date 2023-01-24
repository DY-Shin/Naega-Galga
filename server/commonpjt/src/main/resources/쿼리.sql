select * from building;

select * from product;

select * from options;

insert into options(option_air_conditioner)
VALUES (true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 원평동 1008-1', '경북 구미시 구미중앙로 76', '구미역', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('경북 구미시 송정동 50', '경북 구미시 송정대로 55', '구미시청', true, true);

insert into building (building_address, building_road_address, building_name, building_elevator, building_parking)
VALUES ('공단동 207', '경북 구미시 수출대로 112', '구미소방서', true, true);