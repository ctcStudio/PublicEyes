package com.hiepkhach9x.publiceyes.store;

import com.hiepkhach9x.publiceyes.entities.CategoryText;

import java.util.ArrayList;

/**
 * Created by hungh on 7/1/2017.
 */

public class CategoryMoto {
    private static final String[] category = {
            "Lỗi Điều khiển xe máy không đội “mũ bảo hiểm cho người đi mô tô, xe máy”",
            "Chở người ngồi trên xe không đội mũ bảo hiểm hoặc đội mà cài quai không đúng quy cách",
            "Chở 2 người trên xe",
            "Chở theo 3 người trở lên trên xe",
            "Không giữ khoảng cách an toàn để xảy ra va chạm với xe chạy liền trước hoặc không giữ khoảng cách theo quy định của biển báo hiệu “Cự ly tối thiểu giữa hai xe”",
            "Đi vào đường cao tốc không dành cho xe máy",
            "Sử dụng ô, điện thoại di động, thiết bị âm thanh",
            "Vượt đèn đỏ",
            "Vượt đèn vàng khi sắp chuyển sang đèn đỏ",
            "Chuyển làn đường không đúng nơi được phép hoặc không có tín hiệu báo trước",
            "Quay đầu xe tại nơi cấm quay đầu xe",
            "Điều khiển xe máy khi chưa đủ 16 tuổi",
            "Từ 16 tuổi đến dưới 18 tuổi điều khiển xe mô tô từ 50cm3 trở lên",
            "Điều khiển dưới 175cm3 không có GPLX, sử dụng GPLX không do cơ quan có thẩm quyền cấp, GPLX hoặc bị tẩy xóa",
            "Điều khiển xe từ 175cm3 trở lên không có GPLX, sử dụng GPLX không do cơ quan có thẩm quyền cấp hoặc bị tẩy xóa",
            "Không mang theo Giấy phép lái xe",
            "Điều khiển xe không có Giấy đăng ký xe",
            "Sử dụng Giấy đăng ký xe bị tẩy xóa; Không đúng số khung, số máy hoặc không do cơ quan có thẩm quyền cấp",
            "Không có hoặc không mang theo Giấy chứng nhận bảo hiểm TNDS của chủ xe cơ giới",
            "Không chấp hành hiệu lệnh, chỉ dẫn của biển báo hiệu, vạch kẻ đường",
            "Điều khiển xe chạy quá tốc độ quy định từ 5km/h đến dưới 10km/h",
            "Không sử dụng đủ đèn chiếu sáng khi trời tối hoặc khi sương mù, thời tiết xấu hạn chế tầm nhìn; sử dụng đèn chiếu xa khi tránh xe đi ngược chiều",
            "Không chấp hành hiệu lệnh của đèn tín hiệu giao thông",
            "Đi vào đường cấm, khu vực cấm; đi ngược chiều của đường một chiều, đường có biển “Cấm đi ngược chiều”; trừ các xe ưu tiên đang đi làm nhiệm vụ khẩn cấp theo quy định",
            "Điều khiển xe không đi bên phải theo chiều đi của mình; đi không đúng phần đường hoặc làn đường quy định",
            "Điều khiển xe chạy dưới tốc độ tối thiểu trên những đoạn đường bộ có quy định tốc độ tối thiểu cho phép",
            "Không chấp hành hiệu lệnh, hướng dẫn của người điều khiển giao thông hoặc người kiểm soát giao thông",
            "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn nhưng chưa tới mức vi phạm quy định",
            "Điều khiển xe chạy quá tốc độ quy định trên 20 km/h",
            "Gây tai nạn giao thông không dừng lại, không giữ nguyên hiện trường, bỏ trốn không đến trình báo với cơ quan có thẩm quyền, không tham gia cấp cứu người bị nạn",
            "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn vượt quá 50 miligam đến 80 miligam/100 mililít máu hoặc vượt quá 0,25 miligam đến 0,4 miligam/1 lít khí thở\n",
            "Điều khiển xe lạng lách, đánh võng; chạy quá tốc độ đuổi nhau trên đường bộ gây tai nạn giao thông hoặc không chấp hành hiệu lệnh dừng xe của người thi hành công vụ",
            "Bấm còi, rú ga liên tục; bấm còi hơi, sử dụng đèn chiếu xa trong đô thị, khu đông dân cư, trừ các xe ưu tiên đang đi làm nhiệm vụ theo quy định",
            "Điều khiển xe trên đường mà trong cơ thể có chất ma túy",
            "Người không chấp hành yêu cầu kiểm tra chất ma túy, nồng độ cồn của người kiểm soát giao thông hoặc người thi hành công vụ",
            "Điều khiển xe lạng lách, đánh võng trên đường bộ trong, ngoài đô thị",
            "Điều khiển xe chạy quá tốc độ quy định từ 10 km/h đến 20 km/h"
    };
    private static final String[] description = {
            "100.000 - 200.000",
            "100.000 - 200.000",
            "100.000 - 200.000",
            "200.000 - 400.000",
            "60.000 - 80.000",
            "200.000 - 400.000",
            "60.000 – 80.000",
            "200.000 - 400.000",
            "100.000 - 200.000",
            "80.000 - 100.000",
            "80.000 - 100.000",
            "cảnh cáo",
            "400.000 - 600.000",
            "800.000 - 1.200.000, tịch thu GPLX",
            "4.000.000 - 6.000.000, tịch thu GPLX",
            "80.000 - 120.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "80.000 - 120.000",
            "60.000 - 80.000",
            "100.000 - 200.000",
            "80.000 - 100.000",
            "200.000 - 400.000",
            "200.000 - 400.000",
            "100.000 - 200.000",
            "200.000 - 400.000",
            "200.000 - 400.000",
            "400.000 - 600.000",
            "2.000.000 - 3.000.000",
            "1.000.000 - 3.000.000",
            "500.000 - 1.000.000, Thu GPLX",
            "100.000 - 200.000",
            "200.000 - 400.000",
            "2.000.000 - 3.000.000",
            "2.000.000 - 3.000.000",
            "5.000.000 - 7.000.000",
            "400.000 - 600.000"
    };

    public static ArrayList<CategoryText> getCategoriesMoto() {
        ArrayList<CategoryText> categoryTexts = new ArrayList<>();
        for (int i = 0; i < category.length; i++) {
            CategoryText categoryText = new CategoryText(i, category[i], description[i]);
            categoryTexts.add(categoryText);
        }
        return categoryTexts;
    }
}
