INSERT INTO food_ordering.food_category
VALUES (1, '快餐', 'fast stuff', 'fast'),
       (2, '小食', 'crisps and chocolate...', 'snakes'),
       (3, '水果', 'apple and banana..', 'fruits'),
       (4, '饮品', 'cola and soda...', 'drinks')
;

INSERT INTO food_ordering.food
VALUES (1, '西瓜', 'walter melon', 10.0, '', NOW()),
       (2, '香蕉', 'banana', 12.0, '', NOW()),
       (3, '草莓', 'strawberry', 30.0, '', NOW()),
       (4, '苹果', 'apple', 2.6, '', NOW()),
       (5, '桃子', 'peach', 9.8, '', NOW()),
       (6, '荔枝', 'litchi', 20.0, '', NOW()),
       (7, '樱桃', 'cherry', 9.9, '', NOW()),
       (8, '树莓', 'raspberry', 15.0, '', NOW()),
       (9, '榴莲', 'durian', 49.0, '', NOW()),
       (10, '梨子', 'pear', 6.0, '', NOW()),
       (11, '葡萄', 'grape', 20.6, '', NOW()),
       (12, '甘蔗', 'sugar cane', 9.8, '', NOW()),
       (13, '桂圆', 'longan', 20.6, '', NOW()),
       (14, '山竹', 'mangosteen', 20.99, '', NOW()),
       (15, '杨梅', 'bayberry', 18.76, '', NOW()),
       (16, '火龙果', 'pitaya', 5.0, '', NOW()),
       (17, '哈密瓜', 'cantaloupe', 12.7, '', NOW()),

       (18, '帕尼尼', 'panini', 12, '', NOW()),

       (19, '鸡排', 'chicken chop', 12, '', NOW()),
       (20, '地瓜干', 'dried sweet potatoes', 5, '', NOW()),

       (21, '玉米汁', 'corn juice', 12, '', NOW()),
       (22, '波波奶茶', 'bobo milk tea', 7, '', NOW())
;

INSERT INTO food_ordering.food_info(food_ordering.food_info.cat_id, food_ordering.food_info.food_id)
VALUES (3, 1),
       (3, 2),
       (3, 3),
       (3, 4),
       (3, 5),
       (3, 6),
       (3, 7),
       (3, 8),
       (3, 9),
       (3, 10),
       (3, 11),
       (3, 12),
       (3, 13),
       (3, 14),
       (3, 15),
       (3, 16),
       (3, 17),
       (1, 18),
       (2, 19),
       (2, 20),
       (4, 21),
       (4, 22)
;

INSERT INTO food_ordering.user (user_id, username, password, salt)
VALUES (1, 'mike@qq.com', '$2a$10$GCmxbIUHLk3p8R.r4heSt.FhaGx4dbHc2Q4wvGL8ngdncbgPO1X9i', null);

INSERT INTO food_ordering.user_info (info_id, user_id, email, phone_number)
VALUES (1, 1, 'mike@qq.com', '');

INSERT INTO food_ordering.user_authz (authz_id, user_id, role)
VALUES (1, 1, 'USER');
