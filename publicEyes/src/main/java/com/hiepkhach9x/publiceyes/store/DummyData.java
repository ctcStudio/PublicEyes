package com.hiepkhach9x.publiceyes.store;

import android.content.Context;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.entities.News;

import java.util.ArrayList;

/**
 * Created by hungh on 3/4/2017.
 */

public class DummyData {
    public static final String SIGN_UP_RESPONSE = "{\"Code\": 0,\"Data\": {\"Message\": \"Xử lý thành công!\",\"Path\": \"\"}}";
    public static final String GET_USER_RESPONSE = "{\"Code\": 0,\"Data\": {\"username\": \"minhpt\",\"password\": \"6f3329e55e7c655d29d72e512bd28bac\"," +
            "\"decentralization_id\": 2,\"name\": \"Minh\",\"age\": 18,\"address\": \"102 trường chinhw\",\"id_card\": \"123456789\",\"mobile_phone\": " +
            "\"01667823462\",\"bonus_point\": 0,\"payment_type\": \"as\",\"bank_account\": \"as\",\"bank_name\": \"a\",\"bank_branch\": \"a\"," +
            "\"create_by\": null,\"create_date\": null,\"update_by\": \"minhpt\",\"update_date\": \"2017-05-04T14:37:58.037\",\"delete_by\": null,\"delete_date\": null}}";
    public static final String UPLOAD_RESPONSE = "{\"Code\": 0,\"Data\": {\"Message\": \"Xử lý thành công!\",\"Path\": \"https://drive.google.com/open?id=0B9930oH9q2HrNHhqelVLTnc0djA\"}}";
    public static final String MESSAGE_RESPONSE = "{\"Code\": 0,\"Data\": {\"Message\": \"Xử lý thành công!\",\"Path\": \"\"}}";
    public static final String GET_LIST_VIOLATION = "{\"Code\": 0,\"Data\": [{\"violation_id\": 1,\"category_id\": 1,\"category_name\":\"Cấm đỗ\"," +
            "\"path\": \"/Uploads/Files/636295083555836511_tumblr_mm82d6DNhY1rwuy3uo1_400.jpg\",\"location\": \"123,123\",\"address\": \"102 Trường Chinh Đống Đa\"," +
            "\"license_plate\": \"30F-123.45\",\"district\": \"Đống Đa\",\"province\": \"hà nội\",\"operation_id\": 1,\"username\": \"minhpt\",\"create_by\": \"minhpt\"," +
            "\"create_date\": \"2017-05-04T15:38:43.557\",\"update_by\": null,\"update_date\": null,\"delete_by\": null,\"delete_date\": null,\"status_user\": false," +
            "\"status_admin\": false}]}";
    public static final String GET_OPERATON_LIST = "{\"Code\": 0,\"Data\": [{\"operation_id\": 1,\"name\": \"Tháng giao thông\",\"descripton\": " +
            "\"Mỗi báo cáo vi phạm được x2 point\",\"image\": \"https://drive.google.com/open?id=0B9930oH9q2HrZVJBcjBNM2g3bms\",\"detail\": " +
            "\"Mỗi báo cáo vi phạm được x2 point\",\"bonus_point\": 10,\"date_from\": \"2017-04-15T00:00:00\",\"date_to\": \"2017-05-20T00:00:00\"," +
            "\"create_by\": null,\"create_date\": null,\"update_by\": null,\"update_date\": null,\"delete_by\": null,\"delete_date\": null,\"status\": true}," +
            "{\"operation_id\": 2,\"name\": \"Tháng giao thông\",\"descripton\": \"Mỗi báo cáo vi phạm được x2 point\",\"image\": " +
            "\"https://drive.google.com/open?id=0B9930oH9q2HrZVJBcjBNM2g3bms\",\"detail\": \"Mỗi báo cáo vi phạm được x2 point\",\"bonus_point\": 10," +
            "\"date_from\": \"2017-04-15T00:00:00\",\"date_to\": \"2017-05-20T00:00:00\",\"create_by\": null,\"create_date\": null,\"update_by\": null," +
            "\"update_date\": null,\"delete_by\": null,\"delete_date\": null,\"status\": true}]}";
    public static final String GET_CATEGORY_LIST = "{\"Code\": 0,\"Data\": [{\"category_id\": 1,\"name\": \"Ngược chiều\",\"path\": \"https://drive.google.com/open?id=0B9930oH9q2HrZHBFZU8zVDZidEk\"," +
            "\"create_by\": null,\"create_date\": null,\"update_by\": null,\"update_date\": null,\"delete_by\": null,\"delete_date\": null}," +
            "{\"category_id\": 2,\"name\": \"Vượt đèn đỏ\",\"path\": \"https://drive.google.com/open?id=0B9930oH9q2HrZHBFZU8zVDZidEk\",\"create_by\": null," +
            "\"create_date\": null,\"update_by\": null,\"update_date\": null,\"delete_by\": null,\"delete_date\": null}]}";

    public static ArrayList<Category> createCategories(Context context) {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, R.drawable.no_parking, context.getString(R.string.no_parking)));
        categories.add(new Category(2, R.drawable.no_entry, context.getString(R.string.one_way_no_entry)));
        categories.add(new Category(3, R.drawable.parking_on_footpath, context.getString(R.string.parking_on_footpath)));
        categories.add(new Category(4, R.drawable.riding_on_footpath, context.getString(R.string.riding_on_footpath)));
        categories.add(new Category(5, R.drawable.ride_without_helmet, context.getString(R.string.riding_without_helmet)));
        categories.add(new Category(6, R.drawable.defective_number_plate, context.getString(R.string.defective_number_plate)));
        categories.add(new Category(7, R.drawable.not_wearing_seat_belt, context.getString(R.string.not_wearing_seat_belt)));
        categories.add(new Category(8, R.drawable.zebra_cross_issue, context.getString(R.string.zebra_cross)));
        categories.add(new Category(9, R.drawable.uturn, context.getString(R.string.uturn)));
        categories.add(new Category(10, R.drawable.triple_riding, context.getString(R.string.triple_riding)));
        categories.add(new Category(11, R.drawable.mobile_phone, context.getString(R.string.mobile_phone)));
        categories.add(new Category(12, R.drawable.lane_discipline, context.getString(R.string.lane_discipline)));
        categories.add(new Category(13, R.drawable.wrong_parking, context.getString(R.string.wrong_parking)));
        return categories;
    }

    public static ArrayList<Complaint> createComplaints(Context context) {
        ArrayList<Complaint> complaints = new ArrayList<>();
        for (Category category : createCategories(context)) {
            complaints.add(dummyComplaints(category));
        }
        return complaints;
    }


    public static Complaint dummyComplaints(Category category) {
        Complaint complaint = new Complaint();
        complaint.setCategoryId(category.getId());
        complaint.setCategoryName(category.getCategoryName());
        complaint.setTime("2 days");
        complaint.setAddress("Kim No, Dong Anh, Ha Noi");
        complaint.setDescription(category.getCategoryName() + " at Kim No, Dong Anh, Ha Noi");
        complaint.setUserName("HungHN");
        complaint.setImageThumb("http://news.thuvienphapluat.vn/tintuc/Uploads/UserFiles/635014675329755859/Images/xe-khong-chinh-chu-da997.jpg");
        return complaint;
    }

    final static String htmlContent = "<h2>Welcome to the HTML editor!</h2>\n" +
            "<p>Just type the HTML and it will be shown below.</p>\n" +
            "\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
            "\n" +
            "<h2>Heading in h2, som more sample text</h2>\n" +
            "<ul>\n" +
            "\t<li>Nulla facilisi.</li>\n" +
            "\t<li>Pellentesque habitant morbi</li>\n" +
            "\t<li>Quisque vel justo.</li>\n" +
            "\t<li>Nullam posuere purus sed arcu.</li>\n" +
            "</ul>";

    public static ArrayList<News> dummyNews() {
        ArrayList<News> newses = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            News news = new News();
            if (i == 3) {
                news.setBanner("");
                news.setTitle("Title " + i);
            } else {
                news.setBanner("http://wass.edu.vn/wp-content/uploads/2015/09/Banner-WAPS-huong-ung-thang-an-toan-giao-thong-T.Viet-Copy.jpg");
                news.setTitle("Title " + i);
            }
            news.setFromDate("2017-04-15T00:00:00");
            news.setToDate("2017-04-15T00:00:00");
            news.setHtmlContent(htmlContent);
            newses.add(news);
        }
        return newses;
    }
}
