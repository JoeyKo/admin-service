package com.example.admin.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private final long fileSize = 1073741824;

    public long getFileSize() {
        return this.fileSize;
    }

}