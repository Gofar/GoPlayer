package com.gofar.goplayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author lcf
 * @date 7/8/2018 上午 11:48
 * @since 1.0
 */
public class UriSimple extends Simple {
    private String uri;
    private String extension;
    @SerializedName("ad_tag_uri")
    private String adTagUri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAdTagUri() {
        return adTagUri;
    }

    public void setAdTagUri(String adTagUri) {
        this.adTagUri = adTagUri;
    }
}
