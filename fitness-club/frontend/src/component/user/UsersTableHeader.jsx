import React from 'react';

class UsersTableHeader extends React.Component{
    render(){
        return(
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Email</th>
                    <th>First name</th>
                    <th>Second name</th>
                    <th>Role</th>
                    <th>Discount</th>
                </tr>
            </thead>
        )
    }
}

export default UsersTableHeader;