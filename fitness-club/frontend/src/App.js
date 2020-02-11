import React from "react";
import { Router, Switch, Route } from "react-router";
import "./App.css";
import { history } from "./helpers/history";
import Header from "./component/header/Header";
import Login from "./component/login/Login";
import Home from "./component/home/Home";
import UsersTable from "./component/user/UsersTable";
import Error from "./component/error/Error";
import PropTypes from "prop-types";
import { connect } from "react-redux";

const App = props => {
  const { currentUser } = props;
  const role = currentUser !== null ? currentUser.role : null;

  return (
    <main>
      <Router history={history}>
        <Header />
        <Switch>
          <Route exact path="/users">
            {role === "ADMIN" ? <UsersTable /> : <Error />}
          </Route>
          <Route exact path="/error" component={Error} />
          <Route exact path="/**">
            {currentUser !== null ? <Home /> : <Login />}
          </Route>
        </Switch>
      </Router>
    </main>
  );
};

App.propTypes = {
  currentUser: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
  currentUser: state.currentUser
});

export default connect(mapStateToProps, null)(App);
