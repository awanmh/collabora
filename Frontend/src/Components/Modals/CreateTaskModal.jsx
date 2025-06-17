// src/components/Modals/CreateTaskModal.jsx
import React, { useState, useEffect } from 'react';

const CreateTaskModal = ({ isOpen, onClose, onSubmit, members }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [deadline, setDeadline] = useState('');
  const [assignedToId, setAssignedToId] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (!isOpen) {
      setTitle('');
      setDescription('');
      setDeadline('');
      setAssignedToId('');
      setError('');
    }
  }, [isOpen]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    const taskData = {
      title,
      description,
      deadline: deadline ? new Date(deadline).toISOString() : null,
      assignedToId: parseInt(assignedToId),
    };

    try {
      await onSubmit(taskData); // onSubmit berasal dari props
      onClose();
    } catch (err) {
      console.error("Gagal membuat tugas:", err);
      setError("Gagal membuat tugas. Silakan coba lagi.");
    } finally {
      setIsLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-gray-600 bg-opacity-75 flex items-center justify-center p-4 z-50">
      <div className="bg-gray-800 rounded-lg shadow-lg p-6 w-full max-w-md border border-gray-700">
        <h2 className="text-2xl font-bold text-white mb-6 text-center">Buat Tugas Baru</h2>
        {error && <p className="text-red-400 text-center mb-4">{error}</p>}
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="text-white block mb-1">Judul</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="w-full px-3 py-2 rounded bg-gray-700 text-white"
              required
            />
          </div>

          <div>
            <label className="text-white block mb-1">Deskripsi</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full px-3 py-2 rounded bg-gray-700 text-white"
              required
            />
          </div>

          <div>
            <label className="text-white block mb-1">Deadline</label>
            <input
              type="datetime-local"
              value={deadline}
              onChange={(e) => setDeadline(e.target.value)}
              className="w-full px-3 py-2 rounded bg-gray-700 text-white"
              required
            />
          </div>

          <div>
            <label className="text-white block mb-1">Ditugaskan kepada</label>
            <select
              value={assignedToId}
              onChange={(e) => setAssignedToId(e.target.value)}
              className="w-full px-3 py-2 rounded bg-gray-700 text-white"
              required
            >
              <option value="">Pilih anggota</option>
              {members && members.length > 0 ? (
                members.map((member) => (
                  <option key={member.id} value={member.id}>
                    {member.name} ({member.type})
                  </option>
                ))
              ) : (
                <option disabled>Tidak ada anggota</option>
              )}
            </select>
          </div>

          <div className="flex justify-end gap-2 mt-4">
            <button
              type="button"
              onClick={onClose}
              className="bg-gray-600 px-4 py-2 rounded text-white"
            >
              Batal
            </button>
            <button
              type="submit"
              disabled={isLoading}
              className="bg-blue-600 px-4 py-2 rounded text-white"
            >
              {isLoading ? 'Membuat...' : 'Buat Tugas'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateTaskModal;
