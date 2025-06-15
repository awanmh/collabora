// src/App.jsx

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { AuthProvider } from "./contexts/AuthContext";
import Login from "./Components/Auth/Login";
import Register from "./Components/Auth/Register";
import ProjectBoard from "./Components/Dashboard/ProjectBoard";
import ProjectDetail from "./Components/Dashboard/ProjectDetail";
import Navbar from "./Components/Shared/Navbar";
import PrivateRoute from "./Components/Shared/PrivateRoute";

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-navy text-white">
          <Navbar />
          <Routes>
            {/* Rute Publik */}
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />

            {/* Rute Privat / Dilindungi */}
            <Route 
              path="/projects/:id" 
              element={
                <PrivateRoute>
                  <ProjectDetail />
                </PrivateRoute>
              } 
            />
            <Route 
              path="/projects" 
              element={
                <PrivateRoute>
                  <ProjectBoard />
                </PrivateRoute>
              } 
            />
            <Route
              path="/"
              element={
                <PrivateRoute>
                  <ProjectBoard />
                </PrivateRoute>
              }
            />
          </Routes>
          <ToastContainer
            position="top-right"
            autoClose={3000}
            hideProgressBar={false}
            newestOnTop
            closeOnClick
            rtl={false}
            pauseOnFocusLoss
            draggable
            pauseOnHover
            theme="dark"
          />
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;