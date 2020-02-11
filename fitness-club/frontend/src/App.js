import React from "react";
import { Router, Switch, Route } from "react-router";
import "./App.css";
import { history } from "./helpers/history";
import Header from "./component/header/Header";
import Login from "./component/login/Login";
import UsersTable from "./component/user/UsersTable";
import Error from "./component/error/Error";

const App = () => {
  return (
    <main>
      <Router history={history}>
        <Header />
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/users" component={UsersTable} />
          <Route exact path="/error" component={Error} />
        </Switch>
      </Router>
    </main>
  );
};

export default App;
