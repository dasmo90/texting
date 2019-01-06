import React from "react";
import MainApp from "./js/components/MainApp.jsx";
import ReactDOM from "react-dom";
import "./index.scss";

const wrapper = document.getElementById("main");
wrapper ? ReactDOM.render(<MainApp />, wrapper) : false;