package com.matheus.k8s.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "k8s-example")
public class KnotePropertiesConfig {
	
	@Value("${uploadDir:/tmp/uploads/}")
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}
}
