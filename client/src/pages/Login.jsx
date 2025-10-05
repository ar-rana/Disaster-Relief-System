import React, { useState } from "react";
import LoginAndRegForm from "../components/LoginAndRegForm";
import Navbar from "../components/Navbar";
import { login } from "../api/general";
import useVerify from "../hooks/useVerify";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const { data, err } = useVerify();

  if (data) {
    window.location.href = "/";
  }

  const navigate = useNavigate();

  const loginHandler = async (e, payload) => {
    e.preventDefault();

    const res = await login(payload);
    if (res.success) {
      window.localStorage.setItem("token", res.data.token);
      window.localStorage.setItem("user", res.data.user);
      window.localStorage.setItem("hq", res.data.hq);
      window.location.href = res.data.redirect;
    } else {
      alert(res.data);
    }
    console.log(res.data);
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
