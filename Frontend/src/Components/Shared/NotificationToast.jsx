import { toast } from "react-toastify";

const NotificationToast = ({ message }) => {
  useEffect(() => {
    if (message) {
      toast.info(message, {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        theme: "dark",
      });
    }
  }, [message]);

  return null;
};

export default NotificationToast;