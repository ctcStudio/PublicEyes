package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.api.ApiConfig;

import co.utilities.DateUtils;

public class News implements Parcelable {
    /*
    <a>              - hyperlink.
    <b>              - bold, use as last resort <h1>-<h3>, <em>, and <strong> are preferred.
    <blockquote>     - specifies a section that is quoted from another source.
    <code>           - defines a piece of computer code.
    <del>            - delete, used to indicate modifications.
    <dd>             - describes the item in a <dl> description list.
    <dl>             - description list.
    <dt>             - title of an item in a <dl> description list.
    <em>             - emphasized.
    <h1>, <h2>, <h3> - headings.
    <i>              - italic.
    <img>            - specifies an image tag.
    <kbd>            - represents user input (usually keyboard input).
    <li>             - list item in an ordered list <ol> or an unordered list <ul>.
    <ol>             - ordered list.
    <p>              - paragraph.
    <pre>            - pre-element displayed in a fixed width font and and unchanged line breaks.
    <s>              - strikethrough.
    <sup>            - superscript text appears 1/2 character above the baseline used for footnotes and other formatting.
    <sub>            - subscript appears 1/2 character below the baseline.
    <strong>         - defines important text.
    <strike>         - strikethrough is deprecated, use <del> instead.
    <ul>             - unordered list.
    <br>             - line break.
    <hr>*/
    private static final String[] TAG_LIST = {"a", "b", "blockquote", "code", "del", "dd", "dl",
            "dt", "em", "h1", "h2", "h3", "i", "img", "kbd", "li", "ol", "p", "pre",
            "s", "sup", "sub", "strong", "strike", "ul", "br", "hr", "u"};
    @SerializedName("operation_id")
    private String id;
    @SerializedName("image")
    private String banner;
    @SerializedName("name")
    private String title;
    @SerializedName("date_from")
    private String fromDate;   // 2017-04-15T00:00:00
    @SerializedName("date_to")
    private String toDate;   // 2017-04-15T00:00:00
    @SerializedName("descripton")
    private String htmlContent;
    @SerializedName("bonusPoint")
    private int bonusPoint;

    public News() {
    }

    protected News(Parcel in) {
        id = in.readString();
        banner = in.readString();
        title = in.readString();
        fromDate = in.readString();
        toDate = in.readString();
        htmlContent = in.readString();
        bonusPoint = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(banner);
        dest.writeString(title);
        dest.writeString(fromDate);
        dest.writeString(toDate);
        dest.writeString(htmlContent);
        dest.writeInt(bonusPoint);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    private String createHtmlCss(String html) {
        StringBuilder builder = new StringBuilder("<body id = \"body_app\" style = \" left: 0; right: 0; height: auto; width: auto; \">");
        builder.append(html);
        builder.append("</body>");
        return builder.toString();
    }

    public void setHtmlContent(String htmlContent) {
        // Replace all \n (enter) character to <br/>
        htmlContent = htmlContent.replace("\n", "<br/>");
        String nonHtml = createHtmlCss(htmlContent);
        this.htmlContent = nonHtml;
    }

    public String getBanner() {
        if (TextUtils.isEmpty(banner)) {
            return "";
        }
        if (banner.startsWith("http:") || banner.startsWith("https:")) {
            return banner;
        }
        return ApiConfig.makeUrlImage(banner);
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDeadline() {
        String result = "";
        if (!TextUtils.isEmpty(fromDate)) {
            result += DateUtils.convertFormatDateTime(fromDate, Constants.FORMAT_DATE, Constants.APP_FORMAT_DATE) + " ~ ";
        }

        if (!TextUtils.isEmpty(toDate)) {
            result += DateUtils.convertFormatDateTime(fromDate, Constants.FORMAT_DATE, Constants.APP_FORMAT_DATE);
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean hasBanner() {
        return !TextUtils.isEmpty(banner);
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }
}
