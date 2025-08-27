import React, { useState } from "react";
import LoginAndRegForm from "../components/LoginAndRegForm";
import Navbar from "../components/Navbar";

const Login = () => {
  const [user, setUser] = useState("");
  const [pass, setPass] = useState("");
  const [contact, setContact] = useState("");
  const [warning, setWarning] = useState("");
  const [confirmPass, setConfirmPass] = useState("");

  const changeFormState = (e) => {
    e.preventDefault();
    setSignUp((prev) => !prev);
  };

  return (
    <div className="bg-[#33A1E0] h-full w-full">
      <Navbar />
      <LoginAndRegForm
        signUp={false}
        setUser={setUser}
        setPass={setPass}
        warning={warning}
        setConfirmPass={setConfirmPass}
        setContact={setContact}
      />
    </div>
  );
};

export default Login;
