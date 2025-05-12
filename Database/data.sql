-- Mengisi data awal untuk testing
-- Pastikan schema.sql sudah dijalankan sebelumnya

USE collabora;

-- Data pengguna (password dienkripsi dengan bcrypt)
INSERT INTO user (username, password, role) VALUES
('student1', '$2a$10$XURPShQ5uMj9a0fS5c3N3e3X3Y2Qz4t0c4R8Qz6g8Qz4t0c4R8Qz6', 'STUDENT'),
('lecturer1', '$2a$10$XURPShQ5uMj9a0fS5c3N3e3X3Y2Qz4t0c4R8Qz6g8Qz4t0c4R8Qz6', 'LECTURER');

-- Data mahasiswa
INSERT INTO student (id, student_id) VALUES
(1, '1203230010');

-- Data dosen
INSERT INTO lecturer (id, lecturer_id) VALUES
(2, 'LEC001');

-- Data proyek
INSERT INTO project (title, description) VALUES
('Proyek TUBES PBO', 'Aplikasi manajemen tugas kelompok mahasiswa');

-- Data anggota proyek
INSERT INTO project_members (project_id, user_id) VALUES
(1, 1), -- student1 di proyek
(1, 2); -- lecturer1 sebagai pengawas

-- Data tugas
INSERT INTO task (title, description, deadline, status, is_milestone, project_id, assigned_to_id) VALUES
('Desain Database', 'Membuat skema database', '2025-05-15 23:59:59', 'NOT_STARTED', 1, 1, 1),
('Implementasi Backend', 'Membuat endpoint API', '2025-05-20 23:59:59', 'IN_PROGRESS', 0, 1, 1);

-- Data komentar
INSERT INTO comment (content, task_id, author_id, parent_comment_id) VALUES
('Sudah selesai membuat tabel?', 1, 2, NULL),
('Belum, masih desain ERD', 1, 1, 1);

-- Data notifikasi
INSERT INTO notification (message, is_read, user_id) VALUES
('Anda ditugaskan pada "Desain Database"', 0, 1),
('Komentar baru pada tugas "Desain Database"', 0, 1);