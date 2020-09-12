CREATE TABLE text_snippets(
    id TEXT NOT NULL PRIMARY KEY,
    snippet_type TEXT NOT NULL,
    snippet TEXT NOT NULL,
    next_snippets TEXT NOT NULL,
    choices TEXT NOT NULL,
    animal_id TEXT,
    FOREIGN KEY (animal_id) REFERENCES animal_types(id)
);

CREATE TABLE animal_types(
    id TEXT NOT NULL PRIMARY KEY,
    animal_name TEXT NOT NULL,
    animal_description TEXT NOT NULL,
    animal_image TEXT NOT NULL
);

CREATE TABLE adopted_animals(
    id INT NOT NULL PRIMARY KEY,
    animal_id TEXT NOT NULL,
    animal_name TEXT,
    FOREIGN KEY (animal_id) REFERENCES animal_types(id)
);

CREATE TABLE text_history(
    text_index INT NOT NULL PRIMARY KEY,
    text_description TEXT NOT NULL,
    snippet_id TEXT NOT NULL,
    FOREIGN KEY (snippet_id) REFERENCES text_snippets(id)
);
