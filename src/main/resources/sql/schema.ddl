    alter table content 
        drop 
        foreign key FK38B734792298136A;

    alter table content 
        drop 
        foreign key FK38B7347966D33571;

    alter table content_image 
        drop 
        foreign key FK6942151FFE5CF1;

    alter table country 
        drop 
        foreign key FK39175796EEF82980;

    alter table home_page 
        drop 
        foreign key FK7E3F59EF1FFE5CF1;

    alter table home_page 
        drop 
        foreign key FK7E3F59EFB3AF263;

    alter table menu 
        drop 
        foreign key FK33155F1FFE5CF1;

    alter table menu 
        drop 
        foreign key FK33155FB3AF263;

    alter table menu 
        drop 
        foreign key FK33155F66D33571;

    alter table poll_detail 
        drop 
        foreign key FKC19E18D1C5B1A582;

    alter table state 
        drop 
        foreign key FK68AC491EEF82980;

    alter table state 
        drop 
        foreign key FK68AC491D3F4ABD1;

    alter table user_site 
        drop 
        foreign key FK143C533B84589763;

    alter table user_site 
        drop 
        foreign key FK143C533BA4815E3;

    drop table if exists contact_us;

    drop table if exists content;

    drop table if exists content_image;

    drop table if exists country;

    drop table if exists feedback;

    drop table if exists home_page;

    drop table if exists release;

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

    create table contact_us (
        contact_us_id INT(4) not null AUTO_INCREMENT,
        site_id INT(4) not null,
        name varchar(50) not null comment '',
        address_line1 varchar(30) not null comment '',
        address_line2 varchar(30) not null comment '',
        city_name varchar(25) not null comment '',
        zip_code varchar(10) not null comment '',
        email varchar(50) not null comment '',
        phone varchar(20) comment '',
        description varchar(255) not null comment '',
        seq_num integer not null comment '',
        active boolean not null comment '',
        update_by INT not null comment '',
        update_date datetime not null comment '',
        create_by INT not null comment '',
        create_date datetime not null comment '',
        primary key (contact_us_id)
    ) comment='';

    create table content (
        content_id bigint not null AUTO_INCREMENT,
        category_id INT comment '',
        site_id INT(4) not null,
        natural_key varchar(255) not null comment '',
        title varchar(128) not null comment '',
        short_description text not null comment '',
        description text not null comment '',
        page_title varchar(255) not null comment '',
        hit_counter INT not null comment '',
        publish_date date not null comment '',
        expire_date date not null comment '',
        published boolean not null comment '',
        update_by bigint not null comment '',
        update_date datetime not null comment '',
        create_by bigint not null comment '',
        create_date datetime not null comment '',
        section_id INT comment '',
        featured_image varchar(255),
        score INT not null,
        primary key (content_id),
        FOREIGN KEY (category_id) REFERENCES category(category_id)
    ) comment='';

	CREATE TABLE item (
		id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
		brand VARCHAR(30),
		content_id bigint,
		create_by INT NOT NULL,
		create_date DATETIME NOT NULL,
		description VARCHAR(2000),
		image_path VARCHAR(50),
		level VARCHAR(20),
		name VARCHAR(30),
		price INT,
		rating INT,
		site_id INT(4) NOT NULL,
		update_by INT NOT NULL,
		update_date DATETIME NOT NULL,
		INDEX(name),
		FOREIGN KEY (content_id) REFERENCES content(content_id)
	) engine=InnoDB;

	CREATE TABLE comment (
		id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
		content_id bigint,
		user_id INT NOT NULL,
		create_date DATETIME NOT NULL,
		description VARCHAR(2000),
		rating INT,
		site_id INT(4) NOT NULL,
		FOREIGN KEY (content_id) REFERENCES content(content_id)
	) engine=InnoDB;

    create table content_image (
        image_id bigint not null,
        site_id bigint not null,
        seq_num INT not null comment '',
        content_type varchar(20) not null comment '',
        image_name varchar(40) not null comment '',
        image_value longblob not null comment '',
        height INT not null comment '',
        width INT not null comment '',
        update_by INT not null comment '',
        update_date datetime not null comment '',
        create_by INT not null comment '',
        create_date datetime not null comment '',
        content_id bigint,
        primary key (image_id)
    ) comment='';

    create table country (
        country_id bigint not null,
        site_id bigint not null,
        country_code varchar(2) not null comment '',
        country_name varchar(40) not null comment '',
        rec_update_by varchar(20) not null comment '',
        rec_update_datetime datetime not null comment '',
        rec_create_by varchar(20) not null comment '',
        rec_create_datetime datetime not null comment '',
        primary key (country_id)
    ) comment='';

    create table category (
        category_id INT not null AUTO_INCREMENT,
        name varchar(50) not null,
        parent INT not null comment '',
        description text not null comment '',
        update_by bigint not null comment '',
        update_date datetime not null comment '',
        create_by bigint not null comment '',
        create_date datetime not null comment '',
        primary key (id)
    ) comment='';

    create table feedback (
        id bigint not null AUTO_INCREMENT,
        username varchar(50) not null,
        email varchar(50) not null comment '',
        phone varchar(20) not null comment '',
        description text not null comment '',
        create_date datetime not null comment '',
        primary key (id)
    ) comment='';

    create table home_page (
        home_page_id INT not null,
        site_id INT(4) not null,
        seq_num INT not null comment '',
        feature_data boolean not null comment '',
        update_by bigint not null comment '',
        update_date datetime not null comment '',
        create_by bigint not null comment '',
        create_date datetime not null comment '',
        content_id bigint,
        primary key (home_page_id)
    ) comment='';

	create table release_ (
		release_id bigint not null,
		create_date datetime,
		modified_date datetime,
		build_number INTEGER,
		verified BOOLEAN,
		primary key (release_id)
	);

    create table menu (
        menu_id INT(4) not null,
        site_id INT(4) not null,
        set_name varchar(20) not null comment '',
        title varchar(20) not null comment '',
        name varchar(20) not null comment '',
        seq_num INT not null comment '',
        parent_id INT(4) comment '',
        menu_type varchar(5) not null comment '',
        menu_url varchar(255) not null comment '',
        menu_window_target varchar(20) not null comment '',
        menu_window_mode varchar(255) not null comment '',
        published boolean not null comment '',
        update_by INT not null comment '',
        update_date datetime not null comment '',
        create_by INT not null comment '',
        create_date datetime not null comment '',
        section_id INT,
        content_id bigint,
        primary key (menu_id)
    ) comment='';

    create table poll_detail (
        poll_detail_id bigint not null,
        site_id bigint not null,
        poll_option varchar(80) not null comment '',
        seq_num integer not null comment '',
        poll_vote_count integer not null comment '',
        rec_update_by varchar(20) not null comment '',
        rec_update_datetime datetime not null comment '',
        rec_create_by varchar(20) not null comment '',
        rec_create_datetime datetime not null comment '',
        poll_header_id bigint,
        primary key (poll_detail_id)
    ) comment='';

    create table poll_header (
        poll_header_id bigint not null,
        site_id bigint not null,
        poll_topic varchar(255) not null comment '',
        poll_publish_on date not null comment '',
        poll_expire_on date not null comment '',
        published char(1) not null comment '',
        rec_update_by varchar(20) not null comment '',
        rec_update_datetime datetime not null comment '',
        rec_create_by varchar(20) not null comment '',
        rec_create_datetime datetime not null comment '',
        primary key (poll_header_id)
    ) comment='';

    create table section (
        section_id INT not null,
        site_id INT(4) not null,
        natural_key varchar(255) not null comment '',
        title varchar(40) not null comment '',
        seq_num INT not null comment '',
        parent_id INT comment '',
        short_title varchar(20) not null comment '',
        description text not null comment '',
        published boolean not null comment '',
        update_by INT not null comment '',
        update_date datetime not null comment '',
        create_by INT not null comment '',
        create_date datetime not null comment '',
        primary key (section_id)
    ) comment='';

    create table site (
		site_id INT(4) UNSIGNED NOT NULL AUTO_INCREMENT,
        site_name VARCHAR(50) not null comment '',
        logo_path VARCHAR(50),
        logo_content_type VARCHAR(20) comment '',
        active BOOLEAN,
        update_by bigint not null comment '',
        update_date datetime not null comment '',
        create_by bigint not null comment '',
        create_date datetime not null comment '',
        public_port VARCHAR(4),
        secure_port VARCHAR(4),
        domain_name VARCHAR(50),
        google_analytics_id VARCHAR(50),
        secure_connection_enabled BOOLEAN,
        footer VARCHAR(255),
        listing_page_size VARCHAR(4),
        section_page_size VARCHAR(4),
        theme_id INT(4),
        primary key (site_id)
    ) comment='';

    create table state (
        state_id bigint not null,
        site_id bigint not null,
        state_code varchar(2) not null comment '',
        state_name varchar(40) not null comment '',
        rec_update_by varchar(20) not null comment '',
        rec_update_datetime datetime not null comment '',
        rec_create_by varchar(20) not null comment '',
        rec_create_datetime datetime not null comment '',
        country_id bigint,
        shipping_region_id bigint,
        primary key (state_id)
    ) comment='';

    create table template (
        template_id bigint not null,
        site_id bigint not null,
        template_name varchar(20) not null comment '',
        template_desc varchar(40) not null comment '',
        rec_update_by varchar(20) not null comment '',
        rec_update_datetime datetime not null comment '',
        rec_create_by varchar(20) not null comment '',
        rec_create_datetime datetime not null comment '',
        primary key (template_id)
    ) comment='';

    create table user_ (
        user_id bigint not null AUTO_INCREMENT,
        account_non_expired BOOLEAN NOT NULL;
        account_non_locked BOOLEAN NOT NULL;
        credentials_non_expired BOOLEAN NOT NULL;
        address_line1 varchar(30) not null comment '',
        address_line2 varchar(30) not null comment '',
        username varchar(50) not null comment '',
        password varchar(60) not null comment '',
        city_name varchar(25) not null comment '',
        create_by bigint not null comment '',
        create_date datetime not null comment '',
        enabled boolean not null,
        state_name varchar(40) not null comment '',
        country_name varchar(40) not null comment '',
        zip_code varchar(10) not null comment '',
        email varchar(50) not null comment '',
        phone varchar(20) comment '',
        user_type varchar(1) not null comment '',
        last_login_date datetime comment '',
        last_visit_site_id INT(4),
        update_by bigint not null comment '',
        update_date datetime not null comment '',
        primary key (user_id)
    ) comment='';

	create table authority (
		id bigint not null AUTO_INCREMENT,
		user_id bigint not null,
		authority_name varchar(50) not null,
		primary key (id),
		/*!UNIQUE KEY uni_user_id_authority (authority_name,user_id),*/
		/*!KEY fk_user_id_idx (user_id),*/
		/*!constraint fk_authority_users foreign key(user_id) references user_(user_id)*/
	);

    create table user_site (
        user_id bigint not null,
        site_id INT(4) not null,
        primary key (user_id, site_id)
    );

    create index content_natural_key on content (natural_key);

    alter table content 
        add index FK38B734792298136A (image_id), 
        add constraint FK38B734792298136A 
        foreign key (image_id) 
        references content_image (image_id);

    alter table content 
        add index FK38B7347966D33571 (section_id), 
        add constraint FK38B7347966D33571 
        foreign key (section_id) 
        references section (section_id);

    alter table content_image 
        add index FK6942151FFE5CF1 (content_id), 
        add constraint FK6942151FFE5CF1 
        foreign key (content_id) 
        references content (content_id);

    alter table home_page 
        add index FK7E3F59EF1FFE5CF1 (content_id), 
        add constraint FK7E3F59EF1FFE5CF1 
        foreign key (content_id) 
        references content (content_id);

    alter table menu 
        add index FK33155F1FFE5CF1 (content_id), 
        add constraint FK33155F1FFE5CF1 
        foreign key (content_id) 
        references content (content_id);

    alter table menu 
        add index FK33155F66D33571 (section_id), 
        add constraint FK33155F66D33571 
        foreign key (section_id) 
        references section (section_id);

    alter table poll_detail 
        add index FKC19E18D1C5B1A582 (poll_header_id), 
        add constraint FKC19E18D1C5B1A582 
        foreign key (poll_header_id) 
        references poll_header (poll_header_id);

    create index natural_key on section (natural_key);

    alter table state 
        add index FK68AC491D3F4ABD1 (country_id), 
        add constraint FK68AC491D3F4ABD1 
        foreign key (country_id) 
        references country (country_id);

    alter table user_site
        add index FK143C533B84589763 (user_id), 
        add constraint FK143C533B84589763 
        foreign key (user_id) 
        references user_ (user_id);

    alter table user_site 
        add index FK143C533BA4815E3 (site_id), 
        add constraint FK143C533BA4815E3 
        foreign key (site_id) 
        references site (site_id);