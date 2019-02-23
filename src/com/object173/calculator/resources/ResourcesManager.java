package com.object173.calculator.resources;

import com.object173.calculator.resources.bundle.Utf8ResourceBundle;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ResourcesManager {
	
	private static ResourcesManager sResourcesManager;
	
	private static final String BUNDLE_NAME = "values/strings";
	private final ResourceBundle mBundle;
	
	public static ResourcesManager getInstance() throws MissingResourceException {
		if(sResourcesManager == null) {
			sResourcesManager = new ResourcesManager();
		}
		return sResourcesManager;
	}
	
	private ResourcesManager() throws MissingResourceException {
		mBundle = Utf8ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public String getString(final StringResKey key) {
		return mBundle.getString(key.getKey());
	}

}
