import { useState, useEffect } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { getProjects, createProject } from "../../services/projectService";
import TaskCard from "./TaskCard";
import CreateProjectModal from "../Modals/CreateProjectModal";
import { toast } from "react-toastify";
import { motion } from "framer-motion";

const ProjectBoard = () => {
  const [projects, setProjects] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { user } = useAuth();

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const response = await getProjects();
        setProjects(response.data);
      } catch (error) {
        toast.error("Failed to fetch projects");
      }
    };
    fetchProjects();
  }, []);

  const handleCreateProject = async (projectData) => {
    try {
      const response = await createProject(projectData);
      setProjects([...projects, response.data]);
      toast.success("Project created successfully!");
      setIsModalOpen(false);
    } catch (error) {
      toast.error(error.response?.data?.message || "Failed to create project");
    }
  };

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="p-6"
    >
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gold">Project Board</h1>
        <button
          onClick={() => setIsModalOpen(true)}
          className="bg-gold text-navy py-2 px-4 rounded font-semibold hover:bg-yellow-400 transition"
        >
          Create Project
        </button>
      </div>
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {projects.map((project) => (
          <div key={project.id} className="bg-darkGray p-4 rounded-lg shadow-lg">
            <h2 className="text-xl font-semibold text-white mb-4">{project.title}</h2>
            <p className="text-lightGray mb-4">{project.description}</p>
            <div className="space-y-4">
              {project.tasks?.map((task) => (
                <TaskCard key={task.id} task={task} />
              ))}
            </div>
          </div>
        ))}
      </div>
      <CreateProjectModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleCreateProject}
      />
    </motion.div>
  );
};

export default ProjectBoard;