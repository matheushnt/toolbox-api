CREATE TABLE users (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name       VARCHAR(165)    NOT NULL,
    email           VARCHAR(65)     NOT NULL,
    password        VARCHAR(255)    NOT NULL,
    role            VARCHAR(30)     NOT NULL,

    CONSTRAINT uk_users_email UNIQUE (email)
);

CREATE TABLE tools (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    title           VARCHAR(65)     NOT NULL,
    link            VARCHAR(2048)   NOT NULL,
    description     TEXT            NOT NULL,
    user_id         UUID            NOT NULL,

    CONSTRAINT fk_tools_user        FOREIGN KEY (user_id)   REFERENCES users(id)    ON DELETE CASCADE
);

CREATE TABLE tags (
    id              UUID            PRIMARY KEY DEFAULT gen_random_uuid(),
    name            VARCHAR(50)     NOT NULL UNIQUE
);

CREATE TABLE tools_tags (
    tool_id         UUID            NOT NULL,
    tag_id          UUID            NOT NULL,

    PRIMARY KEY (tool_id, tag_id),

    CONSTRAINT fk_tools_tags_tool   FOREIGN KEY (tool_id)   REFERENCES tools(id)    ON DELETE CASCADE,
    CONSTRAINT fk_tools_tags_tag    FOREIGN KEY (tag_id)    REFERENCES tags(id)     ON DELETE CASCADE
);
