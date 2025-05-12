import { useState } from "react";
import { FaTimes } from "react-icons/fa";
import { motion } from "framer-motion";

const CreateProjectModal = ({ isOpen, onClose, onSubmit }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [memberIds, setMemberIds] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit({
      title,
      description,
      memberIds: memberIds.split(",").map((id) => parseInt(id.trim())),
    });
    setTitle("");
    setDescription("");
    setMemberIds("");
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
          <h2 className="text-xl font-bold text-gold">Create New Project</h2>
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
            <label className="block text-lightGray mb-2" htmlFor="memberIds">
              Member IDs (comma-separated)
            </label>
            <input
              type="text"
              id="memberIds"
              value={memberIds}
              onChange={(e) => setMemberIds(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              placeholder="1,2,3"
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

export default CreateProjectModal;