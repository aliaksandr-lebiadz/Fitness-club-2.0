import React from "react";
import { TableBody } from "@material-ui/core";
import PropTypes from "prop-types";
import User from "./User";
import AddUser from "./AddUser";
import { connect } from "react-redux";

const UsersTableBody = props => {
  const { cells, users, addingUser } = props;

  return (
    <TableBody>
      {users.map(user => (
        <User key={user.id} user={user} cells={cells} />
      ))}
      {addingUser ? <AddUser cells={cells} /> : null}
    </TableBody>
  );
};

UsersTableBody.propTypes = {
  users: PropTypes.arrayOf(PropTypes.object).isRequired,
  addingUser: PropTypes.bool.isRequired,
  cells: PropTypes.arrayOf(PropTypes.object).isRequired
};

const mapStateToProps = state => ({
  users: state.users,
  addingUser: state.addingUser
});

export default connect(mapStateToProps, null)(UsersTableBody);
