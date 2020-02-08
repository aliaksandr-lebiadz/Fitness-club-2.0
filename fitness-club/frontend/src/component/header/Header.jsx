import React from "react";
import { makeStyles } from "@material-ui/core/styles";
import Typography from "@material-ui/core/Typography";
import AppBar from "@material-ui/core/AppBar";

const useStyles = makeStyles({
  header: {
    backgroundColor: "#76FEC5"
  },
  logo: {
    padding: 20
  }
});

const Header = () => {
  const classes = useStyles();
  return (
    <AppBar className={classes.header} position="fixed">
      <Typography className={classes.logo} variant="h6" color="textPrimary">
        Fitness club
      </Typography>
    </AppBar>
  );
};

export default Header;
