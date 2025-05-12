import { useState } from "react";
import { FaClock, FaUser } from "react-icons/fa";
import CreateTaskModal from "../Modals/CreateTaskModal";
import { toast } from "react-toastify";
import { motion } from "framer-motion";

const TaskCard = ({ task }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleCreateTask = async (taskData) => {
    try {
      // Implementasi API untuk membuat tugas
      toast.success("Task created successfully!");
      setIsModalOpen(false);
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to create task");
    }
  };

  return (
    <motion.div
      whileHover={{ scale: 1.02 }}
      className="bg-navy p-4 rounded-lg shadow-md border border-lightGray"
    >
      <h3 className="text-lg font-semibold text-white">{task.title}</h3>
      <p className="text-lightGray text-sm mt-2">{task.description}</p>
      <div className="flex items-center mt-4 text-lightGray text-sm">
        <FaClock className="mr-2" />
        <span>{new Date(task.deadline).toLocaleDateString()}</span>
      </div>
      <div className="flex items-center mt-2 text-lightGray text-sm">
        <FaUser className="mr-2" />
        <span>{task.assignedToId || "Unassigned"}</span>
      </div>
      <div className="mt-4 flex justify-between">
        <span
          className={`px-2 py-1 rounded text-xs ${
            task.status === "COMPLETED"
              ? "bg-green-500"
              : task.status === "IN_PROGRESS"
              ? "bg-yellow-500"
              : "bg-red-500"
          } text-navy`}
        >
          {task.status}
        </span>
        <button
          onClick={() => setIsModalOpen(true)}
          className="text-gold hover:underline text-sm"
        >
          Edit Task
        </button>
      </div>
      <CreateTaskModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleCreateTask}
        projectId={task.projectId}
      />
    </motion.div>
  );
};

export default TaskCard;