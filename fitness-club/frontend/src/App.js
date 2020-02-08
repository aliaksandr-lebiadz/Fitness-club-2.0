import React, { useState } from "react";
import "./App.css";
import { Switch } from "react-router-dom";
import UsersTable from "./component/user/UsersTable";
import Login from "./component/login/Login";
import Home from "./component/home/Home";
import Header from "./component/header/Header";
import Error from "./component/error/Error";
import { Route } from "react-router-dom";

const App = () => {
  const [currentUser, setCurrentUser] = useState(null);
  return (
    <main>
      <Header />
      <Switch>
        <Route exact path="/">
          {currentUser == null ? (
            <Login setCurrentUser={setCurrentUser} />
          ) : (
            <Home />
          )}
        </Route>
        <Route exact path="/users" component={UsersTable} />
        <Route exact path="/error" component={Error} />
      </Switch>
    </main>
  );
};
export default App;
