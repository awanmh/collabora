import { useState } from "react";
import { useAuth } from "../../contexts/AuthContext";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { motion } from "framer-motion";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("STUDENT");
  const [studentId, setStudentId] = useState("");
  const [lecturerId, setLecturerId] = useState("");
  const [loading, setLoading] = useState(false);
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      await register({ username, password, role, studentId, lecturerId });
      toast.success("Registration successful!");
      navigate("/login");
    } catch (error) {
      toast.error(error.response?.data?.message || "Registration failed");
    }
    setLoading(false);
  };

  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      className="flex items-center justify-center min-h-screen"
    >
      <div className="bg-darkGray p-8 rounded-lg shadow-lg w-full max-w-md">
        <h2 className="text-2xl font-bold text-gold mb-6 text-center">Register to Collabora</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="username">
              Username
            </label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="password">
              Password
            </label>
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              required
            />
          </div>
          <div className="mb-4">
            <label className="block text-lightGray mb-2" htmlFor="role">
              Role
            </label>
            <select
              id="role"
              value={role}
              onChange={(e) => setRole(e.target.value)}
              className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
            >
              <option value="STUDENT">Student</option>
              <option value="LECTURER">Lecturer</option>
            </select>
          </div>
          {role === "STUDENT" && (
            <div className="mb-4">
              <label className="block text-lightGray mb-2" htmlFor="studentId">
                Student ID
              </label>
              <input
                type="text"
                id="studentId"
                value={studentId}
                onChange={(e) => setStudentId(e.target.value)}
                className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              />
            </div>
          )}
          {role === "LECTURER" && (
            <div className="mb-4">
              <label className="block text-lightGray mb-2" htmlFor="lecturerId">
                Lecturer ID
              </label>
              <input
                type="text"
                id="lecturerId"
                value={lecturerId}
                onChange={(e) => setLecturerId(e.target.value)}
                className="w-full p-3 bg-navy border border-lightGray rounded focus:outline-none focus:border-gold"
              />
            </div>
          )}
          <button
            type="submit"
            className="w-full bg-gold text-navy py-3 rounded font-semibold hover:bg-yellow-400 transition disabled:opacity-50"
            disabled={loading}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </form>
        <p className="mt-4 text-center text-lightGray">
          Already have an account?{" "}
          <a href="/login" className="text-gold hover:underline">
            Login
          </a>
        </p>
      </div>
    </motion.div>
  );
};

export default Register;