import React, { useState } from "react";
import LoginAndRegForm from "../components/LoginAndRegForm";
import Navbar from "../components/Navbar";

const Login = () => {

  const loginHandler = (e) => {
    e.preventDefault();
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
