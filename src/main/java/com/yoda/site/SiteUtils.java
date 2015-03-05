package com.yoda.site;

import com.yoda.site.model.Site;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

public class SiteUtils {
	public static String getPublicURLPrefix(Site site) {
		String defaultPortNum = Constants.PORTNUM_PUBLIC;

		String publicURLPrefix = "http://" + site.getDomainName();

		if (Validator.isNotNull(site.getPublicPort()) && !site.getPublicPort().equals(defaultPortNum)) {
			publicURLPrefix += ":" + site.getPublicPort();
		}

		return publicURLPrefix;
	}

	public static String getSecureURLPrefix(Site site) {
		String secureURLPrefix = getPublicURLPrefix(site);

		boolean isSecureEnabled = site.isSecureConnectionEnabled();

		if (isSecureEnabled) {
			secureURLPrefix = "https://" + site.getDomainName();

			String defaultPortNum = Constants.PORTNUM_SECURE;

			if (Validator.isNotNull(site.getSecurePort()) && site.getSecurePort() != defaultPortNum) {
				secureURLPrefix += ":" + site.getSecurePort();
			}
		}

		return secureURLPrefix;
	}

	public static boolean isSiteSecureConnectionEnabled(Site site) {
		boolean isSecureEnabled = site.isSecureConnectionEnabled();

		if (isSecureEnabled) {
			return true;
		}

		return false;
	}
}
