import { useState, useRef, useCallback, useEffect } from "react";

export default function useVerify() {
  const [data, setData] = useState(false);
  const [error, setError] = useState(null);

  const verify = async () => {
    setError(null);
    try {
      console.log("BASE_URL at verfy hook: ", import.meta.env.VITE_BASE_URL);
      const res = await fetch(`${import.meta.env.VITE_BASE_URL}user/verify`, {
        method: "GET",
      });

      const response = await res.text();
      if (res.ok) {
        setData(true);
        console.log(response);
      } else {
        setData(false);
      }
    } catch (err) {
      setError(err);
    }
  };

  useEffect(() => {
    verify();
    const handleStorageChange = () => {
      verify();
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  return { data, error };
}
