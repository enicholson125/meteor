CREATE TABLE text_snippets(
    id TEXT NOT NULL PRIMARY KEY,
    snippet_type TEXT NOT NULL,
    snippet TEXT NOT NULL,
    next_snippets TEXT,
    choices TEXT,
    animal_id TEXT,
    FOREIGN KEY (animal_id) REFERENCES animal_types(id)
);

CREATE INDEX text_snippet_index ON text_snippets (id);

CREATE TABLE animal_types(
    id TEXT NOT NULL PRIMARY KEY,
    animal_name TEXT NOT NULL,
    animal_description TEXT,
    animal_image TEXT
);

CREATE TABLE adopted_animals(
    id TEXT NOT NULL PRIMARY KEY,
    animal_id TEXT NOT NULL,
    animal_name TEXT,
    FOREIGN KEY (animal_id) REFERENCES animal_types(id)
);

CREATE TABLE seen_text(
    text_index INT NOT NULL PRIMARY KEY,
    snippet_id TEXT NOT NULL,
    FOREIGN KEY (snippet_id) REFERENCES text_snippets(id)
);
