package com.taklip.yoda.tool;

public class Constants {
	public static final char ACTIVE_YES = 'Y';

	public static final char ACTIVE_NO = 'N';

	public static final int ADMIN_SEARCH_MAXCOUNT = 100;

//	public static final int PAGE_TYPE_HOME = 1;
//
//	public static final int PAGE_TYPE_BRAND = 2;
//
//	public static final int PAGE_TYPE_CONTENT = 3;
//
//	public static final int PAGE_TYPE_ITEM = 4;

	public static final String USER_RATE_THUMB_UP = "up";

	public static final String USER_RATE_THUMB_DOWN = "down";

	public static final String USER_RATE_THUMB_NEUTRAL = "neutral";

	public static final String CONFIG_LOG4J_PROPERTY = "log4j.appender.R.File";

	public static final String CONFIG_LOG4J_LOGFILENAME = "yoda.log";

	public static final String CONFIG_PROPERTIES_FILENAME = "yoda.properties";

	public static final String CONFIG_PROPERTY_WORKINGDIRECTORY = "working.directory";

	public static final String CONFIG_PROPERTY_LOGDIRECTORY = "log4j.directory";

	public static final String CONFIG_COMPLETED_FILENAME = "yoda.complete.txt";

	public static final int DEFAULT_LISTING_PAGE_SIZE = 10;

	public static final String DEFAULT_CONTEXT_PATH = "yoda";

	public static final String FRONTEND_URL_PREFIX = "";

	public static final String FRONTEND_URL_SECTION = "section";

	public static final String FRONTEND_URL_ITEM = "item";

	public static final String FRONTEND_URL_CONTENT = "content";

	public static final String FRONTEND_URL_CONTACTUS = "contactus";

	public static final String FRONTEND_URL_SEARCH = "search";

	public static final String FRONTEND_URL_HOME = "home";

	public static final String FRONTEND_URL_STATIC = "static";

	public static final String IMAGEPROVIDER_ITEM = "I";

	public static final String IMAGEPROVIDER_CONTENT = "C";

	public static final String IMAGEPROVIDER_SITE = "S";

	public static final String IMAGEPROVIDER_TEMPLATE = "T";

	public static final String IMAGEPROVIDER_URL = "U";

	public static final String IMAGE_MIME[] = { "bmp", "gif", "jpeg", "jpg",
		"jpe", "png", "tiff", "tif", "wbmp", "ras", "pnm", "pbm", "pgm",
		"ppm", "rgb", "xbm", "xpm", "xwd" };

	public static final String LOGIN_PAGE_URL = "/login";

	public static final String MENU_HOME = "HOME";

	public static final String MENU_ITEM = "ITEM";

	public static final String MENU_CONTENT = "CONT";

	public static final String MENU_SECTION = "SECT";

	public static final String MENU_STATIC_URL = "SURL";

	public static final String MENU_SIGNIN = "SIGI";

	public static final String MENU_SIGNOUT = "SIGO";

	public static final String MENU_CONTACTUS = "CTUS";

	public static final String MENUSET_MAIN = "MAIN";

//	public static final String MENUSET_SECONDARY = "SECONDARY";

	public static final String MODE_CREATE = "C";

	public static final String MODE_UPDATE = "U";

	public static final int PAGE_NAV_COUNT = 5;

	public static final String PORTNUM_PUBLIC = "80";

	public static final String PORTNUM_SECURE = "443";

	public static final char PUBLISHED_YES = 'Y';

	public static final char PUBLISHED_NO = 'N';

	public static final String SESSION_COOKIE_USER = "user";

	public static final String SESSION_IGNORETOKEN = "com.yoda.ignoreToken";

	public static final String USER_ROLE_ADMINISTRATOR = "administrator";

	public static final String USER_ROLE_SUPERUSER = "superUser";

	public static final String USER_ROLE_USER = "user";

	public static final char VALUE_YES = 'Y';

	public static final char VALUE_NO = 'N';

	public static final String WEBSERVICE_STATUS_SUCCESS = "success";

	public static final String WEBSERVICE_STATUS_FAILED = "failed";

	public static final int DEFAULT_LISTING_PAGE_COUNT = 10;

	public static final char SECTION_SORT_PRICE_DSC = '1';

	public static final char SECTION_SORT_PRICE_ASC = '2';

	public static final char SECTION_SORT_DESC_DSC = '3';

	public static final char SECTION_SORT_DESC_ASC = '4';

	/* kafka */
	public static final String KAFKA_TOPIC_PAGE_VIEW = "pageViewTopic";

	public static final String KAFKA_TOPIC_REDIS_INCR = "redisIncrTopic";

//	public static final String TOPIC_CLICK = "clickTopic";

	public static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";

	public static final String KAFKA_GROUP_ID_PAGE_VIEW = "page-view-data";

	public static final String KAFKA_GROUP_ID_REDIS_INCR = "redis-incr-data";

	public static final String KAFKA_CONTAINER_PAGE_VIEW_DATA = "pageViewContainer";

	public static final String KAFKA_CONTAINER_REDIS_INCR = "redisIncrContainer";

	/* redis */
//	public static final String REDIS_HOME_PAGE_CONTENT_ID_LIST = "homePageContentIdList";

	public static final String REDIS_HOME_PAGE_BRAND_ID_LIST = "homePageBrandIdList";

	public static final String REDIS_HOME_PAGE_ITEM_ID_LIST = "homePageItemIdList";

	public static final String REDIS_CONTENT = "content";

	public static final String REDIS_CONTENT_RATE = "contentRate";

	public static final String REDIS_CONTENT_HIT_COUNTER = "contentHitCounter";

	public static final String REDIS_CONTENT_CONTRIBUROR_LIST = "contentContributorList";

	public static final String REDIS_CONTENT_BRAND_LIST = "contentBrandList";

	public static final String REDIS_CONTENT_ITEM_LIST = "contentItemList";

	public static final String REDIS_CONTENT_BRAND = "contentBrand";

	public static final String REDIS_CONTENT_CONTRIBUROR = "contentContributor";

	public static final String REDIS_CONTENT_FEATURE_DATA_ID_LIST = "contentFeatureDataIdList";

	public static final String REDIS_CONTENT_NOT_FEATURE_DATA_ID_LIST = "contentNotFeatureDataIdList";

	public static final String REDIS_CONTENT_NOT_FEATURE_DATA_COUNT_LIST = "contentNotFeatureDataCount";

	public static final String REDIS_BRAND = "brand";

	public static final String REDIS_BRAND_TOP_VIEW_LIST = "brandTopViewList";

	public static final String REDIS_BRAND_HIT_COUNTER = "brandHitCounter";

	public static final String REDIS_BRAND_RATE = "brandRate";

	public static final String REDIS_ITEM = "item";

	public static final String REDIS_ITEM_TOP_VIEW_LIST = "itemTopViewList";

	public static final String REDIS_ITEM_HIT_COUNTER = "itemHitCounter";

	public static final String REDIS_ITEM_RATE = "itemRate";

	public static final String REDIS_CATEGORY = "category";

	public static final String NEWLINE = "#NEWLINE#";
}