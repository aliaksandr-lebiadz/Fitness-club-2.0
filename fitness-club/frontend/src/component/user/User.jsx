import React from 'react';
import PropTypes from 'prop-types';


class User extends React.Component{
    render(){
        const { user, selected } = this.props;

        return(
            <tr className={selected ? 'selected' : ''} onClick={() => this.props.onSelect(user.id)}>
                <td>{user.id}</td>
                <td>{user.email}</td>
                <td>{user.firstName}</td>
                <td>{user.secondName}</td>
                <td>{user.role}</td>
                <td>{user.discount}</td>
            </tr>
        )
    }
}

User.propTypes = {
    user: PropTypes.object.isRequired,
    onSelect: PropTypes.func.isRequired,
    selected: PropTypes.bool.isRequired
}

export default User;