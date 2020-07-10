import React from "react";
import PropTypes from "prop-types";
import { TableHead, TableCell, Button } from "@material-ui/core";
import { connect } from "react-redux";
import { startAddingUser } from "../../actions";
import { bindActionCreators } from "redux";

const UsersTableHead = props => {
  const { cells } = props;

  return (
    <TableHead>
      {cells.map(headCell => (
        <TableCell key={headCell.id}>{headCell.label}</TableCell>
      ))}
      <TableCell>
        <Button
          variant="contained"
          color="primary"
          onClick={props.startAddingUser}
        >
          Add
        </Button>
      </TableCell>
    </TableHead>
  );
};

UsersTableHead.propTypes = {
  cells: PropTypes.arrayOf(PropTypes.object).isRequired
};

const mapDispatchToProps = dispatch => {
  return bindActionCreators({ startAddingUser }, dispatch);
};

export default connect(null, mapDispatchToProps)(UsersTableHead);
