import { useState, useRef, useCallback, useEffect } from "react";

export default function useVerify() {
  const [data, setData] = useState(false);
  const [error, setError] = useState(null);

  const verify = async () => {
    setError(null);
    try {
      console.log("BASE_URL at verfy hook: ", process.env.BASE_URL);
      const res = await fetch(`/${process.env.BASE_URL}user/verify`, {
        method: "GET",
      });

      if (res.ok) {
        setData(true);
      } else {
        setData(false);
      }
    } catch (err) {
      setError(err);
    }
  };

  useEffect(() => {
    const handleStorageChange = () => {
      verify();
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  return { data, error };
}
