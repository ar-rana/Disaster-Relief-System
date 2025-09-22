import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navigation from "./pages/Navigation";
import Home from "./pages/Home.jsx";
import Login from "./pages/Login.jsx";
import AdminWrapper from "./pages/wappers/AdminWrapper.jsx";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/dashboard" element={<AdminWrapper />}></Route>
          <Route path="/login" element={<Login />}></Route>
          <Route path="/navigation" element={<Navigation />}></Route>
          <Route path="/" element={<Home />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
