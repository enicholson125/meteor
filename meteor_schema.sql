CREATE TABLE TextSnippets(
    ID TEXT NOT NULL PRIMARY KEY,
    SNIPPET_TYPE TEXT NOT NULL,
    SNIPPET TEXT NOT NULL,
    NEXT_SNIPPET TEXT,
    CHOICES TEXT,
    ANIMAL_ID TEXT,
    FOREIGN KEY (ANIMAL_ID) REFERENCES AnimalTypes(ID)
);

CREATE TABLE AnimalTypes(
    ID TEXT NOT NULL PRIMARY KEY,
    ANIMAL_NAME TEXT NOT NULL,
    ANIMAL_DESCRIPTION TEXT,
    ANIMAL_IMAGE TEXT
);

CREATE TABLE AdoptedAnimals(
    ID TEXT NOT NULL PRIMARY KEY,
    ANIMAL_ID TEXT NOT NULL,
    ANIMAL_NAME TEXT,
    FOREIGN KEY (ANIMAL_ID) REFERENCES AnimalTypes(ID)
);

CREATE TABLE SeenText(
    TEXT_INDEX INT NOT NULL PRIMARY KEY,
    SNIPPET_ID TEXT NOT NULL,
    FOREIGN KEY (SNIPPET_ID) REFERENCES TextSnippets(ID)
);