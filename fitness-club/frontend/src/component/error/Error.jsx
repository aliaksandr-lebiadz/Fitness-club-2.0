import React from "react";
import "../styles/Error.css";
import { history } from "../../helpers/history";

class Error extends React.Component {
  render() {
    return (
      <div id="error-intro">
        <div id="error">
          <span id="error-text">Something went wrong...</span>
          <button
            className="custom-button"
            id="home-button"
            onClick={() => history.push("/")}
          >
            Home
          </button>
        </div>
      </div>
    );
  }
}

export default Error;
