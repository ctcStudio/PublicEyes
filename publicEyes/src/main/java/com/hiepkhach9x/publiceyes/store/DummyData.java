package com.hiepkhach9x.publiceyes.store;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.Complaint;

import java.util.ArrayList;

/**
 * Created by hungh on 3/4/2017.
 */

public class DummyData {
    public static ArrayList<Category> createCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, R.drawable.no_parking, "No Parking"));
        categories.add(new Category(2, R.drawable.no_entry, "One Way-No Entry"));
        categories.add(new Category(3, R.drawable.parking_on_footpath, "Parking On Footpath"));
        categories.add(new Category(4, R.drawable.riding_on_footpath, "Riding On Footpath"));
        categories.add(new Category(5, R.drawable.ride_without_helmet, "Riding Without A Helmet"));
        categories.add(new Category(6, R.drawable.defective_number_plate, "Defective numbe plate"));
        categories.add(new Category(7, R.drawable.not_wearing_seat_belt, "Not wearing seat belt"));
        categories.add(new Category(8, R.drawable.zebra_cross_issue, "Stopped on Zebra cross/Near TRF light"));
        categories.add(new Category(9, R.drawable.uturn, "Taking a U-turn where U-turn is prohibited"));
        categories.add(new Category(10, R.drawable.triple_riding, "Triple riding"));
        categories.add(new Category(11, R.drawable.mobile_phone, "Using mobile phone"));
        categories.add(new Category(12, R.drawable.lane_discipline, "Violation lane discipline"));
        categories.add(new Category(13, R.drawable.wrong_parking, "Wrong parking"));
        categories.add(new Category(14, R.drawable.black_film, "Using black film/other materials"));
        return categories;
    }

    public static ArrayList<Complaint> createComplaints() {
        ArrayList<Complaint> complaints = new ArrayList<>();
        for (Category category : createCategories()) {
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
