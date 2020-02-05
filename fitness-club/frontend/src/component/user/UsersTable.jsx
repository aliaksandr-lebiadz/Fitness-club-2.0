import React from 'react';
import User from './User'
import UsersTableHeader from './UsersTableHeader';
import '../styles/UsersTable.css';
import { history } from '../../helpers';
import { userService } from '../../service';

class UsersTable extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            users: [],
            selectedRow: null
        }
    }

    componentDidMount(){
        userService.getAll().then(
            response => {
                this.setState({users: response.data})
            }
        );
    }

    select(index){
        this.setState({selectedRow: index});
    }

    render() {
        return (
            <div id="container">
                <table>
                    <UsersTableHeader />
                    <tbody>
                        {this.state.users.map(user => {
                            return <User user={user} key={user.id} onSelect={(index) => this.select(index)} 
                            selected={this.state.selectedRow === user.id ? true : false}/>
                        })}
                    </tbody>
                </table>
                <button onClick={() => history.push('/')}>Home</button>
            </div>
          )
    }
}

export default UsersTable;