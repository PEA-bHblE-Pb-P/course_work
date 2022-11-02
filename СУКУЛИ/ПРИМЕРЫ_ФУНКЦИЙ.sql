SELECT go_to_location(1, 'Genghis Khan Hous_');

SELECT go_to_servant_by_id(1, 5);

SELECT * FROM people_nearby(1);

SELECT go_to_bar(5, 2);

SELECT drink_blood(2, 4, 20);

BEGIN;
SELECT hunter_go_to_for_fight(6, 1);
SELECT * FROM character WHERE id < 7;
ROLLBACK;


BEGIN;
SELECT hunter_go_to_for_fight(6, 1);
SELECT * FROM character WHERE id < 7;
ROLLBACK;

SELECT go_to_location_by_id(2, -1);
BEGIN;
SELECT hunter_go_to_for_fight(6, -1);
SELECT * FROM character WHERE id < 7;
ROLLBACK;

select kill(1, 6, 'УПС');
