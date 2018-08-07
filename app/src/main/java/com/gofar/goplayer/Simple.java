package com.gofar.goplayer;

import com.google.gson.annotations.SerializedName;

/**
 * @author lcf
 * @date 7/8/2018 上午 11:36
 * @since 1.0
 */
public abstract class Simple {
    private String name;
    @SerializedName("drm_scheme")
    private String drmScheme;
    @SerializedName("drm_license_url")
    private String drmLicenseUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrmScheme() {
        return drmScheme;
    }

    public void setDrmScheme(String drmScheme) {
        this.drmScheme = drmScheme;
    }

    public String getDrmLicenseUrl() {
        return drmLicenseUrl;
    }

    public void setDrmLicenseUrl(String drmLicenseUrl) {
        this.drmLicenseUrl = drmLicenseUrl;
    }
}
