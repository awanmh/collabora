// src/Components/Modals/Modal.jsx

import React, { useEffect } from 'react';
import ReactDOM from 'react-dom';

const Modal = ({ isOpen, onClose, children }) => {
  if (!isOpen) return null;

  const modalRoot = document.getElementById('modal-root');

  if (!modalRoot) {
    console.error(
      "Error: Elemen dengan ID 'modal-root' tidak ditemukan di public/index.html Anda.\n" +
      "Tambahkan <div id='modal-root'></div> tepat sebelum penutup </body>."
    );
    return null;
  }

  // ❗ Tambahkan efek: tekan ESC untuk menutup modal
  useEffect(() => {
    const handleKeyDown = (e) => {
      if (e.key === 'Escape') onClose();
    };
    window.addEventListener('keydown', handleKeyDown);
    return () => window.removeEventListener('keydown', handleKeyDown);
  }, [onClose]);

  return ReactDOM.createPortal(
    <div
      className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center z-50"
      onClick={onClose} // Klik area luar akan menutup modal
    >
      <div
        className="bg-gray-900 p-8 rounded-lg shadow-xl w-full max-w-lg mx-4 relative"
        onClick={(e) => e.stopPropagation()} // Mencegah klik dalam modal menutup
      >
        {/* Tombol Close (X) di pojok kanan atas */}
        <button
          onClick={onClose}
          className="absolute top-3 right-3 text-gray-400 hover:text-gray-200 text-2xl"
          aria-label="Close modal"
        >
          &times;
        </button>
        {children}
      </div>
    </div>,
    modalRoot
  );
};

export default Modal;
