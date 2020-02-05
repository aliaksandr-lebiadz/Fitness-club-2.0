import React from 'react';
import './App.css'
import { Switch } from 'react-router-dom';
import UsersTable from './component/user/UsersTable';
import Home from './component/home/Home';
import Header from './component/header/Header';
import { Route } from 'react-router-dom';

class App extends React.Component {
  render(){
    return (
        <main>
          <Header />
          <Switch>
            <Route exact path='/' component={Home}/>
            <Route exact path='/users' component={UsersTable}/>
          </Switch>
        </main>
    );
  }
}
 
export default App;