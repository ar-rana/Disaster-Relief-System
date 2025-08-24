import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navigation from "./pages/Navigation";
import Map from "./components/Map";

function App() {
  return (
    <div className="h-screen">
      {/* <Map /> */}
      <BrowserRouter>
        <Routes>
          <Route path="/navigation" element={<Navigation />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
