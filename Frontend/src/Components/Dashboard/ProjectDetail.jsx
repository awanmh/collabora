// src/components/Dashboard/ProjectDetail.jsx

import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState, useCallback } from "react";
import { toast } from "react-toastify";
import { getProjectById, addMemberToProject } from "../../services/projectService";
import { getTasksByProject, createTask } from "../../services/taskService";
import AddMemberModal from "../Modals/AddMemberModal";
import CreateTaskModal from "../Modals/CreateTaskModal";
import TaskCard from "./TaskCard";

const ProjectDetail = () => {
  const { id: projectId } = useParams();
  const navigate = useNavigate();

  const [project, setProject] = useState(null);
  const [members, setMembers] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [isMemberModalOpen, setIsMemberModalOpen] = useState(false);
  const [isTaskModalOpen, setIsTaskModalOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);

  const fetchData = useCallback(async () => {
    try {
      const [projectResponse, tasksResponse] = await Promise.all([
        getProjectById(projectId),
        getTasksByProject(projectId)
      ]);

      setProject(projectResponse.data);
      setMembers(projectResponse.data.members || []);
      setTasks(tasksResponse.data);

    } catch (err) {
      toast.error("Gagal memuat data proyek.");
      console.error(err);
      navigate('/projects');
    } finally {
      setIsLoading(false);
    }
  }, [projectId, navigate]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  const handleCreateTask = async (taskData) => {
    try {
      await createTask({ ...taskData, projectId });
      toast.success("Tugas baru berhasil dibuat!");
      setIsTaskModalOpen(false);
      fetchData();
    } catch (error) {
      toast.error(error.response?.data?.message || "Gagal membuat tugas");
    }
  };

  const handleAddMember = async (memberData) => {
    if (members.some(m => m.id === memberData.id && m.type === memberData.type)) {
      return toast.info("Anggota ini sudah ada di dalam proyek.");
    }

    try {
      await addMemberToProject(projectId, memberData);
      toast.success(`${memberData.name} berhasil ditambahkan!`);
      fetchData();
    } catch (error) {
      toast.error("Gagal menambahkan anggota.");
      console.error(error);
    }
  };

  const tasksNotStarted = tasks.filter(t => t.status === 'NOT_STARTED');
  const tasksInProgress = tasks.filter(t => t.status === 'IN_PROGRESS');
  const tasksCompleted = tasks.filter(t => t.status === 'COMPLETED');

  if (isLoading) return <p className="text-white text-center mt-20">Memuat detail proyek...</p>;

  return (
    <div className="p-6 text-white max-w-7xl mx-auto">
      <div className="flex justify-between items-start mb-6">
        <div>
          <button
            onClick={() => navigate("/projects")}
            className="mb-4 px-4 py-2 bg-gray-700 text-white rounded hover:bg-gray-600 transition-colors"
          >
            ← Kembali
          </button>
          <h1 className="text-3xl font-bold text-yellow-400">{project.title}</h1>
          <p className="mt-2 text-gray-300 max-w-2xl">{project.description}</p>
        </div>
        <div className="flex gap-2">
          <button
            onClick={() => setIsTaskModalOpen(true)}
            className="bg-yellow-400 text-black font-semibold px-4 py-2 rounded hover:bg-yellow-500 transition-colors"
          >
            + Buat Tugas
          </button>
          <button
            onClick={() => setIsMemberModalOpen(true)}
            className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition-colors"
          >
            + Tambah Anggota
          </button>
        </div>
      </div>

      {/* Papan Tugas */}
      <div className="mt-8">
        <h2 className="text-2xl font-semibold mb-4">Papan Tugas</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="bg-gray-900/50 p-4 rounded-lg">
            <h3 className="font-bold text-lg mb-4 text-center text-gray-400">To Do ({tasksNotStarted.length})</h3>
            <div className="space-y-3">
              {tasksNotStarted.length > 0 ? (
                tasksNotStarted.map(task => <TaskCard key={task.id} task={task} onTaskUpdate={fetchData} />)
              ) : (
                <p className="text-center text-sm text-gray-500 mt-4">Belum ada tugas</p>
              )}
            </div>
          </div>
          <div className="bg-gray-900/50 p-4 rounded-lg">
            <h3 className="font-bold text-lg mb-4 text-center text-blue-400">In Progress ({tasksInProgress.length})</h3>
            <div className="space-y-3">
              {tasksInProgress.length > 0 ? (
                tasksInProgress.map(task => <TaskCard key={task.id} task={task} onTaskUpdate={fetchData} />)
              ) : (
                <p className="text-center text-sm text-gray-500 mt-4">Tidak ada tugas</p>
              )}
            </div>
          </div>
          <div className="bg-gray-900/50 p-4 rounded-lg">
            <h3 className="font-bold text-lg mb-4 text-center text-green-500">Completed ({tasksCompleted.length})</h3>
            <div className="space-y-3">
              {tasksCompleted.length > 0 ? (
                tasksCompleted.map(task => <TaskCard key={task.id} task={task} onTaskUpdate={fetchData} />)
              ) : (
                <p className="text-center text-sm text-gray-500 mt-4">Tidak ada tugas</p>
              )}
            </div>
          </div>
        </div>
      </div>

      {/* Daftar Anggota Proyek */}
      <div className="mt-8 max-w-md">
        <h3 className="text-xl font-semibold mb-2">Anggota Proyek</h3>
        {members.length > 0 ? (
          <ul className="space-y-2">
            {members.map((member) => (
              <li
                key={member.id}
                className="bg-gray-800 text-white px-4 py-2 rounded flex justify-between items-center"
              >
                <span>{member.name}</span>
                <span className="text-sm text-gray-400">({member.type})</span>
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-sm text-gray-500">Belum ada anggota dalam proyek ini.</p>
        )}
      </div>

      {/* Modal */}
      <AddMemberModal
        isOpen={isMemberModalOpen}
        onClose={() => setIsMemberModalOpen(false)}
        onAdd={handleAddMember}
      />
      <CreateTaskModal
        isOpen={isTaskModalOpen}
        onClose={() => setIsTaskModalOpen(false)}
        onSubmit={handleCreateTask}
        members={members}
      />
    </div>
  );
};

export default ProjectDetail;
