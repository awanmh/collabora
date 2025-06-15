import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getProjectById, getAllStudents, getAllLecturers } from "../../services/projectService";
import { toast } from "react-toastify";

// Modal dengan dropdown dari DB
const AddMemberModal = ({ isOpen, onClose, onAdd }) => {
  const [students, setStudents] = useState([]);
  const [lecturers, setLecturers] = useState([]);
  const [selectedMember, setSelectedMember] = useState("");

  useEffect(() => {
    if (isOpen) {
      getAllStudents().then(res => setStudents(res.data));
      getAllLecturers().then(res => setLecturers(res.data));
    }
  }, [isOpen]);

  const handleSubmit = () => {
    if (!selectedMember) return toast.error("Pilih anggota terlebih dahulu");
    const [type, id] = selectedMember.split("-");
    const data = { type, id: parseInt(id) };
    onAdd(data);
    setSelectedMember("");
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-60 flex justify-center items-center z-50">
      <div className="bg-white p-6 rounded-lg w-full max-w-md text-black">
        <h2 className="text-xl font-semibold mb-4">Tambah Anggota</h2>

        <select
          onChange={(e) => setSelectedMember(e.target.value)}
          value={selectedMember}
          className="w-full border p-2 mb-4 rounded"
        >
          <option value="">-- Pilih Anggota --</option>
          <optgroup label="Mahasiswa">
            {students.map((s) => (
              <option key={s.id} value={`student-${s.id}`}>
                {s.user.name} ({s.studentId})
              </option>
            ))}
          </optgroup>
          <optgroup label="Dosen">
            {lecturers.map((l) => (
              <option key={l.id} value={`lecturer-${l.id}`}>
                {l.user.name} ({l.lecturerId})
              </option>
            ))}
          </optgroup>
        </select>

        <div className="flex justify-end gap-2">
          <button
            onClick={onClose}
            className="bg-gray-400 px-4 py-2 rounded text-white"
          >
            Batal
          </button>
          <button
            onClick={handleSubmit}
            className="bg-blue-600 px-4 py-2 rounded text-white"
          >
            Tambah
          </button>
        </div>
      </div>
    </div>
  );
};

const ProjectDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [project, setProject] = useState(null);
  const [members, setMembers] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    const fetchProject = async () => {
      try {
        const response = await getProjectById(id);
        setProject(response.data);
        setMembers(response.data.members || []);
      } catch (err) {
        toast.error("Gagal memuat detail proyek");
      }
    };
    fetchProject();
  }, [id]);

  const handleAddMember = (member) => {
    // Simpan ke lokal sementara
    setMembers((prev) => [...prev, member]);
    toast.success("Anggota ditambahkan!");
    // TODO: kirim ke backend nanti
  };

  if (!project) return <p className="text-white">Loading...</p>;

  return (
    <div className="p-6 text-white">
      <button
        onClick={() => navigate("/projects")}
        className="mb-4 px-4 py-2 bg-yellow-400 text-black rounded hover:bg-yellow-500"
      >
        ← Kembali ke Project Board
      </button>

      <h1 className="text-3xl font-bold text-yellow-400">{project.title}</h1>
      <p className="mt-2 text-lightGray">{project.description}</p>

      {/* Tombol Tambah Anggota */}
      <div className="mt-6">
        <button
          onClick={() => setIsModalOpen(true)}
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          + Tambah Anggota
        </button>
      </div>

      {/* Daftar Anggota */}
      <div className="mt-4">
        <h3 className="text-xl font-semibold mb-2">Anggota Proyek</h3>
        {members.length === 0 ? (
          <p className="text-gray-400">Belum ada anggota</p>
        ) : (
          <ul className="list-disc list-inside">
            {members.map((m, i) => (
              <li key={i}>
                {m.name ? (
                  <>
                    <span className="font-medium">{m.name}</span> ({m.type})
                  </>
                ) : (
                  <span>
                    ID: {m.id}, Role: {m.type}
                  </span>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* Modal Tambah Anggota */}
      <AddMemberModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onAdd={handleAddMember}
      />
    </div>
  );
};

export default ProjectDetail;
