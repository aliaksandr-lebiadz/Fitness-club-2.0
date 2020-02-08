import React from "react";
import PropTypes from "prop-types";
import { TableHead, TableCell, Button } from "@material-ui/core";

const UsersTableHead = props => {
  const { startAdding, cells } = props;
  return (
    <TableHead>
      {cells.map(headCell => (
        <TableCell key={headCell.id}>{headCell.label}</TableCell>
      ))}
      <TableCell>
        <Button variant="contained" color="primary" onClick={startAdding}>
          Add
        </Button>
      </TableCell>
    </TableHead>
  );
};

UsersTableHead.propTypes = {
  startAdding: PropTypes.func.isRequired,
  cells: PropTypes.arrayOf(PropTypes.object).isRequired
};

export default UsersTableHead;
