DROP TABLE IF EXISTS memo cascade ;
DROP TABLE IF EXISTS tag cascade ;
DROP TABLE IF EXISTS board cascade;
DROP TABLE IF EXISTS memo_theme cascade ;
DROP TABLE IF EXISTS board_theme cascade ;
DROP TABLE IF EXISTS theme_category cascade ;
DROP TABLE IF EXISTS image_info cascade ;

CREATE TABLE board
(
    board_id BIGINT NOT NULL auto_increment,
    board_theme_id BIGINT NOT NULL,
    board_name               VARCHAR(20) NOT NULL,
    board_description        VARCHAR(50) NULL,
    board_code               VARCHAR(36) NOT NULL,
    board_writing_start_time TIMESTAMP    NOT NULL,
    board_writing_end_time   TIMESTAMP    NOT NULL,
    board_open_start_time    TIMESTAMP    NOT NULL,
    board_open_end_time      TIMESTAMP    NOT NULL,
    board_password           VARCHAR(16) NOT NULL,
    board_open_type          VARCHAR(10) NOT NULL,
    board_views INT NOT NULL,
    primary key (board_id)
);


CREATE TABLE memo
(
    memo_id BIGINT NOT NULL auto_increment,
    board_id BIGINT NOT NULL,
    memo_theme_id BIGINT NOT NULL,
    memo_content VARCHAR(100) NOT NULL,
    primary key (memo_id)
);


CREATE TABLE tag
(
    tag_id BIGINT NOT NULL auto_increment,
    board_id BIGINT NOT NULL,
    tag_value VARCHAR(20) NOT NULL,
    primary key (tag_id)
);


CREATE TABLE board_theme
(
    board_theme_id BIGINT NOT NULL auto_increment,
    theme_category_id BIGINT NOT NULL,
    board_theme_name             VARCHAR(20) NOT NULL,
    board_theme_background_type  VARCHAR(10) NOT NULL,
    board_theme_background_image_id BIGINT NULL,
    board_theme_background_color VARCHAR(7)  NULL,
    board_theme_font             VARCHAR(20) NULL,
    primary key (board_theme_id)
);


CREATE TABLE memo_theme
(
    memo_theme_id BIGINT NOT NULL auto_increment,
    board_theme_id BIGINT NOT NULL,
    memo_theme_background_type  VARCHAR(10) NOT NULL,
    memo_theme_background_image_id BIGINT NULL,
    memo_theme_background_color VARCHAR(7)  NULL,
    memo_theme_text_color       VARCHAR(7)  NOT NULL,
    primary key (memo_theme_id)
);


CREATE TABLE theme_category
(
    theme_category_id BIGINT NOT NULL auto_increment,
    theme_category_name VARCHAR(50) NOT NULL,
    primary key (theme_category_id)
);


CREATE TABLE image_info
(
    image_info_id BIGINT NOT NULL auto_increment,
    image_uuid          VARCHAR(36)  NOT NULL,
    image_original_name VARCHAR(255) NOT NULL,
    image_saved_name    VARCHAR(255) NOT NULL,
    image_extension     VARCHAR(6)   NOT NULL,
    image_path          VARCHAR(255) NOT NULL,
    primary key (image_info_id)
);

ALTER TABLE board
    ADD CONSTRAINT FK_board_theme_TO_board_1 FOREIGN KEY (
                                                          board_theme_id
        )
        REFERENCES board_theme (
                                board_theme_id
            );

ALTER TABLE memo
    ADD CONSTRAINT FK_board_TO_memo_1 FOREIGN KEY (
                                                   board_id
        )
        REFERENCES board (
                          board_id
            );

ALTER TABLE memo
    ADD CONSTRAINT FK_memo_theme_TO_memo_1 FOREIGN KEY (
                                                        memo_theme_id
        )
        REFERENCES memo_theme (
                               memo_theme_id
            );

ALTER TABLE tag
    ADD CONSTRAINT FK_board_TO_tag_1 FOREIGN KEY (
                                                  board_id
        )
        REFERENCES board (
                          board_id
            );

ALTER TABLE board_theme
    ADD CONSTRAINT FK_theme_category_TO_board_theme_1 FOREIGN KEY (
                                                                   theme_category_id
        )
        REFERENCES theme_category (
                                   theme_category_id
            );

ALTER TABLE board_theme
    ADD CONSTRAINT FK_image_info_TO_board_theme_1 FOREIGN KEY (
                                                               board_theme_background_image_id
        )
        REFERENCES image_info (
                               image_info_id
            );

ALTER TABLE memo_theme
    ADD CONSTRAINT FK_board_theme_TO_memo_theme_1 FOREIGN KEY (
                                                               board_theme_id
        )
        REFERENCES board_theme (
                                board_theme_id
            );

ALTER TABLE memo_theme
    ADD CONSTRAINT FK_image_info_TO_memo_theme_1 FOREIGN KEY (
                                                              memo_theme_background_image_id
        )
        REFERENCES image_info (
                               image_info_id
            );