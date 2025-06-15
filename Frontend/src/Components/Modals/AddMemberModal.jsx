import { useEffect, useState } from "react";
import { getAllStudents, getAllLecturers } from "../../services/projectService";
import { toast } from "react-toastify";

const AddMemberModal = ({ isOpen, onClose, onAddMember }) => {
  const [students, setStudents] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [selectedMember, setSelectedMember] = useState("");

  useEffect(() => {
    if (isOpen) {
      getAllStudents()
        .then((res) => setStudents(res.data))
        .catch(() => toast.error("Gagal mengambil data mahasiswa"));

      getAllLecturers()
        .then((res) => setLecturers(res.data))
        .catch(() => toast.error("Gagal mengambil data dosen"));
    }
  }, [isOpen]);

  const handleAdd = () => {
    if (!selectedMember) {
      toast.warning("Pilih anggota terlebih dahulu");
      return;
    }

    onAddMember(selectedMember); // misal: "student-3" atau "lecturer-2"
    onClose();
    setSelectedMember("");
  };

  return (
    <div className={`modal fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 ${isOpen ? "block" : "hidden"}`}>
      <div className="modal-content bg-navy p-4 rounded text-white w-96">
        <h2 className="text-lg font-bold mb-4">Tambah Anggota</h2>

        <select
          onChange={(e) => setSelectedMember(e.target.value)}
          value={selectedMember}
          className="text-black p-2 rounded w-full mb-4"
        >
          <option value="">-- Pilih Anggota --</option>
          <optgroup label="Mahasiswa">
            {students.map((s) => (
              <option key={s.id} value={`student-${s.id}`}>
                {s.user?.fullName || s.user?.name} ({s.studentId})
              </option>
            ))}
          </optgroup>
          <optgroup label="Dosen">
            {lecturers.map((l) => (
              <option key={l.id} value={`lecturer-${l.id}`}>
                {l.user?.fullName || l.user?.name} ({l.lecturerId})
              </option>
            ))}
          </optgroup>
        </select>

        <div className="flex justify-end gap-2">
          <button onClick={onClose} className="px-4 py-2 bg-gray-400 rounded hover:bg-gray-500">
            Batal
          </button>
          <button
            onClick={handleAdd}
            className="bg-yellow-400 text-navy px-4 py-2 rounded hover:bg-yellow-500"
          >
            Tambah
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddMemberModal;
