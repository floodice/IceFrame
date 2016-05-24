package com.flood.model;

import java.util.List;

/**
 * 在此写用途
 *
 * @version V1.0 <知乎每日最新数据结构>
 * @author: flood
 * @date: 2016-02-17 17:07
 */
public class LatestNew extends ResponseBO{
    public List<News> stories;
    public List<TopStories> top_stories;

    public static class News extends DataBean{
        public String[] images;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }

    public static class TopStories extends DataBean{
        public String image;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }
}
