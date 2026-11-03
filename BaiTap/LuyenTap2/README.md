# LuyenTap2

Bài luyện tập REST API quản lý sách và nhà xuất bản bằng Spring Boot, Spring Data JDBC và MariaDB.

## Chạy ứng dụng

- Java 17 trở lên.
- MariaDB tại `localhost:3306`, database `shoppingdb`, tài khoản `root`.
- Database cần hai bảng `publishers` và `books`, với khóa ngoại `books.publisher_id` trỏ tới `publishers.id`.
- Khai báo biến môi trường `DB_PASSWORD` nếu tài khoản database có mật khẩu.
- Chạy từ thư mục này bằng `./mvnw.cmd spring-boot:run` trên Windows.
- Ứng dụng dùng cổng `8085`.
- Mã hiện tại còn hai dependency Lombok trùng trong `pom.xml`. Maven Wrapper 3.10.0 sẽ báo lỗi này; cần giữ một dependency Lombok để build.

## API

- `GET /api/books`
- `GET /api/books/author/{author}`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`
- `GET /api/publishers`
- `GET /api/publishers/{id}`
- `GET /api/publishers/{publisherId}/books/search?author=Nguyen`