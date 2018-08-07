package com.gofar.goplayer;

import java.util.List;

/**
 * @author lcf
 * @date 7/8/2018 上午 11:53
 * @since 1.0
 */
public class PlaylistSimple extends Simple {
    private List<UriSimple> playlist;

    public List<UriSimple> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<UriSimple> playlist) {
        this.playlist = playlist;
    }
}
