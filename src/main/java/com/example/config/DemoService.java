package com.example.config;

import javax.inject.Singleton;

import org.eclipse.microprofile.config.ConfigProvider;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.Isolates;
import org.graalvm.nativeimage.Isolates.CreateIsolateParameters;
import org.graalvm.nativeimage.c.function.CEntryPoint;

@Singleton
public class DemoService {

	public int readPropertyValue() {
		var params = CreateIsolateParameters.getDefault();
		var isolateThread = Isolates.createIsolate(params);

		try {
			return processInIsolate(isolateThread);
		}
		finally {
			Isolates.tearDownIsolate(isolateThread);
		}

	}

	@CEntryPoint
	private static int processInIsolate(@CEntryPoint.IsolateThreadContext IsolateThread isolateThread) {
		int propertyToOverride = ConfigProvider.getConfig().getValue("property.to.override", Integer.class);
		System.out.println("property.to.override: " + propertyToOverride);

		return propertyToOverride;
	}

}
