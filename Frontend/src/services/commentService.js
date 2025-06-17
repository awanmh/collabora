// src/services/commentService.js

// Fungsi bantu untuk generate ID unik
const generateId = () => Math.random().toString(36).substr(2, 9);

// Simulasi database dengan localStorage
const STORAGE_KEY = 'comments_data';

const getStoredComments = () => {
  const data = localStorage.getItem(STORAGE_KEY);
  return data ? JSON.parse(data) : [];
};

const saveComments = (comments) => {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(comments));
};

// ✅ Ambil komentar berdasarkan taskId
export const getCommentsByTask = async (taskId) => {
  const allComments = getStoredComments();
  const taskComments = allComments.filter(c => c.taskId === taskId);
  return { data: taskComments };
};

// ✅ Tambahkan komentar baru
export const createComment = async ({ taskId, content, authorId, parentCommentId = null }) => {
  const allComments = getStoredComments();

  const newComment = {
    id: generateId(),
    taskId,
    content,
    authorId,
    parentCommentId,
    createdAt: new Date().toISOString(),
  };

  allComments.push(newComment);
  saveComments(allComments);

  return { data: newComment };
};
