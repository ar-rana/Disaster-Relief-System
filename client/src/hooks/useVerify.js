import { useState, useRef, useCallback, useEffect } from "react";
import { verify } from "../api/general";

export default function useVerify() {
  const [data, setData] = useState(false);
  const [error, setError] = useState(null);

  const verifyHook = async () => {
      const res = await verify(); 

      if (res.success) {
        setData(true);
        console.log(res.data);
      } else {
        setData(false);
        setError(res.data);
      }
  };

  useEffect(() => {
    verifyHook();
    const handleStorageChange = () => {
      verifyHook();
    };

    window.addEventListener("storage", handleStorageChange);
    return () => window.removeEventListener("storage", handleStorageChange);
  }, []);

  return { data, error };
}
