INSERT INTO sex(id, name) VALUES (1, 'undefined'), (2, 'feminine'), (3, 'masculine');

INSERT INTO type(id, name) VALUES (1, 'вампир'), (2, 'оборотень'), (3, 'человек'), (4, 'энергетический вампир');

INSERT INTO location(id, lat, lon, name, is_place_of_living) VALUES
(1, -41.2943296, 174.7856846 , 'Genghis Khan House', true),
(2, -41.2936503, 174.8201893, 'Chocolate Fish Cafe', false),
(3, -41.2936419, 174.7818361, 'The Welsh Dragon Bar', false),
(4, -41.2936443, 174.735631, 'Баня Эрика', false);

INSERT INTO favor(id, sex_id, age_min, age_max) VALUES
(1, 2, 1, 100), (2, 3, 30, 40);

INSERT INTO "group"(id, location_id, admin_id, name) VALUES (1, 2, null, 'МЫ ВАМПИРЫ А ВЫ ЛОХИ');

INSERT INTO character(id, type_id, name, birthday, history, sex_id, place_of_living_id, location_id, "group", favor_id) VALUES
(1, 1, 'Виаго', '1636-03-10', 'Вампир трехсот семидесяти девяти лет от роду, родом из Германии. Денди, помешанный на чистоте и порядке.', 3, 1, 1, 1, 2),
(2, 1, 'Афанас', '1-01-01', 'У Афанаса нет гениталий, но по словам Ласло и Надьи, которые имели с ним связь, он является превосходным любовником.', 1, null, 2, 1, 1);

INSERT INTO character(id, type_id, name, birthday, history, sex_id, place_of_living_id, location_id) VALUES
(3, 3, 'Эрик Баня', '1968-08-09', 'Наиболее известные фильмы с участием Эрика Бани — «Чёрный ястреб» (2001), «Халк» (2003), «Троя» (2004), «Звёздный путь» (2009).', 3, 4, 4),
(4, 4, 'Николь Мэри Кидман', '1967-07-20', 'Австралийская и американская актриса, певица, продюсер и посол доброй воли ЮНИСЕФ', 2, 4, 2),
(5, 3, 'Гильермо дель Торо Гомес', '1994-10-09', 'Режиссёр', 3, 1, 4);

UPDATE "group"
SET "admin_id" = 1
WHERE id = 1;

INSERT INTO vampire_to_servant(servant_id, vampire_id) VALUES
(5, 1), (3, 2);

INSERT INTO murder(id, killer_id, victim, description) VALUES
(1, 3, 2, 'Гильермо мог случайно убить Афанаса. Тот слегка загорел на солнце.'),
(2, 3, 2, 'Гильермо снова пытался убить Афанаса. Тот поджарился на солнце.');

INSERT INTO court(id, verdict, date, defendant_id, group_id, location_id) VALUES
(1, 'Гильермо был отпущен на волю.', '2022-10-17', 5, 1, 1);