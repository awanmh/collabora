-- Membuat database Collabora
CREATE DATABASE IF NOT EXISTS collabora;
USE collabora;

-- Tabel untuk pengguna (abstrak, untuk mahasiswa dan dosen)
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dtype VARCHAR(31) NOT NULL COMMENT 'Tipe entitas (Student atau Lecturer)',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'Username unik untuk login',
    password VARCHAR(255) NOT NULL COMMENT 'Password terenkripsi (bcrypt)',
    role ENUM('STUDENT', 'LECTURER') NOT NULL COMMENT 'Peran pengguna',
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk data pengguna';

-- Tabel untuk mahasiswa (mewarisi user)
CREATE TABLE student (
    id BIGINT PRIMARY KEY,
    student_id VARCHAR(20) NOT NULL UNIQUE COMMENT 'NIM mahasiswa',
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk data tambahan mahasiswa';

-- Tabel untuk dosen (mewarisi user)
CREATE TABLE lecturer (
    id BIGINT PRIMARY KEY,
    lecturer_id VARCHAR(20) NOT NULL UNIQUE COMMENT 'Kode dosen',
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk data tambahan dosen';

-- Tabel untuk proyek
CREATE TABLE project (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL COMMENT 'Judul proyek',
    description TEXT COMMENT 'Deskripsi proyek'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk data proyek';

-- Tabel penghubung untuk anggota proyek (many-to-many)
CREATE TABLE project_members (
    project_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, user_id),
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX idx_project_id (project_id),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel penghubung untuk anggota proyek';

-- Tabel untuk tugas
CREATE TABLE task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL COMMENT 'Judul tugas',
    description TEXT COMMENT 'Deskripsi tugas',
    deadline DATETIME COMMENT 'Tenggat waktu tugas',
    status ENUM('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED') NOT NULL DEFAULT 'NOT_STARTED' COMMENT 'Status tugas',
    is_milestone TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Apakah tugas adalah milestone',
    project_id BIGINT NOT NULL,
    assigned_to_id BIGINT,
    FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to_id) REFERENCES user(id) ON DELETE SET NULL,
    INDEX idx_project_id (project_id),
    INDEX idx_assigned_to_id (assigned_to_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk data tugas';

-- Tabel untuk komentar
CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL COMMENT 'Isi komentar',
    task_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    parent_comment_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Waktu pembuatan komentar',
    FOREIGN KEY (task_id) REFERENCES task(id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (parent_comment_id) REFERENCES comment(id) ON DELETE CASCADE,
    INDEX idx_task_id (task_id),
    INDEX idx_parent_comment_id (parent_comment_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk komentar pada tugas';

-- Tabel untuk notifikasi
CREATE TABLE notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    message VARCHAR(255) NOT NULL COMMENT 'Pesan notifikasi',
    is_read TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'Status baca notifikasi',
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Waktu pembuatan notifikasi',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_is_read (is_read)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabel untuk notifikasi pengguna';

-- Data pengujian untuk tabel user dan student
INSERT INTO user (dtype, username, password, role) 
VALUES ('Student', 'admin', '$2a$10$XURPShQ5uMj9V6h4V8aG5eZ/3jB1M3m1eI3jB1M3m1eI3jB1M3m1e', 'STUDENT');
INSERT INTO student (id, student_id) 
VALUES ((SELECT id FROM user WHERE username = 'admin'), 'NIM12345');

-- Data pengujian untuk tabel user dan lecturer
INSERT INTO user (dtype, username, password, role) 
VALUES ('Lecturer', 'dosen', '$2a$10$XURPShQ5uMj9V6h4V8aG5eZ/3jB1M3m1eI3jB1M3m1eI3jB1M3m1e', 'LECTURER');
INSERT INTO lecturer (id, lecturer_id) 
VALUES ((SELECT id FROM user WHERE username = 'dosen'), 'DOSEN001');

-- Data pengujian untuk tabel project
INSERT INTO project (title, description) 
VALUES ('Proyek Capstone', 'Proyek pengembangan aplikasi manajemen tugas');

-- Data pengujian untuk tabel project_members
INSERT INTO project_members (project_id, user_id) 
VALUES ((SELECT id FROM project WHERE title = 'Proyek Capstone'), 
        (SELECT id FROM user WHERE username = 'admin'));

-- Data pengujian untuk tabel task
INSERT INTO task (title, description, deadline, status, is_milestone, project_id, assigned_to_id) 
VALUES ('Desain UI', 'Membuat desain antarmuka pengguna', '2025-06-01 23:59:59', 'NOT_STARTED', 0, 
        (SELECT id FROM project WHERE title = 'Proyek Capstone'), 
        (SELECT id FROM user WHERE username = 'admin'));

-- Data pengujian untuk tabel comment
INSERT INTO comment (content, task_id, author_id) 
VALUES ('Tolong prioritaskan desain dashboard', 
        (SELECT id FROM task WHERE title = 'Desain UI'), 
        (SELECT id FROM user WHERE username = 'dosen'));

-- Data pengujian untuk tabel notification
INSERT INTO notification (message, is_read, user_id) 
VALUES ('Anda ditugaskan ke tugas Desain UI', 0, 
        (SELECT id FROM user WHERE username = 'admin'));