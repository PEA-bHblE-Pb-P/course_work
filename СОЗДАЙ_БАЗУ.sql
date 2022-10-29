CREATE TABLE "character" (
  "id" SERIAL PRIMARY KEY,
  "type_id" int,
  "name" varchar,
  "birthday" date,
  "history" text,
  "sex_id" int,
  "place_of_living_id" int,
  "location_id" int,
  "group" int,
  "favor_id" int
);

CREATE TABLE "type" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "sex" (
  "id" SERIAL PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "murder" (
  "id" SERIAL PRIMARY KEY,
  "killer_id" int,
  "victim" int,
  "description" varchar,
  "date" timestamp
);

CREATE TABLE "location" (
  "id" SERIAL PRIMARY KEY,
  "lat" int,
  "lon" int,
  "name" varchar,
  "is_place_of_living" boolean
);

CREATE TABLE "vampire_to_servant" (
  "servant_id" int NOT NULL,
  "vampire_id" int NOT NULL
);

CREATE TABLE "group" (
  "id" SERIAL PRIMARY KEY,
  "location_id" int,
  "admin_id" int,
  "name" varchar
);

CREATE TABLE "location_history" (
  "character_id" int NOT NULL,
  "location_id" int,
  "visit_time" timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE "court" (
  "id" SERIAL PRIMARY KEY,
  "verdict" varchar,
  "date" date,
  "defendant_id" int,
  "group_id" int,
  "location_id" int
);

CREATE TABLE "passes" (
  "character_id" int NOT NULL,
  "location_id" int NOT NULL
);

CREATE TABLE "favor" (
  "id" SERIAL PRIMARY KEY,
  "sex_id" int,
  "age_min" int,
  "age_max" int
);

CREATE TABLE "drink_blood" (
  "id" SERIAL PRIMARY KEY,
  "blood_amount_percentage" INT,
  "character_id" INT REFERENCES "character",
  "drinker_id" INT REFERENCES "character"
);

ALTER TABLE "character" ADD FOREIGN KEY ("type_id") REFERENCES "type" ("id");

ALTER TABLE "character" ADD FOREIGN KEY ("sex_id") REFERENCES "sex" ("id");

ALTER TABLE "character" ADD FOREIGN KEY ("place_of_living_id") REFERENCES "location" ("id");

ALTER TABLE "character" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id");

ALTER TABLE "character" ADD FOREIGN KEY ("group") REFERENCES "group" ("id");

ALTER TABLE "character" ADD FOREIGN KEY ("favor_id") REFERENCES "favor" ("id");

ALTER TABLE "murder" ADD FOREIGN KEY ("killer_id") REFERENCES "character" ("id");

ALTER TABLE "murder" ADD FOREIGN KEY ("victim") REFERENCES "character" ("id");

ALTER TABLE "vampire_to_servant" ADD FOREIGN KEY ("servant_id") REFERENCES "character" ("id");

ALTER TABLE "vampire_to_servant" ADD FOREIGN KEY ("vampire_id") REFERENCES "character" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id");

ALTER TABLE "group" ADD FOREIGN KEY ("admin_id") REFERENCES "character" ("id");

ALTER TABLE "location_history" ADD FOREIGN KEY ("character_id") REFERENCES "character" ("id");

ALTER TABLE "location_history" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id");

ALTER TABLE "court" ADD FOREIGN KEY ("defendant_id") REFERENCES "character" ("id");

ALTER TABLE "court" ADD FOREIGN KEY ("group_id") REFERENCES "group" ("id");

ALTER TABLE "court" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id");

ALTER TABLE "passes" ADD FOREIGN KEY ("character_id") REFERENCES "character" ("id");

ALTER TABLE "passes" ADD FOREIGN KEY ("location_id") REFERENCES "location" ("id");

ALTER TABLE "favor" ADD FOREIGN KEY ("sex_id") REFERENCES "sex" ("id");

ALTER TABLE "passes" ADD PRIMARY KEY ("character_id", "location_id");

ALTER TABLE "location_history" ADD PRIMARY KEY ("character_id", "visit_time");

ALTER TABLE "vampire_to_servant" ADD CONSTRAINT unique_vampire_servant UNIQUE ("vampire_id", "servant_id");

ALTER TABLE "favor" ADD CONSTRAINT unique_favor UNIQUE("age_max", "age_min", "sex_id");

ALTER TABLE "character" ADD CONSTRAINT unique_story UNIQUE (history);