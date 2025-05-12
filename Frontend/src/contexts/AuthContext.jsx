import { createContext, useContext, useState, useEffect } from "react";
import { login as loginService, register as registerService } from "../services/authService";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [token, setToken] = useState(localStorage.getItem("token") || "");

  useEffect(() => {
    if (token) {
      // Asumsikan token valid, decode untuk mendapatkan user info
      // Dalam produksi, validasi token dengan backend
      const payload = JSON.parse(atob(token.split(".")[1]));
      setUser({ username: payload.sub, role: payload.role });
    }
  }, [token]);

  const login = async ({ username, password }) => {
    const response = await loginService({ username, password });
    const { token, username: userName, role } = response.data;
    setToken(token);
    setUser({ username: userName, role });
    localStorage.setItem("token", token);
  };

  const register = async ({ username, password, role, studentId, lecturerId }) => {
    const response = await registerService({ username, password, role, studentId, lecturerId });
    const { token, username: userName, role: userRole } = response.data;
    setToken(token);
    setUser({ username: userName, role: userRole });
    localStorage.setItem("token", token);
  };

  const logout = () => {
    setUser(null);
    setToken("");
    localStorage.removeItem("token");
  };

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);