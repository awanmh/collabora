// src/components/Dashboard/TaskCard.jsx

import React, { useState } from 'react';
import { FaUser, FaTags } from "react-icons/fa"; // Mengganti ikon jam dengan ikon tag untuk milestone
import { toast } from "react-toastify";
import { motion } from "framer-motion";
import { updateTask } from "../../services/taskService"; // 1. Kita butuh fungsi untuk UPDATE
import CreateTaskModal from "../Modals/CreateTaskModal"; // 2. Kita akan gunakan ulang modal ini untuk EDIT

/**
 * Komponen kartu untuk menampilkan satu tugas.
 * @param {object} props
 * @param {object} props.task - Objek data tugas.
 * @param {Function} props.onTaskUpdate - Callback untuk memberitahu parent bahwa tugas telah diupdate,
 * agar UI bisa di-refresh.
 */
const TaskCard = ({ task, onTaskUpdate }) => {
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  // 3. Fungsi ini sekarang untuk MENGUPDATE, bukan membuat.
  const handleUpdateTask = async (taskDataFromModal) => {
    try {
      // Panggil API untuk update tugas dengan ID tugas dan data barunya
      await updateTask(task.id, taskDataFromModal);
      toast.success("Tugas berhasil diperbarui!");
      setIsEditModalOpen(false); // Tutup modal
      
      // Beritahu komponen parent (ProjectDetail) untuk memuat ulang daftar tugas
      if (onTaskUpdate) {
        onTaskUpdate();
      }
    } catch (error) {
      toast.error(error.response?.data?.message || "Gagal memperbarui tugas");
    }
  };

  // Pengaman jika data task tidak ada
  if (!task) return null;

  return (
    <>
      <motion.div
        whileHover={{ scale: 1.03 }}
        className="bg-gray-800 p-4 rounded-lg mb-3 shadow-lg border border-gray-700/50"
      >
        <h3 className="text-lg font-semibold text-white">{task.title}</h3>
        
        {task.description && (
          <p className="text-gray-400 text-sm mt-2 break-words">{task.description}</p>
        )}
        
        {/* 4. Menampilkan MILESTONE, bukan deadline */}
        {task.milestone && (
          <div className="flex items-center mt-4 text-gray-400 text-sm">
            <FaTags className="mr-2 text-yellow-400" />
            <span>{task.milestone}</span>
          </div>
        )}
        
        {/* 5. Menampilkan USERNAME assignee, bukan ID */}
        <div className="flex items-center mt-2 text-gray-400 text-sm">
          <FaUser className="mr-2" />
          <span>{task.assignee?.username || "Belum ditugaskan"}</span>
        </div>

        <div className="mt-4 pt-3 border-t border-gray-700 flex justify-between items-center">
          {/* 6. Styling badge status yang lebih baik */}
          <span
            className={`px-3 py-1 rounded-full text-xs font-bold capitalize ${
              task.status === "COMPLETED"
                ? "bg-green-500/20 text-green-400"
                : task.status === "IN_PROGRESS"
                ? "bg-blue-500/20 text-blue-400"
                : "bg-gray-600/30 text-gray-300" // NOT_STARTED
            }`}
          >
            {task.status.replace("_", " ")}
          </span>
          <button
            onClick={() => setIsEditModalOpen(true)}
            className="text-yellow-400 hover:text-yellow-300 hover:underline text-sm font-semibold"
          >
            Edit
          </button>
        </div>
      </motion.div>

      {/* 7. Modal ini sekarang kita gunakan untuk EDITING */}
      {isEditModalOpen && (
        <CreateTaskModal
          isOpen={isEditModalOpen}
          onClose={() => setIsEditModalOpen(false)}
          onSubmit={handleUpdateTask}
          // PENTING: Kirim data tugas yang ada ke modal
          // agar form bisa diisi dengan data saat ini untuk diedit.
          // Anda perlu memodifikasi CreateTaskModal untuk menerima prop ini.
          taskToEdit={task} 
        />
      )}
    </>
  );
};

export default TaskCard;
