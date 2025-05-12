import { useEffect, useState } from "react";
import { getTasks } from "../../services/projectService";
import { toast } from "react-toastify";
import { motion } from "framer-motion";

const Timeline = () => {
  const [tasks, setTasks] = useState([]);

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await getTasks(); // Implementasi API untuk semua tugas
        setTasks(response.data);
      } catch (error) {
        toast.error("Failed to fetch tasks");
      }
    };
    fetchTasks();
  }, []);

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="p-6"
    >
      <h2 className="text-2xl font-bold text-gold mb-6">Project Timeline</h2>
      <div className="space-y-4">
        {tasks.map((task) => (
          <div
            key={task.id}
            className="flex items-center p-4 bg-darkGray rounded-lg shadow-md"
          >
            <div className="w-2 h-2 bg-gold rounded-full mr-4"></div>
            <div>
              <h3 className="text-lg font-semibold text-white">{task.title}</h3>
              <p className="text-lightGray text-sm">
                Deadline: {new Date(task.deadline).toLocaleDateString()}
              </p>
              <p className="text-lightGray text-sm">Status: {task.status}</p>
            </div>
          </div>
        ))}
      </div>
    </motion.div>
  );
};

export default Timeline;