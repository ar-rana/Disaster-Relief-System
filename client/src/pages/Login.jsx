import React, { useState } from "react";
import LoginAndRegForm from "../components/LoginAndRegForm";
import Navbar from "../components/Navbar";
import { login } from "../api/general";

const Login = () => {

  const loginHandler = async (e, payload) => {
    e.preventDefault();

    const res = await login(payload);
    if (res.success) { // there will be redirection here
      window.localStorage.setItem("token", res.data);
      window.location.reload();
    } else {
      alert(res.data);
    }
  };

  return (
    <div className="bg-[#33A1E0] h-full w-full">
      <Navbar />
      <LoginAndRegForm
        signUp={false}
        formHandler={loginHandler}
      />
    </div>
  );
};

export default Login;
