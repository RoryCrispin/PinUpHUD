/*
 * Rory Crispin -rorycrispin.co.uk- rozzles.com
 *
 * Distributed under the Attribution-NonCommercial-ShareAlike 4.0 International License, full conditions can be found here:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * This is free software, and you are welcome to redistribute it under certain conditions;
 *
 *  Go crazy,
 *  Rozz xx
 */

package com.shirwa.simplistic_rss;

import com.ocpsoft.pretty.time.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RssItem {
    String title;
    String description;
    String link;
    String imageUrl;
    String pubDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return stringToDate(pubDate);
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTimeSincePost() {
        return getTimePassed(stringToDate(pubDate));
    }

    private Date stringToDate(String strDate) {
        DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTimePassed(Date date) {
//        DateTime dt = new DateTime(date);
        PrettyTime p = new PrettyTime();
        return p.format(date);
    }


}
