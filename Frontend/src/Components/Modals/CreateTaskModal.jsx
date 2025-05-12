import { useState } from "react";
import { FaTimes } from "react-icons/fa";
import { motion } from "framer-motion";

const CreateTaskModal = ({ isOpen, onClose, onSubmit, projectId }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");
  const [assignedToId, setAssignedToId] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      title,
      description,
      deadline,
      projectId,
      assignedToId: assignedToId ? parseInt(assignedToId) : null,
    });
    setTitle("");
    setDescription("");
    setDeadline("");
    setAssignedToId("");
  };

  if (!isOpen) return null;

  return (
    <motion.div
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
      className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    >
      <div className="bg-darkGray p-6 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-bold text-gold">Create New Task</h2>
          <button onClick={onClose} className="text-lightGray hover:text-white">
            <FaTimes />
          </button>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="title">
              Title
            </label>
            <input
              type="text"
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="description">
              Description
            </label>
            <textarea
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              rows="4"
            ></textarea>
          </div>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="deadline">
              Deadline
            </label>
            <input
              type="datetime-local"
              id="deadline"
              value={deadline}
              onChange={(e) => setDeadline(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="assignedToId">
              Assigned To (User ID)
            </label>
            <input
              type="number"
              id="assignedToId"
              value={assignedToId}
              onChange={(e) => setAssignedToId(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
            />
          </div>
          <div className="flex justify-end">
            <button
              type="button"
              onClick={onClose}
              className="mr-4 text-lightGray hover:text-white"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="bg-gold text-navy py-2 px-4 rounded font-semibold hover:bg-yellow-400 transition"
            >
              Create
            </button>
          </div>
        </form>
      </div>
    </motion.div>
  );
};

export default CreateTaskModal;