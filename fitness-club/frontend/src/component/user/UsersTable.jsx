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
        this.deleteUser = this.deleteUser.bind(this);
        this.reloadUserList = this.reloadUserList.bind(this);
    }

    componentDidMount(){
        this.reloadUserList();
    }

    reloadUserList(){
        userService.getAll().then(
            response => {
                this.setState({users: response.data})
            }
        );
    }

    deleteUser(){
        const { selectedRow } = this.state;
        if(selectedRow !== null){
            userService.deleteById(selectedRow).then(
                response => {
                    this.setState({users: this.state.users.filter(user => user.id !== selectedRow)});
                    this.setState({selectedRow: null});
                }
            ).catch(error => {
                history.push('/error');
            })
        }
    }

    select(index){
        this.setState({selectedRow: index});
    }

    render() {
        return (
            <div id="users-intro">
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
                    <button className="custom-button" id="delete-button"onClick={() => this.deleteUser()}>Delete</button>
                </div>
            </div>
          )
    }
}

export default UsersTable;