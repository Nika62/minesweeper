--DROP ALL OBJECTS;

create table if not exists games(
    game_id VARCHAR NOT NULL PRIMARY KEY,
    width int not null,
    height int not null,
    mines_count int not null,
    completed boolean not null,
    free_cells int not null
    );

create table if not exists playing_fields(
     line_id bigint NOT NULL PRIMARY KEY auto_increment,
     game_id VARCHAR NOT NULL,
     col int not null,
     height int not null,
     letter int not null,
     CONSTRAINT fk_playing_fields_game_id FOREIGN KEY(game_id)
     REFERENCES games(game_id) ON DELETE CASCADE,
     UNIQUE (col, height)
);