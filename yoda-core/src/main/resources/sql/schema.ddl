drop table if exists contact_us;

drop table if exists content;

drop table if exists content_image;

drop table if exists country;

drop table if exists feedback;

drop table if exists home_page;

drop table if exists release_;

drop table if exists menu;

drop table if exists poll_detail;

drop table if exists poll_header;

drop table if exists section;

drop table if exists site;

drop table if exists state;

drop table if exists template;

drop table if exists user_;

drop table if exists authority;

drop table if exists user_site;

create table contact_us
(
    id            INT(4)       not null AUTO_INCREMENT,
    site_id       INT(4),
    name          varchar(50)  not null comment '',
    address_line1 varchar(30)  not null comment '',
    address_line2 varchar(30)  not null comment '',
    city_name     varchar(25)  not null comment '',
    zip_code      varchar(10)  not null comment '',
    email         varchar(50)  not null comment '',
    phone         varchar(20) comment '',
    description   varchar(255) not null comment '',
    seq_num       integer      not null comment '',
    active        boolean      not null comment '',
    update_by     INT          not null comment '',
    update_date   datetime     not null comment '',
    create_by     INT          not null comment '',
    create_date   datetime     not null comment '',
    primary key (id)
) comment ='';

create table content
(
    id                bigint       not null AUTO_INCREMENT,
    category_id       INT comment '',
    site_id           INT(4),
    natural_key       varchar(255) not null comment '',
    title             varchar(128) not null comment '',
    short_description text         not null comment '',
    description       text         not null comment '',
    page_title        varchar(255) not null comment '',
    hit_counter       INT          not null comment '',
    publish_date      date         not null comment '',
    expire_date       date         not null comment '',
    published         boolean      not null comment '',
    feature_data      boolean      not null comment '',
    update_by         bigint       not null comment '',
    update_date       datetime     not null comment '',
    create_by         bigint       not null comment '',
    create_date       datetime     not null comment '',
    section_id        INT comment '',
    featured_image    varchar(255),
    score             INT          not null,
    primary key (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
) comment ='';

create table content_contributor
(
    id                  INT         not null AUTO_INCREMENT PRIMARY KEY,
    content_id          bigint      not null,
    user_id             INT         not null,
    username            varchar(50) not null,
    profile_photo_small varchar(255) DEFAULT NULL,
    version             VARCHAR(30),
    approved            boolean     not null,
    create_by           INT         NOT NULL,
    create_date         DATETIME    NOT NULL,
    update_by           INT         NOT NULL,
    update_date         DATETIME    NOT NULL,
    INDEX (id)
) engine = InnoDB;

CREATE TABLE content_user_rate
(
    id          INT      NOT NULL AUTO_INCREMENT,
    content_id  INT,
    score       INT,
    create_by   INT      NOT NULL,
    create_date DATETIME NOT NULL,
    PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE item
(
    id           INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    brand_id     INT,
    category_id  INT,
    content_id   bigint,
    create_by    INT      NOT NULL,
    create_date  DATETIME NOT NULL,
    description  VARCHAR(5000),
    hit_counter  INT,
    image_path   VARCHAR(255),
    level        VARCHAR(20),
    name         VARCHAR(100),
    buy_links    VARCHAR(2000),
    extra_fields VARCHAR(2000),
    price        INT,
    rating       INT,
    site_id      INT(4),
    update_by    INT      NOT NULL,
    update_date  DATETIME NOT NULL,
    INDEX (name)
) engine = InnoDB;

CREATE TABLE brand
(
    id          INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    country     varchar(30),
    company     VARCHAR(30),
    name        VARCHAR(30),
    description VARCHAR(2000),
    found_date  DATETIME,
    kind        VARCHAR(30),
    image_path  VARCHAR(255),
    hit_counter INT,
    score       INT,
    create_by   bigint   NOT NULL,
    create_date DATETIME NOT NULL,
    update_by   bigint   NOT NULL,
    update_date DATETIME NOT NULL,
    INDEX (name)
) engine = InnoDB;

create table content_brand
(
    id          bigint   not null AUTO_INCREMENT PRIMARY KEY,
    content_id  bigint   not null,
    brand_id    INT      not null,
    brand_name  VARCHAR(30),
    brand_logo  VARCHAR(50),
    description VARCHAR(5000),
    create_by   bigint   NOT NULL,
    create_date DATETIME NOT NULL,
    update_by   bigint   NOT NULL,
    update_date DATETIME NOT NULL,
    INDEX (id)
);

create table brand_category
(
    brand_id    INT not null,
    category_id INT not null,
    primary key (brand_id, category_id)
);

CREATE TABLE comment
(
    id          INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    content_id  bigint,
    user_id     INT      NOT NULL,
    create_date DATETIME NOT NULL,
    description VARCHAR(2000),
    rating      INT,
    site_id     INT(4),
    FOREIGN KEY (content_id) REFERENCES content (id)
) engine = InnoDB;

create table term
(
    id          bigint       not null AUTO_INCREMENT PRIMARY KEY,
    title       varchar(100) not null,
    description VARCHAR(2000),
    content_id  bigint,
    category_id INT,
    create_by   bigint       NOT NULL,
    create_date DATETIME     NOT NULL,
    update_by   bigint       NOT NULL,
    update_date DATETIME     NOT NULL
) engine = InnoDB;

create table solution
(
    id          bigint       not null AUTO_INCREMENT PRIMARY KEY,
    title       varchar(100) not null,
    description VARCHAR(1000),
    create_by   bigint       NOT NULL,
    create_date DATETIME     NOT NULL,
    update_by   bigint       NOT NULL,
    update_date DATETIME     NOT NULL
) engine = InnoDB;

create table solution_item
(
    id          bigint   not null AUTO_INCREMENT PRIMARY KEY,
    solution_id bigint   not null,
    item_id     bigint   not null,
    description VARCHAR(1000),
    create_by   bigint   NOT NULL,
    create_date DATETIME NOT NULL,
    update_by   bigint   NOT NULL,
    update_date DATETIME NOT NULL
);

create table content_image
(
    image_id     bigint      not null,
    site_id      bigint,
    seq_num      INT         not null comment '',
    content_type varchar(20) not null comment '',
    image_name   varchar(40) not null comment '',
    image_value  longblob    not null comment '',
    height       INT         not null comment '',
    width        INT         not null comment '',
    update_by    INT         not null comment '',
    update_date  datetime    not null comment '',
    create_by    INT         not null comment '',
    create_date  datetime    not null comment '',
    content_id   bigint,
    primary key (image_id)
) comment ='';

create table country
(
    country_id   int         not null AUTO_INCREMENT PRIMARY KEY,
    country_name varchar(40) not null comment '',
    create_by    varchar(20) not null comment '',
    create_date  datetime    not null comment '',
    site_id      int,
    update_by    varchar(20) not null comment '',
    update_date  datetime    not null comment ''
);

create table category
(
    id          INT         not null AUTO_INCREMENT,
    name        varchar(50) not null,
    parent      INT         not null comment '',
    description text        not null comment '',
    update_by   bigint      not null comment '',
    update_date datetime    not null comment '',
    create_by   bigint      not null comment '',
    create_date datetime    not null comment '',
    primary key (id)
) comment ='';

create table feedback
(
    id          bigint      not null AUTO_INCREMENT,
    username    varchar(50) not null,
    email       varchar(50) not null comment '',
    phone       varchar(20) not null comment '',
    description text        not null comment '',
    create_date datetime    not null comment '',
    primary key (id)
) comment ='';

CREATE TABLE file
(
    id              bigint              NOT NULL AUTO_INCREMENT,
    file_name       varchar(255)        NULL DEFAULT NULL,
    file_path       varchar(255)        NULL DEFAULT NULL,
    file_small_path varchar(255)        NULL DEFAULT NULL,
    file_full_path  varchar(255)        NULL DEFAULT NULL,
    file_type       varchar(255)        NULL DEFAULT NULL,
    content_type    INT                 NULL DEFAULT NULL,
    content_id      INT                      DEFAULT NULL,
    suffix          varchar(20)         NULL DEFAULT NULL,
    size            bigint(20) UNSIGNED NULL DEFAULT NULL,
    width           int(10) UNSIGNED    NULL DEFAULT NULL,
    height          int(10) UNSIGNED    NULL DEFAULT NULL,
    create_by       bigint              NOT NULL,
    create_date     DATETIME            NOT NULL,
    update_by       bigint              NOT NULL,
    update_date     DATETIME            NOT NULL,
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci
  ROW_FORMAT = Compact;

create table home_page
(
    home_page_id INT      not null,
    site_id      INT(4),
    seq_num      INT      not null comment '',
    feature_data boolean  not null comment '',
    update_by    bigint   not null comment '',
    update_date  datetime not null comment '',
    create_by    bigint   not null comment '',
    create_date  datetime not null comment '',
    content_id   bigint,
    primary key (home_page_id)
) comment ='';

create table release_
(
    release_id    INT not null AUTO_INCREMENT,
    create_date   datetime,
    modified_date datetime,
    build_number  INTEGER,
    verified      BOOLEAN,
    primary key (release_id)
);

create table menu
(
    menu_id            INT(4)       not null,
    site_id            INT(4),
    set_name           varchar(20)  not null comment '',
    title              varchar(20)  not null comment '',
    name               varchar(20)  not null comment '',
    seq_num            INT          not null comment '',
    parent_id          INT(4) comment '',
    menu_type          varchar(5)   not null comment '',
    menu_url           varchar(255) not null comment '',
    menu_window_target varchar(20)  not null comment '',
    menu_window_mode   varchar(255) not null comment '',
    published          boolean      not null comment '',
    update_by          INT          not null comment '',
    update_date        datetime     not null comment '',
    create_by          INT          not null comment '',
    create_date        datetime     not null comment '',
    section_id         INT,
    content_id         bigint,
    primary key (menu_id)
) comment ='';

CREATE TABLE private_message
(
    id           INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    root_id      INT      NOT NULL,
    message_from INT      NOT NULL,
    message_to   INT      NOT NULL,
    create_by    bigint   NOT NULL,
    create_date  DATETIME NOT NULL,
    update_by    bigint   NOT NULL,
    update_date  DATETIME NOT NULL,
    description  VARCHAR(2000)
) engine = InnoDB;

create table site
(
    site_id                   INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
    site_name                 VARCHAR(50)     not null comment '',
    logo_path                 VARCHAR(50),
    logo_content_type         VARCHAR(20) comment '',
    active                    BOOLEAN,
    update_by                 bigint          not null comment '',
    update_date               datetime        not null comment '',
    create_by                 bigint          not null comment '',
    create_date               datetime        not null comment '',
    public_port               VARCHAR(4),
    secure_port               VARCHAR(4),
    domain_name               VARCHAR(50),
    google_analytics_id       VARCHAR(50),
    secure_connection_enabled BOOLEAN,
    footer                    VARCHAR(255),
    listing_page_size         VARCHAR(4),
    section_page_size         VARCHAR(4),
    theme_id                  INT(4),
    title                     varchar(100)    NOT NULL,
    primary key (site_id)
) comment ='';

create table user_
(
    id                      bigint      not null AUTO_INCREMENT,
    account_non_expired     BOOLEAN     NOT NULL,
    account_non_locked      BOOLEAN     NOT NULL,
    credentials_non_expired BOOLEAN     NOT NULL,
    address_line1           varchar(30) not null comment '',
    address_line2           varchar(30) not null comment '',
    username                varchar(50) not null comment '',
    password                varchar(60) not null comment '',
    profile_photo           varchar(255) DEFAULT NULL,
    profile_photo_small     varchar(255) DEFAULT NULL,
    city_name               varchar(25) not null comment '',
    create_by               bigint      not null comment '',
    create_date             datetime    not null comment '',
    enabled                 boolean     not null,
    state_name              varchar(40) not null comment '',
    country_name            varchar(40) not null comment '',
    zip_code                varchar(10) not null comment '',
    email                   varchar(50) not null comment '',
    phone                   varchar(20) comment '',
    user_type               varchar(1)  not null comment '',
    last_login_date         datetime comment '',
    last_visit_site_id      INT(4),
    update_by               bigint      not null comment '',
    update_date             datetime    not null comment '',
    primary key (id)
) comment ='';

CREATE TABLE page_view
(
    id              INT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id         INT,
    username        varchar(50),
    user_ip_address VARCHAR(30),
    page_type       INT,
    page_id         bigint(20),
    page_name       varchar(100),
    page_url        VARCHAR(100),
    create_date     DATETIME NOT NULL
) engine = InnoDB;

create table authority
(
    id             bigint      not null AUTO_INCREMENT,
    user_id        bigint      not null,
    authority_name varchar(50) not null,
    primary key (id), /*!
    UNIQUE KEY uni_user_id_authority (authority_name, user_id), */
    /*!
    KEY fk_user_id_idx (user_id), */
    /*!
    constraint fk_authority_users foreign key (user_id) references user_ (id)*/
);

create table user_site
(
    user_id bigint not null,
    site_id INT(4) not null,
    primary key (user_id, site_id)
);