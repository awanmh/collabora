import { useEffect, useState } from "react";
import { getAllStudents, getAllLecturers } from "../../services/projectService";
import { toast } from "react-toastify";

/**
 * Komponen Modal untuk menambah anggota (Mahasiswa/Dosen) ke dalam proyek.
 * @param {object} props
 * @param {boolean} props.isOpen - Status untuk menampilkan atau menyembunyikan modal.
 * @param {Function} props.onClose - Fungsi untuk menutup modal.
 * @param {Function} props.onAdd - Fungsi callback yang dipanggil saat anggota ditambahkan, mengirimkan data anggota.
 */
const AddMemberModal = ({ isOpen, onClose, onAdd }) => {
  // =================================================================
  // State Management
  // =================================================================
  const [students, setStudents] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [selectedMember, setSelectedMember] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  // =================================================================
  // Data Fetching
  // =================================================================
  useEffect(() => {
    // Fungsi untuk mengambil data dari API
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const studentRes = await getAllStudents();
        const lecturerRes = await getAllLecturers();

        // Mengambil array dari dalam respons API.
        // Ganti 'students' atau 'lecturers' jika key di API Anda berbeda.
        setStudents(studentRes.data.students || []);
        setLecturers(lecturerRes.data.lecturers || []);

      } catch (error) {
        toast.error("Gagal mengambil daftar anggota.");
        console.error("API fetch error:", error);
      } finally {
        setIsLoading(false);
      }
    };

    // Hanya panggil API jika modal terbuka (untuk efisiensi)
    if (isOpen) {
      fetchData();
    }
  }, [isOpen]);

  // =================================================================
  // Handler & Submission Logic
  // =================================================================
  const handleSubmit = () => {
    if (!selectedMember) {
      return toast.warning("Pilih anggota terlebih dahulu");
    }

    // Pisahkan tipe (student/lecturer) dan id dari value
    const [type, id] = selectedMember.split("-");

    let memberData = null;
    let memberList = type === 'student' ? students : lecturers;
    const foundMember = memberList.find(member => member.id === parseInt(id));

    if (foundMember) {
      memberData = {
        id: foundMember.id,
        type: type,
        name: foundMember.user.name, // Asumsi nama ada di nested object 'user'
      };
      // Kirim objek data yang lengkap ke komponen parent
      onAdd(memberData);
      // Reset dan tutup modal setelah berhasil
      onClose();
      setSelectedMember("");
    } else {
      toast.error("Data anggota tidak ditemukan.");
    }
  };

  // Jangan render apapun jika modal tidak terbuka
  if (!isOpen) return null;

  // =================================================================
  // JSX Rendering
  // =================================================================
  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex justify-center items-center z-50">
      <div className="bg-gray-800 p-6 rounded-lg w-full max-w-md text-white shadow-lg">
        <h2 className="text-xl font-semibold mb-4 text-yellow-400">Tambah Anggota</h2>

        {isLoading ? (
          <p className="text-center p-4">Memuat data...</p>
        ) : (
          <select
            onChange={(e) => setSelectedMember(e.target.value)}
            value={selectedMember}
            className="w-full border p-2 mb-4 rounded bg-gray-700 border-gray-600 focus:outline-none focus:ring-2 focus:ring-yellow-400"
          >
            <option value="">-- Pilih Anggota --</option>
            
            <optgroup label="Mahasiswa">
              {students.length > 0 ? (
                students.map((s) => (
                  <option key={s.id} value={`student-${s.id}`}>
                    {s.user.name} ({s.studentId})
                  </option>
                ))
              ) : (
                <option disabled>Tidak ada data mahasiswa</option>
              )}
            </optgroup>

            <optgroup label="Dosen">
              {lecturers.length > 0 ? (
                lecturers.map((l) => (
                  <option key={l.id} value={`lecturer-${l.id}`}>
                    {l.user.name} ({l.lecturerId})
                  </option>
                ))
              ) : (
                <option disabled>Tidak ada data dosen</option>
              )}
            </optgroup>
          </select>
        )}

        <div className="flex justify-end gap-3 mt-4">
          <button
            onClick={onClose}
            className="bg-gray-500 px-4 py-2 rounded hover:bg-gray-600 transition-colors"
          >
            Batal
          </button>
          <button
            onClick={handleSubmit}
            disabled={isLoading}
            className="bg-blue-600 px-4 py-2 rounded hover:bg-blue-700 transition-colors disabled:opacity-50"
          >
            Tambah
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMemberModal;