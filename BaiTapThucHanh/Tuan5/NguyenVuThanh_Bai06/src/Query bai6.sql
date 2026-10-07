CREATE DATABASE IF NOT EXISTS quanlydanhmuc
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE quanlydanhmuc;

-- =========================
-- BẢNG DANH MỤC
-- =========================
CREATE TABLE IF NOT EXISTS DANHMUC (
    MADM INT AUTO_INCREMENT PRIMARY KEY,
    TENDANHMUC VARCHAR(100) NOT NULL,
    NGUOIQUANLY VARCHAR(100),
    GHICHU VARCHAR(255)
) ENGINE=InnoDB;

-- =========================
-- BẢNG TIN TỨC
-- Quan hệ DANHMUC 1 - N TINTUC
-- =========================
CREATE TABLE IF NOT EXISTS TINTUC (
    MATT INT AUTO_INCREMENT PRIMARY KEY,
    TIEUDE VARCHAR(255) NOT NULL,
    NOIDUNGTT TEXT,
    LIENKET VARCHAR(500),
    MADM INT NOT NULL,

    CONSTRAINT FK_TINTUC_DANHMUC
        FOREIGN KEY (MADM)
        REFERENCES DANHMUC(MADM)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB;

-- =========================
-- DỮ LIỆU DANH MỤC
-- =========================
INSERT INTO DANHMUC (TENDANHMUC, NGUOIQUANLY, GHICHU)
VALUES
('Công nghệ', 'Nguyễn Văn An', 'Tin tức công nghệ'),
('Giáo dục', 'Trần Văn Bình', 'Tin tức giáo dục'),
('Thể thao', 'Lê Văn Cường', 'Tin tức thể thao'),
('Kinh tế', 'Phạm Văn Dũng', 'Tin tức kinh tế'),
('Giải trí', 'Hoàng Văn Em', 'Tin tức giải trí');

-- =========================
-- DỮ LIỆU TIN TỨC
-- =========================
INSERT INTO TINTUC (TIEUDE, NOIDUNGTT, LIENKET, MADM)
VALUES
('Trí tuệ nhân tạo đang phát triển mạnh',
 'Trí tuệ nhân tạo đang phát triển nhanh chóng và được ứng dụng trong nhiều lĩnh vực.',
 'https://example.com/ai',
 1),

('Sự phát triển của Java',
 'Java tiếp tục là một trong những ngôn ngữ lập trình phổ biến.',
 'https://example.com/java',
 1),

('Công nghệ mới năm 2026',
 'Nhiều công nghệ mới đang được nghiên cứu và phát triển.',
 'https://example.com/cong-nghe',
 1),

('Khai giảng năm học mới',
 'Năm học mới chính thức bắt đầu với nhiều hoạt động giáo dục.',
 'https://example.com/giao-duc',
 2),

('Phương pháp học tập hiệu quả',
 'Các phương pháp học tập giúp sinh viên nâng cao kết quả học tập.',
 'https://example.com/hoc-tap',
 2),

('Giải bóng đá quốc gia',
 'Giải bóng đá quốc gia đang diễn ra với nhiều trận đấu hấp dẫn.',
 'https://example.com/bong-da',
 3),

('Đội tuyển chuẩn bị thi đấu',
 'Đội tuyển đang tích cực chuẩn bị cho giải đấu sắp tới.',
 'https://example.com/doi-tuyen',
 3),

('Thị trường kinh tế năm 2026',
 'Tình hình kinh tế có nhiều thay đổi trong năm 2026.',
 'https://example.com/kinh-te',
 4),

('Xu hướng kinh doanh mới',
 'Nhiều xu hướng kinh doanh mới đang được doanh nghiệp quan tâm.',
 'https://example.com/kinh-doanh',
 4),

('Bộ phim mới ra mắt',
 'Một bộ phim mới đang nhận được sự quan tâm của khán giả.',
 'https://example.com/phim',
 5);

-- =========================
-- KIỂM TRA
-- =========================
SELECT * FROM DANHMUC;

SELECT * FROM TINTUC;

-- Kiểm tra quan hệ 1-N
SELECT 
    dm.MADM,
    dm.TENDANHMUC,
    tt.MATT,
    tt.TIEUDE,
    tt.NOIDUNGTT,
    tt.LIENKET
FROM DANHMUC dm
LEFT JOIN TINTUC tt 
    ON dm.MADM = tt.MADM
ORDER BY dm.MADM, tt.MATT;
