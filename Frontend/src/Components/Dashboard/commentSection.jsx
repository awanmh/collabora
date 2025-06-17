import React, { useState, useEffect, useCallback } from 'react';
import { toast } from 'react-toastify';
import { getCommentsByTask, createComment } from '../../services/commentService';

const CommentSection = ({ taskId }) => {
  const [comments, setComments] = useState([]);
  const [newCommentContent, setNewCommentContent] = useState('');
  const [isLoadingComments, setIsLoadingComments] = useState(false);
  const [isSubmittingComment, setIsSubmittingComment] = useState(false);
  const [users, setUsers] = useState({});

  // ✅ Gunakan dummy current user
  const currentUser = {
    id: 1,
    username: 'DummyUser',
  };

  const fetchComments = useCallback(async () => {
    setIsLoadingComments(true);
    try {
      const response = await getCommentsByTask(taskId);
      setComments(response.data);

      // Dummy mapping authorId ke username
      const authorMap = {};
      response.data.forEach(comment => {
        if (!authorMap[comment.authorId]) {
          authorMap[comment.authorId] = `User_${comment.authorId}`;
        }
      });
      setUsers(authorMap);
    } catch (error) {
      toast.error("Gagal memuat komentar.");
      console.error("Error fetching comments:", error);
    } finally {
      setIsLoadingComments(false);
    }
  }, [taskId]);

  useEffect(() => {
    if (taskId) {
      fetchComments();
    }
  }, [taskId, fetchComments]);

  const handleSubmitComment = async (e) => {
    e.preventDefault();

    if (!newCommentContent.trim()) {
      toast.warn("Komentar tidak boleh kosong!");
      return;
    }

    const commentData = {
      content: newCommentContent,
      taskId: taskId,
      authorId: currentUser.id, // ✅ Kirim dummy authorId
    };

    setIsSubmittingComment(true);
    try {
      await createComment(commentData);
      toast.success("Komentar berhasil ditambahkan!");
      setNewCommentContent('');
      fetchComments(); // Refresh komentar
    } catch (error) {
      toast.error(error.response?.data?.message || "Gagal menambahkan komentar.");
      console.error("Error submitting comment:", error);
    } finally {
      setIsSubmittingComment(false);
    }
  };

  const renderComments = (commentsToRender) => {
    const commentMap = new Map();
    commentsToRender.forEach(comment => commentMap.set(comment.id, { ...comment, replies: [] }));

    const rootComments = [];
    commentsToRender.forEach(comment => {
      if (comment.parentCommentId && commentMap.has(comment.parentCommentId)) {
        commentMap.get(comment.parentCommentId).replies.push(commentMap.get(comment.id));
      } else {
        rootComments.push(commentMap.get(comment.id));
      }
    });

    const displayComment = (comment, level = 0) => (
      <div key={comment.id} className={`bg-gray-800 p-3 rounded-lg ${level > 0 ? 'ml-6 border-l border-gray-700' : ''} mb-2`}>
        <div className="flex items-center text-sm text-gray-400 mb-1">
          <span className="font-semibold text-yellow-300 mr-2">
            {users[comment.authorId] || `User_${comment.authorId}`}
          </span>
          <span className="text-xs text-gray-500">
            {new Date(comment.createdAt || Date.now()).toLocaleString('id-ID', {
              year: 'numeric', month: 'short', day: 'numeric',
              hour: '2-digit', minute: '2-digit'
            })}
          </span>
        </div>
        <p className="text-gray-200">{comment.content}</p>
        {comment.replies.length > 0 && (
          <div className="mt-2">
            {comment.replies.map(reply => displayComment(reply, level + 1))}
          </div>
        )}
      </div>
    );

    return rootComments.map(comment => displayComment(comment));
  };

  return (
    <div className="mt-8">
      <h3 className="text-2xl font-semibold mb-4">Komentar</h3>

      {/* Komentar */}
      <div className="bg-gray-900/50 p-4 rounded-lg min-h-[150px] max-h-[400px] overflow-y-auto mb-4">
        {isLoadingComments ? (
          <p className="text-center text-gray-500">Memuat komentar...</p>
        ) : comments.length > 0 ? (
          renderComments(comments)
        ) : (
          <p className="text-center text-gray-500">Belum ada komentar untuk tugas ini.</p>
        )}
      </div>

      {/* Form Komentar */}
      <form onSubmit={handleSubmitComment} className="mt-4">
        <textarea
          className="w-full p-3 bg-gray-800 border border-gray-700 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 text-white placeholder-gray-500"
          rows="3"
          placeholder="Tulis komentar Anda..."
          value={newCommentContent}
          onChange={(e) => setNewCommentContent(e.target.value)}
          disabled={isSubmittingComment}
        ></textarea>
        <button
          type="submit"
          className="mt-3 px-6 py-2 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          disabled={isSubmittingComment}
        >
          {isSubmittingComment ? 'Mengirim...' : 'Kirim Komentar'}
        </button>
      </form>
    </div>
  );
};

export default CommentSection;
