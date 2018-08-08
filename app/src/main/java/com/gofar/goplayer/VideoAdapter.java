package com.gofar.goplayer;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author lcf
 * @date 8/8/2018 下午 12:00
 * @since 1.0
 */
public class VideoAdapter extends BaseQuickAdapter<Video, BaseViewHolder> {
    public VideoAdapter(@Nullable List<Video> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Video item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_path, item.getPath())
                .setText(R.id.tv_info, NumberUtils.formatByte(item.getSize()) + " " + NumberUtils.formatDate(item.getDuration()));
        ImageView imageView = helper.getView(R.id.iv_thumbnail);
        Picasso.get().load("file://" + item.getThumbnail()).into(imageView);
    }
}
