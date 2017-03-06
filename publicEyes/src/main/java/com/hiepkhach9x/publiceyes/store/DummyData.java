package com.hiepkhach9x.publiceyes.store;

import android.content.Context;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.Complaint;

import java.util.ArrayList;

/**
 * Created by hungh on 3/4/2017.
 */

public class DummyData {
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
        categories.add(new Category(14, R.drawable.black_film, context.getString(R.string.black_film)));
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
        complaint.setName(category.getCategoryName());
        complaint.setTime("2 days");
        complaint.setAddress("Kim No, Dong Anh, Ha Noi");
        complaint.setContent(category.getCategoryName() + " at Kim No, Dong Anh, Ha Noi");
        complaint.setImageThumb("http://news.thuvienphapluat.vn/tintuc/Uploads/UserFiles/635014675329755859/Images/xe-khong-chinh-chu-da997.jpg");
        return complaint;
    }
}
