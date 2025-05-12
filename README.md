# Collabora - Aplikasi Manajemen Proyek dan Tugas

**Collabora** adalah aplikasi berbasis web untuk manajemen proyek dan tugas yang dirancang untuk memfasilitasi kolaborasi antara mahasiswa dan dosen dalam proyek akademik, seperti tugas besar atau capstone. Aplikasi ini mendukung fitur autentikasi, manajemen proyek, tugas, komentar, notifikasi, dan visualisasi timeline proyek. Backend dibangun menggunakan **Spring Boot** (Java), frontend menggunakan **React.js**, dan database menggunakan **MySQL**.

Dokumentasi ini menjelaskan cara menginstal, menjalankan, dan menguji aplikasi, serta struktur proyek dan teknologi yang digunakan. Collabora dikembangkan sebagai bagian dari Tugas Besar Pemrograman Berorientasi Objek (PBO).

## Daftar Isi
1. [Fitur Aplikasi](#fitur-aplikasi)
2. [Prasyarat](#prasyarat)
3. [Instalasi](#instalasi)
4. [Struktur Proyek](#struktur-proyek)
5. [Menjalankan Aplikasi](#menjalankan-aplikasi)
6. [Pengujian dengan Postman](#pengujian-dengan-postman)
7. [Catatan Error](#Catatan-Error)
8. [Kontribusi](#kontribusi)


## Fitur Aplikasi
Berdasarkan ide/proposal tugas besar, Collabora menyediakan fitur berikut:
- **Autentikasi dan Otorisasi**:
  - Login dan registrasi untuk mahasiswa dan dosen.
  - Autentikasi berbasis JWT (JSON Web Token).
  - Role-based access control (STUDENT, LECTURER).
- **Manajemen Proyek**:
  - Membuat, mengedit, dan menghapus proyek.
  - Menambahkan anggota proyek (mahasiswa/dosen).
- **Manajemen Tugas**:
  - Membuat, mengedit, dan menghapus tugas dalam proyek.
  - Menetapkan tugas ke anggota proyek.
  - Melacak status tugas (NOT_STARTED, IN_PROGRESS, COMPLETED) dan milestone.
- **Komentar**:
  - Menambahkan komentar pada tugas untuk diskusi.
  - Mendukung komentar bersarang (replies).
- **Notifikasi**:
  - Notifikasi real-time untuk tugas baru, pembaruan status, atau komentar.
- **Visualisasi Timeline**:
  - Menampilkan timeline proyek dengan milestone dan tenggat waktu tugas.
- **Antarmuka Pengguna**:
  - Dashboard interaktif dengan kanban board untuk tugas.
  - Modal untuk membuat proyek/tugas.
  - Notifikasi toast untuk pembaruan.

## Prasyarat
Untuk menjalankan Collabora, Anda memerlukan perangkat lunak berikut:

### Backend
- **Java Development Kit (JDK)**: Versi 17 atau lebih tinggi.
- **Maven**: Build tool untuk mengelola dependensi dan membangun proyek.
- **MySQL**: Database relasional (versi 8.0 atau lebih tinggi).
- **IDE**: IntelliJ IDEA (disarankan) atau Eclipse.

### Frontend
- **Node.js**: Versi 16 atau lebih tinggi (termasuk npm).
- **Text Editor**: Visual Studio Code (disarankan).

### Pengujian
- **Postman**: Untuk menguji API REST.

### Sistem Operasi
- Windows, macOS, atau Linux.

## Instalasi
Ikuti langkah-langkah berikut untuk menginstal dependensi dan menyiapkan lingkungan pengembangan.

### 1. Instalasi JDK
1. Unduh JDK 17 dari [Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) atau [OpenJDK](https://adoptium.net/).
2. Instal JDK dan atur variabel lingkungan `JAVA_HOME`:
   - Windows: Tambahkan `JAVA_HOME` ke System Environment Variables (contoh: `C:\Program Files\Java\jdk-17`).
   - Linux/macOS: Tambahkan ke `~/.bashrc` atau `~/.zshrc`:
     ```bash
     export JAVA_HOME=/path/to/jdk-17
     export PATH=$JAVA_HOME/bin:$PATH
     ```
3. Verifikasi instalasi:
   ```bash
   java -version
   ```
   Output: `java version "17.0.x"`.

### 2. Instalasi Maven
1. Unduh Maven dari [Apache Maven](https://maven.apache.org/download.cgi).
2. Ekstrak ke direktori (contoh: `C:\maven`).
3. Tambahkan Maven ke `PATH`:
   - Windows: Tambahkan `C:\maven\bin` ke System Environment Variables.
   - Linux/macOS: Tambahkan ke `~/.bashrc` atau `~/.zshrc`:
     ```bash
     export PATH=/path/to/maven/bin:$PATH
     ```
4. Verifikasi instalasi:
   ```bash
   mvn -version
   ```
   Output: `Apache Maven 3.x.x`.

### 3. Instalasi MySQL
1. Unduh MySQL Community Server dari [MySQL](https://dev.mysql.com/downloads/mysql/).
2. Instal MySQL dan konfigurasikan:
   - Buat pengguna root tanpa kata sandi (atau atur kata sandi dan perbarui di `application.properties`).
   - Pastikan MySQL berjalan:
     ```bash
     mysqladmin -u root -p status
     ```
3. Buat database `collabora`:
   ```bash
   mysql -u root -p
   ```
   ```sql
   CREATE DATABASE collabora;
   ```

### 4. Instalasi Node.js
1. Unduh Node.js dari [Node.js](https://nodejs.org/).
2. Instal dan verifikasi:
   ```bash
   node -v
   npm -v
   ```
   Output: `v16.x.x` (atau lebih tinggi) untuk Node.js, dan versi npm.

### 5. Instalasi Postman
1. Unduh Postman dari [Postman](https://www.postman.com/downloads/).
2. Instal dan buka untuk pengujian API.

### 6. Kloning Repositori
1. Klon repositori proyek (jika ada) atau buat struktur proyek seperti dijelaskan di [Struktur Proyek](#struktur-proyek).
   ```bash
   git clone <repository-url>
   cd tubes-pbo
   ```
2. Pastikan struktur direktori sesuai:
   ```
   tubes-pbo/
   ├── backend/
   ├── frontend/
   └── database/
   ```

## Struktur Proyek
Struktur proyek Collabora dirancang untuk memisahkan backend, frontend, dan database. Berikut adalah penjelasan setiap komponen:

```
tubes-pbo/
├── backend/                 # Spring Boot Backend
│   ├── src/main/java/com/manajemennilai/
│   │   ├── config/         # Konfigurasi aplikasi
│   │   │   ├── SecurityConfig.java        # Konfigurasi Spring Security dan JWT
│   │   │   ├── JwtAuthFilter.java         # Filter untuk validasi JWT
│   │   │   ├── OpenAPIConfig.java         # Konfigurasi Swagger/OpenAPI
│   │   │   └── CorsConfig.java            # Konfigurasi CORS
│   │   ├── controller/     # REST API Controllers
│   │   │   ├── AuthController.java        # Endpoint autentikasi (login, register)
│   │   │   ├── ProjectController.java     # Endpoint manajemen proyek
│   │   │   ├── TaskController.java        # Endpoint manajemen tugas
│   │   │   ├── CommentController.java     # Endpoint manajemen komentar
│   │   │   └── NotificationController.java # Endpoint manajemen notifikasi
│   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── request/
│   │   │   │   ├── AuthRequest.java       # Request untuk login
│   │   │   │   ├── RegisterRequest.java   # Request untuk registrasi
│   │   │   │   ├── CreateProjectRequest.java # Request untuk membuat proyek
│   │   │   │   ├── CreateTaskRequest.java # Request untuk membuat tugas
│   │   │   │   └── CreateCommentRequest.java # Request untuk membuat komentar
│   │   │   └── response/
│   │   │       ├── AuthResponse.java      # Respons autentikasi
│   │   │       ├── ProjectResponse.java   # Respons proyek
│   │   │       ├── TaskResponse.java      # Respons tugas
│   │   │       ├── CommentResponse.java   # Respons komentar
│   │   │       └── NotificationResponse.java # Respons notifikasi
│   │   ├── exception/      # Penanganan error
│   │   │   ├── CustomExceptionHandler.java # Handler untuk exception
│   │   │   ├── errors/
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── AuthenticationFailedException.java
│   │   │   │   ├── OperationNotAllowedException.java
│   │   │   │   ├── ValidationException.java
│   │   │   │   └── DatabaseException.java
│   │   │   └── response/
│   │   │       └── ErrorResponse.java     # Format respons error
│   │   ├── model/          # Entitas JPA
│   │   │   ├── User.java                  # Entitas pengguna (abstrak)
│   │   │   ├── Student.java               # Entitas mahasiswa
│   │   │   ├── Lecturer.java              # Entitas dosen
│   │   │   ├── Project.java               # Entitas proyek
│   │   │   ├── Task.java                  # Entitas tugas
│   │   │   ├── Comment.java               # Entitas komentar
│   │   │   └── Notification.java          # Entitas notifikasi
│   │   ├── repository/     # JPA Repositories
│   │   │   ├── UserRepository.java
│   │   │   ├── ProjectRepository.java
│   │   │   ├── TaskRepository.java
│   │   │   ├── CommentRepository.java
│   │   │   └── NotificationRepository.java
│   │   ├── service/        # Logika bisnis
│   │   │   ├── AuthService.java           # Service autentikasi
│   │   │   ├── ProjectService.java        # Service manajemen proyek
│   │   │   ├── TaskService.java           # Service manajemen tugas
│   │   │   ├── CommentService.java        # Service manajemen komentar
│   │   │   └── NotificationService.java   # Service manajemen notifikasi
│   │   ├── security/       # Komponen keamanan
│   │   │   ├── JwtUtils.java              # Utilitas untuk JWT
│   │   │   ├── UserDetailsCustom.java     # Implementasi UserDetails
│   │   │   └── UserDetailsServiceImpl.java # Service untuk UserDetails
│   │   ├── payload/        # Objek tambahan
│   │   │   ├── ValidationMessages.java    # Pesan validasi
│   │   │   ├── CreateTaskRequest.java     # Payload untuk tugas
│   │   │   ├── UpdateTaskStatusRequest.java # Payload untuk update status
│   │   │   └── ApiResponse.java           # Respons API generik
│   │   └── Application.java               # Entry point aplikasi
│   ├── src/main/resources/
│   │   ├── application.properties         # Konfigurasi Spring dan DB
│   │   └── schema.sql                     # Skema database
│   └── pom.xml                            # Dependensi Maven
├── frontend/               # React.js Frontend
│   ├── public/
│   │   ├── index.html                     # File HTML utama
│   │   └── favicon.ico                    # Ikon aplikasi
│   ├── src/
│   │   ├── components/
│   │   │   ├── Auth/
│   │   │   │   ├── Login.jsx              # Komponen login
│   │   │   │   └── Register.jsx           # Komponen registrasi
│   │   │   ├── Dashboard/
│   │   │   │   ├── ProjectBoard.jsx       # Kanban board proyek
│   │   │   │   ├── TaskCard.jsx           # Kartu tugas
│   │   │   │   └── Timeline.jsx           # Timeline proyek
│   │   │   ├── Modals/
│   │   │   │   ├── CreateProjectModal.jsx # Modal membuat proyek
│   │   │   │   └── CreateTaskModal.jsx    # Modal membuat tugas
│   │   │   └── Shared/
│   │   │       ├── Navbar.jsx             # Navigasi
│   │   │       ├── NotificationToast.jsx  # Notifikasi toast
│   │   │       └── PrivateRoute.jsx       # Rute terproteksi
│   │   ├── contexts/
│   │   │   └── AuthContext.jsx            # Konteks autentikasi
│   │   ├── hooks/
│   │   │   └── useApi.jsx                 # Hook untuk API calls
│   │   ├── services/
│   │   │   ├── api.js                     # Konfigurasi Axios
│   │   │   ├── authService.js             # Service autentikasi
│   │   │   └── projectService.js          # Service proyek/tugas
│   │   ├── utils/
│   │   │   ├── constants.js               # Konstanta
│   │   │   └── helpers.js                 # Fungsi helper
│   │   ├── App.jsx                        # Komponen utama
│   │   ├── index.js                       # Entry point
│   │   └── index.css                      # CSS global (Tailwind)
│   ├── package.json                       # Dependensi dan skrip
│   └── tailwind.config.js                 # Konfigurasi Tailwind CSS
└── database/               # SQL Scripts
    ├── schema.sql          # Skema tabel
    └── data.sql            # Data contoh (opsional)
```

### Penjelasan Komponen
- **Backend**:
  - **config/**: Berisi konfigurasi seperti Spring Security (`SecurityConfig.java`, `JwtAuthFilter.java`) dan CORS.
  - **controller/**: Menangani permintaan HTTP (REST API) untuk autentikasi, proyek, tugas, komentar, dan notifikasi.
  - **dto/**: Objek untuk request dan response API, seperti `AuthRequest.java` dan `AuthResponse.java`.
  - **exception/**: Penanganan error kustom untuk respons yang konsisten.
  - **model/**: Entitas JPA yang memetakan tabel database (`User`, `Project`, `Task`, dll.).
  - **repository/**: Antarmuka JPA untuk operasi database.
  - **service/**: Logika bisnis untuk autentikasi, manajemen proyek, dll.
  - **security/**: Komponen keamanan seperti `JwtUtils.java` untuk pengelolaan token JWT.
  - **payload/**: Objek tambahan untuk validasi dan respons API.
  - **resources/**: Berisi `application.properties` untuk konfigurasi dan `schema.sql` untuk skema database.

- **Frontend**:
  - **components/**: Komponen UI React, termasuk halaman login, dashboard, dan modal.
  - **contexts/**: Konteks untuk mengelola state autentikasi.
  - **hooks/**: Custom hook untuk memanggil API.
  - **services/**: Konfigurasi Axios untuk komunikasi dengan backend.
  - **utils/**: Konstanta dan fungsi pembantu.
  - **App.jsx**: Mengatur routing dan layout utama.
  - **index.js**: Entry point aplikasi React.

- **Database**:
  - **schema.sql**: Berisi perintah DDL untuk membuat tabel seperti `user`, `project`, `task`, dll.
  - **data.sql**: (Opsional) Berisi data pengujian untuk mengisi tabel.

## Menjalankan Aplikasi

### 1. Menyiapkan Database
1. Pastikan MySQL berjalan:
   ```bash
   mysqladmin -u root -p status
   ```
2. Buat database `collabora`:
   ```bash
   mysql -u root -p
   ```
   ```sql
   CREATE DATABASE collabora;
   ```
3. Jalankan `schema.sql` untuk membuat tabel:
   ```bash
   mysql -u root -p collabora < database/schema.sql
   ```
4. (Opsional) Jalankan `data.sql` untuk data pengujian:
   ```bash
   mysql -u root -p collabora < database/data.sql
   ```

### 2. Menjalankan Backend
1. Masuk ke direktori backend:
   ```bash
   cd tubes-pbo/backend
   ```
2. Build proyek menggunakan Maven:
   ```bash
   mvn clean install
   ```
3. Jalankan aplikasi:
   ```bash
   mvn spring-boot:run
   ```
   Atau jalankan dari IntelliJ IDEA dengan mengklik **Run** pada `Application.java`.
4. Verifikasi bahwa backend berjalan:
   - Buka `http://localhost:8081/api/swagger-ui.html` untuk melihat dokumentasi API.
   - Log akan menampilkan:
     ```
     INFO  o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8081 (http)
     INFO  com.manajemennilai.Application           : Started Application in X.XXX seconds
     ```

### 3. Menjalankan Frontend
1. Masuk ke direktori frontend:
   ```bash
   cd tubes-pbo/frontend
   ```
2. Instal dependensi:
   ```bash
   npm install
   ```
3. Jalankan aplikasi:
   ```bash
   npm start
   ```
4. Buka `http://localhost:3000` di browser untuk mengakses aplikasi.
5. Verifikasi bahwa frontend terhubung ke backend:
   - Coba login dengan kredensial dari `schema.sql` (misalnya, `username: admin`, `password: password`).

### 4. Konfigurasi Tambahan
- **application.properties** (`backend/src/main/resources/application.properties`):
  - Sesuaikan konfigurasi database jika menggunakan kata sandi MySQL:
    ```properties
    spring.datasource.password=your_mysql_password
    ```
  - Pastikan `jwt.secret` aman untuk produksi:
    ```properties
    jwt.secret=your_secure_secret_key
    ```
- **CORS**: Backend sudah dikonfigurasi untuk mengizinkan permintaan dari `http://localhost:3000`.

## Pengujian dengan Postman
Gunakan Postman untuk menguji endpoint API backend. Berikut adalah contoh pengujian untuk fitur utama:

### 1. Login
- **Method**: POST
- **URL**: `http://localhost:8081/api/auth/login`
- **Headers**:
  ```
  Content-Type: application/json
  ```
- **Body** (raw, JSON):
  ```json
  {
    "username": "admin",
    "password": "password"
  }
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "admin",
    "role": "STUDENT"
  }
  ```
- **Catatan**: Simpan token untuk permintaan yang memerlukan autentikasi.

### 2. Registrasi
- **Method**: POST
- **URL**: `http://localhost:8081/api/auth/register`
- **Headers**:
  ```
  Content-Type: application/json
  ```
- **Body** (raw, JSON):
  ```json
  {
    "username": "mahasiswa1",
    "password": "password",
    "role": "STUDENT",
    "studentId": "NIM67890"
  }
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "mahasiswa1",
    "role": "STUDENT"
  }
  ```

### 3. Membuat Proyek
- **Method**: POST
- **URL**: `http://localhost:8081/api/projects`
- **Headers**:
  ```
  Content-Type: application/json
  Authorization: Bearer <token>
  ```
- **Body** (raw, JSON):
  ```json
  {
    "title": "Proyek Baru",
    "description": "Deskripsi proyek baru"
  }
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  {
    "id": 2,
    "title": "Proyek Baru",
    "description": "Deskripsi proyek baru"
  }
  ```

### 4. Membuat Tugas
- **Method**: POST
- **URL**: `http://localhost:8081/api/tasks`
- **Headers**:
  ```
  Content-Type: application/json
  Authorization: Bearer <token>
  ```
- **Body** (raw, JSON):
  ```json
  {
    "title": "Desain UI Baru",
    "description": "Membuat desain antarmuka",
    "deadline": "2025-06-01T23:59:59",
    "projectId": 1,
    "assignedToId": 1
  }
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  {
    "id": 1,
    "title": "Desain UI Baru",
    "status": "NOT_STARTED",
    "isMilestone": false
  }
  ```

### 5. Menambahkan Komentar
- **Method**: POST
- **URL**: `http://localhost:8081/api/comments`
- **Headers**:
  ```
  Content-Type: application/json
  Authorization: Bearer <token>
  ```
- **Body** (raw, JSON):
  ```json
  {
    "content": "Prioritaskan desain dashboard",
    "taskId": 1
  }
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  {
    "id": 1,
    "content": "Prioritaskan desain dashboard",
    "taskId": 1,
    "authorId": 1
  }
  ```

### 6. Mendapatkan Notifikasi
- **Method**: GET
- **URL**: `http://localhost:8081/api/notifications`
- **Headers**:
  ```
  Authorization: Bearer <token>
  ```
- **Respons yang Diharapkan** (200 OK):
  ```json
  [
    {
      "id": 1,
      "message": "Anda ditugaskan ke tugas Desain UI",
      "isRead": false
    }
  ]
  ```

### Catatan Pengujian
- Gunakan token JWT dari respons login untuk permintaan yang memerlukan autentikasi.
- Jika mendapatkan HTTP 403 Forbidden, periksa:
  - Apakah token valid (belum kedaluwarsa).
  - Apakah URL menggunakan prefiks `/api` (misalnya, `/api/auth/login`).
- Jika mendapatkan HTTP 500, periksa log backend untuk stack trace.


## Catatan Error
Disini digunakan untuk menjelaskan terkait error di aplikasi sejauh mana :
1. Sejauh ini saya telah membuat full dari Backend, Database, dan Frontend:
- Untuk error terjadi masih di backend karena program dijalankan menggunakan backend terlebih dahulu
- Error terjadi bukan di codingan namun ketika program di jalankan ada kendala error yaitu seperti "403 forbidden", 
  "404 not found" dll
- kemungkinan coba otak atik bagian backend karena kemungkinan ada kesalahan logika yang menyebabkan terjadi error di 
  server
- untuk suta mungkin bisa perbaiki/troubleshooting di bagian file backend
2. Untuk database mungkin sejauh yang saya buat masih berjalan dengan baik tidak ada kendala apapun
3. Untuk frontend mungkin sejauh yang saya coba sudah bisa menampilkan layar login dan register namun saya masih belum 
   melihat lebih jauh ui nya
4. untuk ai rekomendasi dari saya bisa menggunakan : grok dan deepsek (diutamakan), chatgpt dan gemini(untuk tambahan)

## Kontribusi
Kontribusi KELOMPOK manajemen nilai (collabora):
1. Setiawan Muhammad :
- Backend dan Database, serta kerangka frontend sementara
- Dokumentasi GITHUB
2. NAMA_KALIAN
3. NAMA_KALIAN
4. NAMA_KALIAN
5. NAMA_KALIAN

