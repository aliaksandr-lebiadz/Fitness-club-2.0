import React from 'react';
import './App.css'
import { Switch } from 'react-router-dom';
import UsersTable from './component/user/UsersTable';
import Home from './component/home/Home';
import Header from './component/header/Header';
import Error from './component/error/Error';
import { Route } from 'react-router-dom';

class App extends React.Component {
  render(){
    return (
        <main>
          <Header />
          <Switch>
            <Route exact path='/' component={Home}/>
            <Route exact path='/users' component={UsersTable}/>
            <Route exact path='/error' component={Error}/>
          </Switch>
        </main>
    );
  }
}
 
export default App;