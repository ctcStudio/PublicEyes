package com.hiepkhach9x.publiceyes.store;

import com.hiepkhach9x.publiceyes.entities.CategoryText;

import java.util.ArrayList;

/**
 * Created by hungh on 7/1/2017.
 */

public class CategoryCar {
    private static final String[] category = {
            "Không chấp hành hiệu lệnh, chỉ dẫn của biển báo hiệu, vạch  kẻ đường",
            "Khi dừng xe, đỗ xe không có tín hiệu báo cho người điều khiển phương tiện khác biết",
            "Bấm còi hoặc gây ồn ào, tiếng động lớn trong đô thị, khu đông dân cư từ 22 giờ ngày hôm trước đến 5 giờ ngày hôm sau",
            "Người điều khiển, người được chở trên xe ô tô có trang bị dây an toàn mà không thắt dây an toàn khi xe đang chạy",
            "Chuyển làn đường không đúng nơi cho phép hoặc không có tín hiệu báo trước",
            "Chở người trên buồng lái quá số lượng quy định",
            "Không giảm tốc độ và nhường đường khi điều khiển xe chạy từ trong ngõ, đường nhánh ra đường chính",
            "Xe lắp thiết bị phát tín hiệu sai quy định hoặc sử dụng thiết bị phát tín hiệu mà không có giấy phép\n",
            "Dừng xe sai quy định (dừng trên phần đường xe chạy, dừng xe không sát lề đường, hè phố phía bên phải, dừng xe trên miệng cống thoát nước, miệng hầm đường điện thoại...)",
            "Quay đầu xe trái quy định trong khu dân cư, quay đầu xe ở phần đường dành cho người đi bộ, trên cầu, đầu cầu, gầm cầu vượt... hoặc những nơi có biển báo Cấm quay đầu xe",
            "Lùi xe ở đường một chiều, đường cầm đi ngược chiều, khu vực cấm dừng,... Lùi xe không quan sát hoặc không có tín hiệu báo trước",
            "Điều khiển xe có liên quan trực tiếp đến vụ tai nạn giao thông mà không dừng lại, không giữ nguyên hiện trường, không tham gia cấp cứu người bị nạn",
            "Điều khiển xe chạy quá tốc độ quy định từ 05 km/h đến dưới 10 km/h",
            "Chuyển hướng không giảm tốc độ hoặc không có tín hiệu báo hướng rẽ",
            "Không sử dụng đủ đèn chiếu sáng từ 19 giờ ngày hôm trước đến 06 giờ sáng ngày hôm sau hoặc khi sương mù, thời tiết xấu hạn chế tầm nhìn; sử dụng đèn chiếu xa khi tránh xe đi ngược chiều",
            "Dùng tay sử dụng điện thoại di động khi đang điều khiển xe",
            "Chạy trong hầm đường bộ không sử dụng đèn chiếu sáng gần; lùi xe, quay đầu xe, dừng xe, đỗ xe, vượt xe trong hầm đường bộ không đúng nơi quy định",
            "Đi ngược chiều, đi vào đường cấm, khu vực cấm",
            "Điều khiển xe chạy dưới tốc độ tối thiểu trên những đoạn đường bộ có quy định tốc độ tối thiểu cho phép",
            "Không chấp hành hiệu lệnh, hướng dẫn của người điều khiển giao thông, đèn tín hiệu giao thông",
            "Vượt trong các trường hợp cấm vượt; không có báo hiệu trước khi vượt; vượt bên phải xe khác trong trường hợp không được phép",
            "Tránh xe đi ngược chiều không đúng quy định; không nhường đường cho xe đi ngược chiều theo quy định tại nơi đường hẹp, đường dốc, nơi có chướng ngại vật",
            "Chạy quá tốc độ quy định từ 10 km/h đến 20 km/h",
            "Điều khiển xe chạy quá tốc độ quy định trên 20 km/h đến 35 km/h",
            "Gây tai nạn giao thông không dừng lại, không giữ nguyên hiện trường, bỏ trốn, không tham gia cấp cứu người bị nạn",
            "Điều khiển xe chạy quá tốc độ quy định trên 35 km/h; điều khiển xe đi ngược chiều trên đường cao tốc",
            "Điều khiển xe trên đường mà trong máu hoặc hơi thở có nồng độ cồn vượt quá 50 miligam đến 80 miligam/100 mililít máu hoặc vượt quá 0,25 miligam đến 0,4 miligam/1 lít khí thở",
            "Không chấp hành yêu cầu kiểm tra về chất ma túy, nồng độ cồn của người kiểm soát giao thông hoặc người thi hành công vụ",
    };
    private static final String[] description = {
            "150.000 - 250.000",
            "150.000 - 250.000",
            "150.000 - 250.000",
            "150.000 - 250.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "300.000 - 400.000",
            "600.000 - 800.000",
            "600.000 - 800.000",
            "600.000 - 800.000",
            "600.000 - 800.000",
            "800.000 - 1.200.000",
            "800.000 - 1.200.000",
            "800.000 - 1.200.000",
            "800.000 - 1.200.000, Tước 1-3 tháng",
            "2.000.000 - 3.000.000",
            "2.000.000 - 3.000.000",
            "3.000.000 - 5.000.000",
            "5.000.000 - 7.000.000, Tước 1-3 tháng",
            "5.000.000 - 7.000.000, Tước 2-4 tháng",
            "8.000.000 - 12.000.000, Tước 2-4 tháng",
            "8.000.000 - 12.000.000, Tước 4-6 tháng",
            "16.000.000 - 18.000.000, Tước 10-12 tháng"
    };

    public static ArrayList<CategoryText> getCategoriesCar() {
        ArrayList<CategoryText> categoryTexts = new ArrayList<>();
        for (int i = 0; i < category.length; i++) {
            CategoryText categoryText = new CategoryText(i, category[i], description[i]);
            categoryTexts.add(categoryText);
        }
        return categoryTexts;
    }
}
