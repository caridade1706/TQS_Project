import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Home from "./Pages/Home/HomePage";
import Queue from "./Pages/Queue/QueuePage";
import ChooseTicket from './Pages/ChooseTicket/ChooseTicket';
import Navbar from "./Components/Navbar/Navbar";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/queue" element={<Queue />} />
        <Route path="/chosseTicket" element={<ChooseTicket />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
